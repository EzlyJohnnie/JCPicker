package nz.co.jclib.jcpicklib.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nz.co.jclib.jcpicklib.R;


/**
 * Created by Johnnie on 18/03/16.
 */
public class JCFileListItem extends RecyclerView.ViewHolder {

    public ImageView iv_icon;
    public TextView txt_name;
    public TextView txt_size;
    public ImageView checkBox;
    public View bottomDivider;
    public View overlay;


    public JCFileListItem(View view) {
        super(view);
        initView(view);
    }

    protected void initView(View view) {
        iv_icon = (ImageView) view.findViewById(R.id.iv_file_icon);
        txt_name = (TextView) view.findViewById(R.id.txt_file_name);
        checkBox = (ImageView) view.findViewById(R.id.check_box);
        txt_size = (TextView) view.findViewById(R.id.txt_folder_size);
        bottomDivider = view.findViewById(R.id.bottom_divider);
    }

    public void setSelect(boolean isSelect){
        if(checkBox != null){
            checkBox.setImageDrawable(checkBox.getContext().getResources().getDrawable(isSelect ? R.drawable.jcpick_file_selected : R.drawable.jcpick_file_unselect));
        }
    }
}
