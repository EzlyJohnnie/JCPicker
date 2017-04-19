package nz.co.jclib.jcpicklib.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.data.model.JCFile;
import nz.co.jclib.jcpicklib.ui.adapter.viewHolder.JCEmptyFileListItem;
import nz.co.jclib.jcpicklib.ui.adapter.viewHolder.JCFileListItem;
import nz.co.jclib.jcpicklib.utils.FileUtils;

/**
 * Created by Johnnie on 19/04/17.
 */
public class JCFileListAdapter extends JCBaseFileListAdapter {

    @Override
    protected void setupNoFileCell(JCEmptyFileListItem holder) {
        Context context = holder.itemView.getContext();
        holder.txtDesc.setText(context.getResources().getText(R.string.jc_empty_file_desc));
        holder.ivImage.setImageDrawable(context.getResources().getDrawable(R.drawable.jcpick_no_file_icon));
    }
}
