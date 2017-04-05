package nz.co.jclib.jcpicklib;

import android.support.v4.app.Fragment;

import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.ui.JCPickerHostFragment;

/**
 * Created by Johnnie on 28/03/17.
 */
public class JCPickerClient {

    private static JCPickerClient instance;

    public static JCPickerClient getInstance(){
        if(instance == null){
            instance = new JCPickerClient();
        }

        return instance;
    }

    private JCPickerClient() {}

    public Fragment getPickerFragment(){
        return getPickerFragment(null);
    }

    public Fragment getPickerFragment(JCPickerEnterOption enterOption){
        return JCPickerHostFragment.getInstance(enterOption);
    }
}
