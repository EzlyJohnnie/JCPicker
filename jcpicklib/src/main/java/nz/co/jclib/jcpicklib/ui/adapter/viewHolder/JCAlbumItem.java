package nz.co.jclib.jcpicklib.ui.adapter.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nz.co.jclib.jcpicklib.R;

/**
 * Created by Johnnie on 19/04/17.
 */
public class JCAlbumItem extends JCFileListItem {

    public JCAlbumItem(View view) {
        super(view);
    }

    @Override
    protected void initView(View view) {
        iv_icon = (ImageView) view.findViewById(R.id.iv_file_icon);
        txt_name = (TextView) view.findViewById(R.id.txt_name);
    }
}
