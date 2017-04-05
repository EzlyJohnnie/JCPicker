package nz.co.jclib.jcpicklib.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.ui.base.JCPickerBaseFragment;
import nz.co.jclib.jcpicklib.ui.fragment.filePicker.JCFilePickerFragment;
import nz.co.jclib.jcpicklib.ui.fragment.imagePicker.JCAlbumPickerFragment;
import nz.co.jclib.jcpicklib.utils.JCConstant;

/**
 * Created by Johnnie on 4/04/17.
 */
public class JCPickerHostFragment extends JCPickerBaseFragment {
    protected static final String KEY_MAIN_FRAGMENT = "key_mainFragment";
    protected static final String KEY_ENTER_OPTION = "key_enterOption";

    private JCPickerEnterOption enterOption;

    public static Fragment getInstance(JCPickerEnterOption enterOption) {
        JCPickerHostFragment fragment = new JCPickerHostFragment();
        Bundle args = new Bundle();
        if(enterOption != null){
            args.putParcelable(KEY_ENTER_OPTION, enterOption);
        }
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.host_fragment, container, false);
        init(savedInstanceState);
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_ENTER_OPTION, enterOption);
        super.onSaveInstanceState(outState);
    }

    private void init(Bundle savedInstanceState) {
        prepareData(savedInstanceState);
        initView();
    }

    private void prepareData(Bundle savedInstanceState) {
        if(savedInstanceState == null & getArguments() != null){
            enterOption = getArguments().getParcelable(KEY_ENTER_OPTION);
            if(enterOption == null){
                enterOption = JCPickerEnterOption.createDefaultOption(getContext());
            }
        }
        else{
            enterOption = savedInstanceState.getParcelable(KEY_ENTER_OPTION);
        }
    }

    private void initView() {
        initLandingFragment();
        initMenu();
    }

    private void initLandingFragment() {
        Fragment landingFragment = null;
        switch (enterOption.getPickType()){
            case JCConstant.PICK_TYPE_IMAGE:
                if(TextUtils.isEmpty(enterOption.getPath())){
                    landingFragment = JCAlbumPickerFragment.getInstance(enterOption);
                }
                else{
                    //TODO: implement landing on path, show album list or image list
                }
                break;

            case JCConstant.PICK_TYPE_FILE:
            default:
                landingFragment = JCFilePickerFragment.getInstance(enterOption);
                break;
        }
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_host, landingFragment, KEY_MAIN_FRAGMENT)
                .commit();
    }

    private void initMenu() {

    }


}
