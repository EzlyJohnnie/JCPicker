package nz.co.jclib.jcpicklib.ui.fragment.imagePicker;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.ui.adapter.JCAlbumListAdapter;
import nz.co.jclib.jcpicklib.ui.adapter.JCBaseFileListAdapter;
import nz.co.jclib.jcpicklib.ui.adapter.JCImageListAdapter;
import nz.co.jclib.jcpicklib.ui.fragment.JCPickerBaseFileListFragment;

/**
 * Created by Johnnie on 28/03/17.
 */
public class JCImagePickerFragment extends JCAlbumPickerFragment {

    public static Fragment getInstance(JCPickerEnterOption enterOption) {
        JCImagePickerFragment fragment = new JCImagePickerFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_ENTER_OPTION, enterOption);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getMaxImageWidth(){
        return 120;
    }

    @Override
    protected int getMinColumnCount(){
        return 3;
    }

    @Override
    protected JCBaseFileListAdapter initListAdapter(){
        return new JCImageListAdapter();
    }

    @Override
    protected void loadFiles() {
        presenter.loadImageForAlbumName(enterOption.getAlbumID());
    }
}
