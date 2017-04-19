package nz.co.jclib.jcpicklib;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.ui.JCPickerActivity;
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

    public static void reset(){
        instance = null;
    }

    public Fragment getPickerFragment(){
        return getPickerFragment(null);
    }

    public Fragment getPickerFragment(JCPickerEnterOption enterOption){
        return JCPickerHostFragment.getInstance(enterOption);
    }

    public void startPickerActivity(Activity activity){
        startPickerActivity(activity, null);
    }

    public void startPickerActivity(Activity activity, JCPickerEnterOption enterOption){
        JCPickerActivity.createAndstartActivity(activity, enterOption);
    }
}
