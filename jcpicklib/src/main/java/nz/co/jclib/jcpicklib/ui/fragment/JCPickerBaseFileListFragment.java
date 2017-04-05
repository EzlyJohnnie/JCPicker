package nz.co.jclib.jcpicklib.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.ui.base.JCPickerBaseFragment;

/**
 * Created by Johnnie on 28/03/17.
 */
public class JCPickerBaseFileListFragment extends JCPickerBaseFragment {

    protected static final String KEY_ENTER_OPTION = "key_enterOption";

    private JCPickerEnterOption enterOption;

    public static Fragment getInstance(JCPickerEnterOption enterOption) {
        JCPickerBaseFileListFragment fragment = new JCPickerBaseFileListFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_ENTER_OPTION, enterOption);
        fragment.setArguments(args);
        return fragment;
    }

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
    }

    private void initLandingFragment() {

    }

}
