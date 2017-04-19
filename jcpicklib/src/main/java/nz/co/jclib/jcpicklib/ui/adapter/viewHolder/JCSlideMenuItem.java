package nz.co.jclib.jcpicklib.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nz.co.jclib.jcpicklib.R;

/**
 * Created by Johnnie on 19/04/17.
 */
public class JCSlideMenuItem extends RecyclerView.ViewHolder {

    public ImageView ivIcon;
    public TextView txtTitle;
    public View bottomDivider;

    public JCSlideMenuItem(View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View itemView) {
        ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
        bottomDivider =  itemView.findViewById(R.id.bottom_divider);
    }
}
