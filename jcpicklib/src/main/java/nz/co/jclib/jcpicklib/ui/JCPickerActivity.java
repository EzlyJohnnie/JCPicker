package nz.co.jclib.jcpicklib.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import nz.co.jclib.jcpicklib.JCPickerClient;
import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.ui.base.JCPickerBaseActivity;

public class JCPickerActivity extends JCPickerBaseActivity {
    protected static final String KEY_MAIN_FRAGMENT = "key_mainFragment";
    protected static final String KEY_ENTER_OPTION = "key_enterOption";


    public static void createAndstartActivity(Activity activity, JCPickerEnterOption enterOption) {
        Intent intent = new Intent(activity, JCPickerActivity.class);
        intent.putExtra(KEY_ENTER_OPTION, enterOption);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jc_picker_acticity);
        setLandingFragment();
    }

    private void setLandingFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, JCPickerClient.getDefaultInstance(this).getPickerFragment(null), KEY_MAIN_FRAGMENT)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(KEY_MAIN_FRAGMENT);
        if(fragment instanceof JCPickerHostFragment && ((JCPickerHostFragment)fragment).pop()){
            return;
        }
        else{
            //TODO: set result and finish
            JCPickerClient.reset();
            finish();
        }
    }

}
