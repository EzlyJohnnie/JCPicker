package nz.co.jclib.jcpicklib.ui.fragment.imagePicker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import java.util.ArrayList;

import nz.co.jclib.jcpicklib.data.model.JCFile;
import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.ui.JCPickerHostFragment;
import nz.co.jclib.jcpicklib.ui.adapter.JCAlbumListAdapter;
import nz.co.jclib.jcpicklib.ui.adapter.JCBaseFileListAdapter;
import nz.co.jclib.jcpicklib.ui.fragment.JCPickerBaseFileListFragment;
import nz.co.jclib.jcpicklib.utils.UIHelper;

/**
 * Created by Johnnie on 28/03/17.
 */
public class JCAlbumPickerFragment extends JCPickerBaseFileListFragment{
    private static final int MAX_IMAGE_WIDTH = 180;

    private int column = -1;
    private JCGridSpacingItemDecoration itemDecoration;

    public static Fragment getInstance(JCPickerEnterOption enterOption) {
        JCAlbumPickerFragment fragment = new JCAlbumPickerFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_ENTER_OPTION, enterOption);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * calculate column base on screen width(dp)
     * 2 columns as min
     * @return
     */
    private int getColumn(){
        if(files != null && files.size() > 0){
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int screenWidth = (int)(displaymetrics.widthPixels / getContext().getResources().getDisplayMetrics().density);
            column = screenWidth / MAX_IMAGE_WIDTH;

            if(column < 2){
                column = 2;
            }
        }
        else{
            column = 1;
        }

        return column;
    }

    @Override
    protected JCBaseFileListAdapter initListAdapter(){
        return new JCAlbumListAdapter();
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(getContext(), getColumn());
    }

    @Override
    protected void addItemDecorationIfNeed(RecyclerView fileList) {
        if(itemDecoration != null){
            fileList.removeItemDecoration(itemDecoration);
        }
        itemDecoration = new JCGridSpacingItemDecoration(getColumn(), UIHelper.dip2px(getContext(), 1), false);
        fileList.addItemDecoration(itemDecoration);
    }

    @Override
    protected void loadFiles() {
        presenter.loadAlbum();
    }

    @Override
    protected void processPreparedFiles(ArrayList<JCFile> files) {
        int previousFilesCount = this.files == null ? 0 : this.files.size();
        //to re-init file list layout
        if((previousFilesCount == 0 && (files != null && files.size() > 0))
                || (previousFilesCount > 0 && (files == null || files.size() <= 0)))
        {
            this.files = files;
            initRecyclerView();
        }
        super.processPreparedFiles(files);
    }




    @Override
    public void onOpenFolder(JCFile file, int position) {
        if(getParentFragment() instanceof JCPickerHostFragment){
            JCPickerEnterOption enterOption = this.enterOption.clone();
            enterOption.setAlbumName(file.getName());
            enterOption.setParentName(file.getName());
            ((JCPickerHostFragment)getParentFragment()).pushFragment(getInstance(enterOption));
        }
    }
}
