package nz.co.jclib.jcpicklib.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.data.model.JCFile;
import nz.co.jclib.jcpicklib.ui.adapter.viewHolder.JCAlbumItem;
import nz.co.jclib.jcpicklib.ui.adapter.viewHolder.JCEmptyFileListItem;
import nz.co.jclib.jcpicklib.ui.adapter.viewHolder.JCFileListItem;
import nz.co.jclib.jcpicklib.utils.FileUtils;

/**
 * Created by Johnnie on 18/04/17.
 */
public class JCAlbumListAdapter extends JCBaseFileListAdapter {

    @Override
    protected RecyclerView.ViewHolder getFileCell(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new JCAlbumItem(view);
    }

    @Override
    protected void setupNoFileCell(JCEmptyFileListItem holder) {
        Context context = holder.itemView.getContext();
        holder.txtDesc.setText(context.getResources().getText(R.string.jc_empty_album_desc));
        holder.ivImage.setImageDrawable(context.getResources().getDrawable(R.drawable.jcpick_no_image_icon));
    }

    @Override
    protected void setupItemView(RecyclerView.ViewHolder holder, final int position) {
        final JCFileListItem viewHolder = (JCFileListItem) holder;
        final JCFile file = files.get(position);

        if(viewHolder.txt_name != null){
            viewHolder.txt_name.setText(file.getName());
        }

        super.setupItemView(holder, position);
    }
}
