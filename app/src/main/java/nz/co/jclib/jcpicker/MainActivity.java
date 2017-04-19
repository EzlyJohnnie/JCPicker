package nz.co.jclib.jcpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nz.co.jclib.jcpicklib.JCPickerClient;
import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.utils.JCConstant;

public class MainActivity extends AppCompatActivity {
    protected static final String KEY_MAIN_FRAGMENT = "key_mainFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jc_picker_acticity);
        initLandingFragment();
    }

    private void initLandingFragment() {
        JCPickerEnterOption enterOption = new JCPickerEnterOption();
        enterOption.setPickType(JCConstant.PICK_TYPE_IMAGE);
        JCPickerClient.getInstance().startPickerActivity(this, enterOption);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container, JCPickerClient.getInstance().getPickerFragment(enterOption), KEY_MAIN_FRAGMENT)
//                .commit();
    }
}
