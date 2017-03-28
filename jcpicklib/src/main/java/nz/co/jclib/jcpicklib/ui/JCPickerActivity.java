package nz.co.jclib.jcpicklib.ui;

import android.os.Bundle;

import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.ui.base.JCPickerBaseActivity;

public class JCPickerActivity extends JCPickerBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
