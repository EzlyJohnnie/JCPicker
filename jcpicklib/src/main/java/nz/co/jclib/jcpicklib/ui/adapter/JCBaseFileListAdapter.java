package nz.co.jclib.jcpicklib.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import nz.co.jclib.jcpicklib.JCPickerClient;
import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.data.model.JCFile;
import nz.co.jclib.jcpicklib.ui.adapter.viewHolder.JCEmptyFileListItem;
import nz.co.jclib.jcpicklib.ui.adapter.viewHolder.JCFileListItem;
import nz.co.jclib.jcpicklib.utils.FileUtils;

/**
 * Created by Johnnie on 18/04/17.
 */
public class JCBaseFileListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_FILE = 0;
    private static final int VIEW_TYPE_NO_FILE = 1;

    protected ArrayList<JCFile> files;
    protected ArrayList<JCFile> selectedFiles;

    protected FileListAdapterListener mFileListAdapterListener;

    public JCBaseFileListAdapter() {
        selectedFiles = new ArrayList<>();
    }

    public void setFiles(ArrayList<JCFile> files) {
        this.files = files;
        notifyDataSetChanged();
    }

    public void setOnItemClickedListener(FileListAdapterListener onItemClickedListener) {
        this.mFileListAdapterListener = onItemClickedListener;
    }

    public ArrayList<JCFile> getSelectedFiles() {
        return selectedFiles;
    }

    public void resetSelectedFiles() {
        this.selectedFiles = new ArrayList<>();
        for(JCFile file : files){
            file.setSelected(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return files != null && files.size() > 0 ? VIEW_TYPE_FILE : VIEW_TYPE_NO_FILE;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case VIEW_TYPE_FILE:
                viewHolder = getFileCell(parent);
                break;
            case VIEW_TYPE_NO_FILE:
                viewHolder = geNotFileCell(parent);
                break;
        }
        return viewHolder;
    }

    protected RecyclerView.ViewHolder geNotFileCell(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_file, parent, false);
        return new JCEmptyFileListItem(view);
    }

    protected RecyclerView.ViewHolder getFileCell(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
        return new JCFileListItem(view);
    }


    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case VIEW_TYPE_FILE:
                setupItemView(holder, position);
                break;
            case VIEW_TYPE_NO_FILE:
                if(holder instanceof JCEmptyFileListItem) {
                    setupNoFileCell((JCEmptyFileListItem) holder);
                }
                break;
        }
    }

    protected void setupNoFileCell(JCEmptyFileListItem holder) {
    }

    protected void setupItemView(RecyclerView.ViewHolder holder, final int position) {
        final JCFileListItem viewHolder = (JCFileListItem) holder;
        final JCFile file = files.get(position);
        final Context context = holder.itemView.getContext();
        boolean shouldDisableInteraction = shouldDisableInteraction(context, file);

        boolean hasFileBeenSelected = FileUtils.isFileExistInGivenFiles(file, selectedFiles);
        if(hasFileBeenSelected){
            file.setSelected(true);
        }

        if(viewHolder.txt_size != null){
            viewHolder.txt_size.setVisibility(file.isFolder() ? View.GONE: View.VISIBLE);
            viewHolder.txt_size.setText(FileUtils.getSizeString(file.getSize(), false));
            viewHolder.txt_size.setTextColor(context.getResources().getColor(
                    shouldDisableInteraction ? R.color.jc_divider_color : R.color.jc_light_grey));
        }

        if(viewHolder.txt_name != null){
            viewHolder.txt_name.setText(file.getName());
            viewHolder.txt_name.setTextColor(context.getResources().getColor(
                    shouldDisableInteraction ? R.color.jc_divider_color : R.color.jc_file_name_color));
        }



        if(viewHolder.checkBox != null){
            if(shouldDisableInteraction
                    || JCPickerClient.getDefaultInstance(context).isSinglePicking()
                    || file.isFolder() && !JCPickerClient.getDefaultInstance(context).isAllowSelectDir())
            {
                viewHolder.checkBox.setVisibility(View.GONE);
            }
            else{
                viewHolder.checkBox.setVisibility(View.VISIBLE);
            }

            viewHolder.setSelect(file.isSelected());

            if(!shouldDisableInteraction) {
                viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.itemView.performClick();
                    }
                });
            }
        }

        if(viewHolder.overlay != null){
            viewHolder.overlay.setVisibility(file.isSelected() || JCPickerClient.getDefaultInstance(context).isSinglePicking() ? View.GONE : View.VISIBLE);
        }

        //setup image
        if(file.isImage()){
            Picasso.with(context)
                    .load(new File(file.getUrl()))
                    .fit()
                    .centerCrop()
                    .placeholder(getFileIconPlaceHolder(file))
                    .into(viewHolder.iv_icon);
        }
        else if(file.isVideo()){
            //TODO
        }
        else{
            viewHolder.iv_icon.setImageDrawable(context.getResources().getDrawable(file.getFileImageResID()));
        }

        int padding = getPadding(context, file);
        viewHolder.iv_icon.setPadding(padding, padding, padding, padding);

        //setup listener
        if(viewHolder.checkBox != null){
            viewHolder.setSelect(file.isSelected());

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.itemView.performClick();
                }
            });
        }

        if(!shouldDisableInteraction){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mFileListAdapterListener != null){
                        if(file.isFolder() && !JCPickerClient.getDefaultInstance(context).isAllowSelectDir()
                                || file.isAlbum()){
                            mFileListAdapterListener.onOpenFolder(file, position);
                        }else{
                            if(JCPickerClient.getDefaultInstance(context).getMaxSelectedItemCount() > 0
                                    && selectedFiles.size() >= JCPickerClient.getDefaultInstance(context).getMaxSelectedItemCount()
                                    && !file.isSelected())
                            {
                                if(mFileListAdapterListener != null){
                                    mFileListAdapterListener.onSelectedItemReachLimitation();
                                }
                                return;
                            }

                            file.setSelected(!file.isSelected());
                            if(file.isSelected()){
                                selectedFiles.add(file);
                            }
                            else{
                                selectedFiles.remove(file);
                            }
                            viewHolder.setSelect(file.isSelected());
                            if(viewHolder.overlay != null){
                                viewHolder.overlay.setVisibility(file.isSelected() ? View.GONE : View.VISIBLE);
                            }

                            if(mFileListAdapterListener != null){
                                mFileListAdapterListener.onItemSelected(selectedFiles);
                            }
                        }
                    }
                }
            });
        }

        if(!shouldDisableInteraction){
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(file.isImage() && !file.isAlbum()){
                        if(mFileListAdapterListener != null){
                            mFileListAdapterListener.onOpenImage(file, position);
                        }
                    }
                    else if(file.isFolder() && JCPickerClient.getDefaultInstance(context).isAllowSelectDir()){
                        if(mFileListAdapterListener != null){
                            mFileListAdapterListener.onOpenFolder(file, position);
                        }
                    }

                    return false;
                }
            });

        }
    }

    private boolean shouldDisableInteraction(Context context, JCFile file) {
        boolean shouldHandleClick = false;
        if(file.isFile() && !JCPickerClient.getDefaultInstance(context).isAllowSelectFile()){
            shouldHandleClick = true;
        }
        return shouldHandleClick;
    }
    
    protected int getFileIconPlaceHolder(JCFile file){
        return file.isImage() ?  R.drawable.jcpick_placeholder : file.getFileImageResID();
    }

    protected int getPadding(Context context, JCFile file) {
        return 0;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if(files != null){
            count = files.size() == 0 ? 1: files.size();
        }
        return count;
    }




    public interface FileListAdapterListener {
        void onOpenFolder(JCFile file, int position);
        void onOpenImage(JCFile file, int position);
        void onItemSelected(ArrayList<JCFile> selectedFiles);
        void onSelectedItemReachLimitation();
    }
}
