package nz.co.jclib.jcpicklib;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import nz.co.jclib.jcpicklib.data.model.JCFile;
import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.ui.JCPickerActivity;
import nz.co.jclib.jcpicklib.ui.JCPickerHostFragment;

/**
 * Created by Johnnie on 28/03/17.
 */
public class JCPickerClient {

    private static JCPickerClient instance;
    private JCPickerListener mListener;

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

    public Fragment getPickerFragment(JCPickerListener listener){
        return getPickerFragment(null, listener);
    }

    public Fragment getPickerFragment(JCPickerEnterOption enterOption, JCPickerListener listener){
        if(listener != null){
            instance.mListener = listener;
        }

        return JCPickerHostFragment.getInstance(enterOption);
    }

    public void startPickerActivity(Activity activity, JCPickerListener listener){
        startPickerActivity(activity, null, listener);
    }

    public void startPickerActivity(Activity activity, JCPickerEnterOption enterOption, JCPickerListener listener){
        if(listener != null){
            instance.mListener = listener;
        }

        JCPickerActivity.createAndstartActivity(activity, enterOption);
    }



    public void completePicker(ArrayList<JCFile> selectedFiles){
        if(mListener != null){
            mListener.onFilePicked(selectedFiles);
        }
        reset();
    }


    public interface JCPickerListener{
        void onFilePicked(ArrayList<JCFile> files);
    }
}
