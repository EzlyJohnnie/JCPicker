package nz.co.jclib.jcpicklib.ui.adapter.viewHolder;

import android.view.View;
import android.widget.ImageView;

import nz.co.jclib.jcpicklib.R;

/**
 * Created by Johnnie on 19/04/17.
 */
public class JCImageItem extends JCFileListItem {

    public JCImageItem(View view) {
        super(view);
    }

    @Override
    protected void initView(View view) {
        iv_icon = (ImageView) view.findViewById(R.id.iv_file_icon);
        checkBox = (ImageView) view.findViewById(R.id.check_box);
        overlay = view.findViewById(R.id.overlay);
    }
}
