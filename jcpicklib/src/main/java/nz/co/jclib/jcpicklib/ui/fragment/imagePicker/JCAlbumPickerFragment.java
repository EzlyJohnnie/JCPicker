package nz.co.jclib.jcpicklib.ui.fragment.imagePicker;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.ui.fragment.JCPickerBaseFileListFragment;

/**
 * Created by Johnnie on 28/03/17.
 */
public class JCAlbumPickerFragment extends JCPickerBaseFileListFragment{

    public static Fragment getInstance(JCPickerEnterOption enterOption) {
        JCAlbumPickerFragment fragment = new JCAlbumPickerFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_ENTER_OPTION, enterOption);
        fragment.setArguments(args);
        return fragment;
    }
}
