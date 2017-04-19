package nz.co.jclib.jcpicklib.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.ui.adapter.JCSlideMenuAdapter;
import nz.co.jclib.jcpicklib.ui.base.JCPickerBaseFragment;
import nz.co.jclib.jcpicklib.ui.fragment.JCPickerBaseFileListFragment;
import nz.co.jclib.jcpicklib.utils.UIHelper;

/**
 * Created by Johnnie on 4/04/17.
 */
public class JCPickerHostFragment extends JCPickerBaseFragment implements JCSlideMenuAdapter.SlideMenuItemClickedListener{
    protected static final String KEY_MAIN_FRAGMENT = "key_mainFragment";
    protected static final String KEY_ENTER_OPTION = "key_enterOption";
    protected static final String KEY_SELECTED_MENU_INDEX = "key_selectedMenuIndex";

    private JCPickerEnterOption enterOption;

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;

    private RecyclerView slideMenuList;
    private JCSlideMenuAdapter slideMenuAdapter;
    private int selectedMenuIndex;

    public static Fragment getInstance(JCPickerEnterOption enterOption) {
        JCPickerHostFragment fragment = new JCPickerHostFragment();
        Bundle args = new Bundle();
        if(enterOption != null){
            args.putParcelable(KEY_ENTER_OPTION, enterOption);
        }
        fragment.setArguments(args);
        return fragment;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    //    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.host_fragment, container, false);
        init(savedInstanceState, root);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initToolbar();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_ENTER_OPTION, enterOption);
        outState.putInt(KEY_SELECTED_MENU_INDEX, selectedMenuIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    private void init(Bundle savedInstanceState, View root) {
        prepareData(savedInstanceState);
        initView(root);
    }

    private void prepareData(Bundle savedInstanceState) {
        if(savedInstanceState == null & getArguments() != null){
            enterOption = getArguments().getParcelable(KEY_ENTER_OPTION);
            if(enterOption == null){
                enterOption = JCPickerEnterOption.createDefaultOption(getContext());
            }

            selectedMenuIndex = enterOption.getPickType();
        }
        else{
            enterOption = savedInstanceState.getParcelable(KEY_ENTER_OPTION);
            selectedMenuIndex = savedInstanceState.getInt(KEY_SELECTED_MENU_INDEX);
        }
    }

    private void initView(View root) {
        initViewComponents(root);
        initLandingFragment();
        initSlideMenu();
    }

    private void initToolbar() {
        Resources resources = getContext().getResources();
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(resources.getColor(R.color.jc_toolbar_bg_color));
        toolbar.setTitleTextColor(resources.getColor(R.color.jc_toolbar_title_color));
        toolbar.setSubtitleTextColor(resources.getColor(R.color.jc_toolbar_subtitle_color));
    }


    private void initViewComponents(View root) {
        mDrawerLayout = (DrawerLayout) root.findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        slideMenuList = (RecyclerView) root.findViewById(R.id.slide_menu_list);
    }

    private void initLandingFragment() {
        Fragment landingFragment = JCPickerBaseFileListFragment.getInstance(enterOption);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_host, landingFragment, KEY_MAIN_FRAGMENT)
                .commit();
    }

    private void initSlideMenu() {
        slideMenuAdapter = new JCSlideMenuAdapter(getContext(), selectedMenuIndex);
        slideMenuAdapter.setListener(this);
        slideMenuList.setAdapter(slideMenuAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        slideMenuList.setLayoutManager(layoutManager);
    }


    public void pushFragment(Fragment fragment){
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                        R.anim.slide_left_in, R.anim.slide_right_out)
                .replace(R.id.fragment_host, fragment, KEY_MAIN_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }


    public void toggleSlideMenu(){
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
        else{
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    ///////////////////////////////////// Adapter ////////////////////////////////////
    @Override
    public void onImagesClicked(int selectedMenuIndex) {
        toggleSlideMenu();
        this.selectedMenuIndex = selectedMenuIndex;
        enterOption = JCPickerEnterOption.createPickImageOption();
        initLandingFragment();
    }

    @Override
    public void onFilesClicked(int selectedMenuIndex) {
        toggleSlideMenu();
        this.selectedMenuIndex = selectedMenuIndex;
        enterOption = JCPickerEnterOption.createPickFileOption();
        initLandingFragment();
    }

    public boolean pop(){
        boolean hasPop = false;
        Fragment fragment = getChildFragmentManager().findFragmentByTag(KEY_MAIN_FRAGMENT);
        if(fragment != null && fragment instanceof JCBackableFragment){
            hasPop = ((JCBackableFragment)fragment).onBackClicked();
        }

        if (!hasPop && getChildFragmentManager().getBackStackEntryCount() > 0) {
            hasPop = true;
            UIHelper.hideKeyBoard(getView());
            getChildFragmentManager().popBackStackImmediate();
            if(fragment instanceof JCToolbarFragment){
                ((JCToolbarFragment)getChildFragmentManager().findFragmentByTag(KEY_MAIN_FRAGMENT)).updateToolbar();
            }
        }
        return hasPop;
    }
}
