package nz.co.jclib.jcpicklib.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nz.co.jclib.jcpicklib.R;


/**
 * Created by Johnnie on 18/03/16.
 */
public class JCEmptyFileListItem extends RecyclerView.ViewHolder {

    private View view;
    public TextView txtDesc;
    public ImageView ivImage;

    public JCEmptyFileListItem(View view) {
        super(view);
        initView(view);
    }

    private void initView(View view) {
        txtDesc = (TextView) view.findViewById(R.id.txt_empty_file_desc);
        ivImage = (ImageView) view.findViewById(R.id.iv_image);
    }
}
