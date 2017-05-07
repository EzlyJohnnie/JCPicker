package nz.co.jclib.jcpicklib;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

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
    private static final int INT_UNDEFINED = -999;

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
        private int maxSelectedItemCount = INT_UNDEFINED;
        private ArrayList<Integer> pickSources;
        private Context context;
        private int pickerType = INT_UNDEFINED;

        public Builder(Context context) {
            this.context = context;
            pickSources = new ArrayList<>();
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

        /**
         * set landing picker type image or file
         * landing picker type will be ignored if only has one type
         * @param pickerType
         * @return
         */
        public Builder setLandingPickerType(@JCConstant.PickType int pickerType) {
            this.pickerType = pickerType;
            return this;
        }

        public Builder addPickSource(@JCConstant.PickSource int pickSource) {
            if(pickSource == JCConstant.PICK_SOURCE_ALL){
                addPickSource(JCConstant.PICK_SOURCE_DIRECTORY);
                addPickSource(JCConstant.PICK_SOURCE_FILE);
                addPickSource(JCConstant.PICK_SOURCE_IMAGE);
            }
            else if(!pickSources.contains(pickSource)) {
                pickSources.add(pickSource);
            }
            return this;
        }

        public Builder removePickSource(@JCConstant.PickSource int pickSource) {
            if(pickSource == JCConstant.PICK_SOURCE_ALL){
                pickSources.clear();
            }
            else{
                pickSources.remove(pickSource);
            }

            return this;
        }

        /**
         * default: select image and file
         *          max selected item: unlimited
         *          landing on: image picker
         * @return JCPickerClient
         */
        public JCPickerClient build(){
            instance = new JCPickerClient(context);

            if(pickSources.isEmpty()){//set default
                pickSources.add(JCConstant.PICK_SOURCE_IMAGE);
                pickSources.add(JCConstant.PICK_SOURCE_FILE);
            }

            JCPickerEnterOption enterOption = new JCPickerEnterOption();
            if(pickSources.size() == 1 && pickSources.contains(JCConstant.PICK_SOURCE_IMAGE)){
                //pick image only
                pickerType = JCConstant.PICK_TYPE_IMAGE;
            }
            else if(!pickSources.contains(JCConstant.PICK_SOURCE_IMAGE)){
                //file and directory only
                pickerType = JCConstant.PICK_TYPE_FILE;
            }
            else if(pickerType == INT_UNDEFINED){
                pickerType = JCConstant.PICK_TYPE_IMAGE;
            }
            enterOption.setPickType(pickerType);
            instance.enterOption = enterOption;


            if(maxSelectedItemCount != INT_UNDEFINED){
                instance.maxSelectedItemCount = maxSelectedItemCount;
            }

            if(pickSources.size() > 0){
                instance.pickSources = pickSources;
            }

            return instance;
        }
    }

    public interface JCPickerListener{
        void onFilePicked(ArrayList<JCFile> files);
    }
}
