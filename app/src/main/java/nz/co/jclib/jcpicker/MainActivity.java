package nz.co.jclib.jcpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nz.co.jclib.jcpicklib.JCPickerClient;

public class MainActivity extends AppCompatActivity {
    protected static final String KEY_MAIN_FRAGMENT = "key_mainFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLandingFragment();
    }

    private void initLandingFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, JCPickerClient.getInstance().getPickerFragment(), KEY_MAIN_FRAGMENT)
                .commit();
    }
}
