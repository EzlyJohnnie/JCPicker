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
import nz.co.jclib.jcpicklib.utils.JCConstant;

/**
 * Created by Johnnie on 28/03/17.
 */
public class JCPickerClient {
    private static final int MAX_SELECTED_ITEM_COUNT_UNLIMITED = -1;
    private static final int MAX_SELECTED_ITEM_COUNT_UNDEFINED = -999;

    private static JCPickerClient instance;
    private JCPickerListener mListener;
    private JCPickerEnterOption enterOption;
    private int maxSelectedItemCount = MAX_SELECTED_ITEM_COUNT_UNLIMITED;
    private int pickSource;
    private ArrayList<Integer> pickSources;

    public static JCPickerClient getDefaultInstance(Context context){
        if(instance == null){
            instance = new JCPickerClient(context);
        }

        return instance;
    }

    private JCPickerClient(Context context) {
        enterOption = JCPickerEnterOption.createDefaultOption(context);
        pickSources = new ArrayList<>();
    }

    public static void reset(){
        instance = null;
    }

    public int getMaxSelectedItemCount() {
        return maxSelectedItemCount;
    }

    /**
     *
     * @return is can only pick one itme
     */
    public boolean isSinglePicking(){
        return maxSelectedItemCount == 1;
    }

    public boolean isAllowSelectDir() {
        return pickSources.contains(JCConstant.PICK_SOURCE_DIRECTORY);
    }

    public boolean isAllowSelectImage(){
        return pickSources.contains(JCConstant.PICK_SOURCE_IMAGE);
    }

    public boolean isAllowSelectFile(){
        return pickSources.contains(JCConstant.PICK_SOURCE_FILE);
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
        private int maxSelectedItemCount = MAX_SELECTED_ITEM_COUNT_UNDEFINED;
        private ArrayList<Integer> pickSources;
        private Context context;

        public Builder(Context context) {
            this.context = context;
            pickSources = new ArrayList<>();
        }

        public Builder setEnterOption(JCPickerEnterOption enterOption) {
            this.enterOption = enterOption;
            return this;
        }

        /**
         * set maximum count for items
         * default (OR set a negative value) for unlimited
         * @param maxSelectedItemCount
         * @return
         */
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

        public Builder addPickSource(@JCConstant.PickSource int pickSource) {
            if(!pickSources.contains(pickSource)) {
                pickSources.add(pickSource);
            }
            return this;
        }

        public Builder removePickSource(@JCConstant.PickSource int pickSource) {
            pickSources.remove(pickSource);
            return this;
        }

        public JCPickerClient build(){
            instance = new JCPickerClient(context);
            if(enterOption != null){
                instance.enterOption = enterOption;
            }

            if(maxSelectedItemCount != MAX_SELECTED_ITEM_COUNT_UNDEFINED){
                instance.maxSelectedItemCount = maxSelectedItemCount;
            }

            if(pickSources != null && pickSources.size() > 0){
                instance.pickSources = pickSources;
            }

            return instance;
        }
    }

    public interface JCPickerListener{
        void onFilePicked(ArrayList<JCFile> files);
    }
}
