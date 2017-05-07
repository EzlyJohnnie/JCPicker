package nz.co.jclib.jcpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import nz.co.jclib.jcpicklib.JCPickerClient;
import nz.co.jclib.jcpicklib.data.model.JCFile;
import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.utils.JCConstant;

public class MainActivity extends AppCompatActivity implements JCPickerClient.JCPickerListener{
    protected static final String KEY_MAIN_FRAGMENT = "key_mainFragment";

    private View btnStartActivity;
    private View btnGetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnStartActivity = findViewById(R.id.btn_start_activity);
        btnGetFragment = findViewById(R.id.btn_get_fragment);

        btnStartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final JCPickerClient client = JCPickerClient.Builder(MainActivity.this)
                        .setPickerType(JCConstant.PICK_TYPE_IMAGE)
                        .setMaxSelectedItemCount(3)
                        .addPickSource(JCConstant.PICK_SOURCE_DIRECTORY)
                        .build();

                client.startPickerActivity(MainActivity.this, MainActivity.this);
            }
        });

        btnGetFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final JCPickerClient client = JCPickerClient.Builder(MainActivity.this)
                        .setPickerType(JCConstant.PICK_TYPE_IMAGE)
                        .setMaxSelectedItemCount(1)
                        .build();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, client.getPickerFragment(MainActivity.this), KEY_MAIN_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }
        else{
            //TODO: set result and finish
            finish();
        }
    }

    @Override
    public void onFilePicked(ArrayList<JCFile> files) {
        for(JCFile file : files){
            Log.e("JCPicker", file.getName() + ": " + file.getUrl());
        }
    }
}
