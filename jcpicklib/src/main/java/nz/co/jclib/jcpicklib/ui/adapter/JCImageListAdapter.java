package nz.co.jclib.jcpicklib.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.data.model.JCFile;
import nz.co.jclib.jcpicklib.ui.adapter.viewHolder.JCAlbumItem;
import nz.co.jclib.jcpicklib.ui.adapter.viewHolder.JCFileListItem;
import nz.co.jclib.jcpicklib.ui.adapter.viewHolder.JCImageItem;

/**
 * Created by Johnnie on 19/04/17.
 */
public class JCImageListAdapter extends JCAlbumListAdapter{

    @Override
    protected RecyclerView.ViewHolder getFileCell(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new JCImageItem(view);
    }

}
