package nz.co.jclib.jcpicklib;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentNavigableMap;

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
    private JCPickerEnterOption enterOption;
    private int maxSelectedItemCount;

    public static JCPickerClient getDefaultInstance(Context context){
        if(instance == null){
            instance = new JCPickerClient(context);
        }

        return instance;
    }

    private JCPickerClient(Context context) {
        enterOption = JCPickerEnterOption.createDefaultOption(context);
    }

    public static void reset(){
        instance = null;
    }

    public int getMaxSelectedItemCount() {
        return maxSelectedItemCount;
    }

    public Fragment getPickerFragment(JCPickerListener listener){
        if(listener != null){
            instance.mListener = listener;
        }

        return JCPickerHostFragment.getInstance(enterOption);
    }

    public void startPickerActivity(Activity activity, JCPickerListener listener){
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

    public static Builder Builder(Context context){
        return new Builder(context);
    }


    public static class Builder{
        private JCPickerEnterOption enterOption;
        private int maxSelectedItemCount;
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setEnterOption(JCPickerEnterOption enterOption) {
            this.enterOption = enterOption;
            return this;
        }

        public Builder setMaxSelectedItemCount(int maxSelectedItemCount) {
            this.maxSelectedItemCount = maxSelectedItemCount;
            return this;
        }

        public Builder setPickerType(int pickerType) {
            if(enterOption == null){
                enterOption = new JCPickerEnterOption();
            }
            enterOption.setPickType(pickerType);
            return this;
        }

        public JCPickerClient build(){
            instance = new JCPickerClient(context);
            if(enterOption != null){
                instance.enterOption = enterOption;
            }
            instance.maxSelectedItemCount = maxSelectedItemCount;
            return instance;
        }
    }

    public interface JCPickerListener{
        void onFilePicked(ArrayList<JCFile> files);
    }
}
