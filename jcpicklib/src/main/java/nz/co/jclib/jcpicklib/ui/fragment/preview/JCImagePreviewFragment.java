package nz.co.jclib.jcpicklib.ui.fragment.preview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.ui.JCPickerHostFragment;
import nz.co.jclib.jcpicklib.ui.Widget.JCZoomableImageView;
import nz.co.jclib.jcpicklib.ui.base.JCPickerBaseFragment;

/**
 * Created by Johnnie on 20/04/17.
 */
public class JCImagePreviewFragment extends JCPickerBaseFragment {

    protected static final String KEY_PATH = "key_path";

    private String path;
    private File file;
    private JCZoomableImageView imageView;

    public static Fragment getInstance(String path) {
        Fragment fragment = new JCImagePreviewFragment();
        Bundle args = new Bundle();
        args.putString(KEY_PATH, path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_image_preview, container, false);
        init(root, savedInstanceState);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initToolbar();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_PATH, path);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu,inflater);
    }

    private void prepareData(Bundle savedInstanceState) {
        if(savedInstanceState == null && getArguments() != null){
            path = getArguments().getString(KEY_PATH);
        }
        else{
            path = savedInstanceState.getString(KEY_PATH);
        }

        file = new File(path);
    }

    private void init(View root, Bundle savedInstanceState) {
        prepareData(savedInstanceState);
        initViewComponents(root);
        initView();
    }

    private void initViewComponents(View root) {
        imageView = (JCZoomableImageView) root.findViewById(R.id.image_view);
    }


    private void initToolbar() {
        Toolbar toolbar = null;
        if(getParentFragment() instanceof JCPickerHostFragment){
            toolbar = ((JCPickerHostFragment) getParentFragment()).getToolbar();
        }

        if(toolbar != null){
            toolbar.setTitle(file.getName());
            toolbar.setSubtitle("");

            toolbar.setNavigationIcon(R.drawable.jcpick_back_icon);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((JCPickerHostFragment) getParentFragment()).pop();
                }
            });
        }
    }

    private void initView() {
        imageView.setAllowZoom(true);
        Picasso.with(getContext())
                .load(file)
                .fit()
                .centerInside()
                .into(imageView);
    }
}
