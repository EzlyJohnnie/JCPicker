package nz.co.jclib.jcpicklib.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import nz.co.jclib.jcpicklib.JCPickerClient;
import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.data.model.JCFile;
import nz.co.jclib.jcpicklib.data.model.JCPickerEnterOption;
import nz.co.jclib.jcpicklib.presenter.JCFileListPresenter;
import nz.co.jclib.jcpicklib.ui.JCPickerActivity;
import nz.co.jclib.jcpicklib.ui.JCPickerHostFragment;
import nz.co.jclib.jcpicklib.ui.JCToolbarFragment;
import nz.co.jclib.jcpicklib.ui.JCBackableFragment;
import nz.co.jclib.jcpicklib.ui.Widget.JCSingleToast;
import nz.co.jclib.jcpicklib.ui.adapter.JCBaseFileListAdapter;
import nz.co.jclib.jcpicklib.ui.adapter.JCFileListAdapter;
import nz.co.jclib.jcpicklib.ui.base.JCPickerBaseFragment;
import nz.co.jclib.jcpicklib.ui.fragment.filePicker.JCFilePickerFragment;
import nz.co.jclib.jcpicklib.ui.fragment.imagePicker.JCAlbumPickerFragment;
import nz.co.jclib.jcpicklib.ui.fragment.imagePicker.JCImagePickerFragment;
import nz.co.jclib.jcpicklib.ui.fragment.preview.JCImagePreviewFragment;
import nz.co.jclib.jcpicklib.ui.viewinterface.JCFileListView;
import nz.co.jclib.jcpicklib.utils.JCConstant;

/**
 * Created by Johnnie on 28/03/17.
 */
public class JCPickerBaseFileListFragment extends JCPickerBaseFragment implements JCBaseFileListAdapter.FileListAdapterListener,
        JCFileListView,
        JCToolbarFragment,
        JCBackableFragment
{

    protected static final String KEY_ENTER_OPTION = "key_enterOption";

    protected JCPickerEnterOption enterOption;
    protected JCFileListPresenter presenter;

    private SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView fileList;
    protected JCBaseFileListAdapter adapter;
    protected ArrayList<JCFile> files;
    private int previousSelectedFilesCount;

    public static Fragment getInstance(JCPickerEnterOption enterOption) {
        Fragment fragment = null;
        switch (enterOption.getPickType()){
            case JCConstant.PICK_TYPE_IMAGE:
                if(TextUtils.isEmpty(enterOption.getAlbumName())){
                    fragment = JCAlbumPickerFragment.getInstance(enterOption);
                }
                else{
                    fragment = JCImagePickerFragment.getInstance(enterOption);
                }
                break;

            case JCConstant.PICK_TYPE_FILE:
            default:
                fragment = JCFilePickerFragment.getInstance(enterOption);
                break;
        }


        Bundle args = new Bundle();
        args.putParcelable(KEY_ENTER_OPTION, enterOption);
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
        View root = initView(inflater, container);
        init(root, savedInstanceState);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initToolbar();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        initMenu(menu, inflater);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_item_done){
            JCPickerClient.getDefaultInstance(getContext()).completePicker(adapter.getSelectedFiles());
        }

        dismissPicker();

        return super.onOptionsItemSelected(item);
    }

    private void dismissPicker(){
        if(getActivity() instanceof JCPickerActivity){
            getActivity().finish();
        }
        else{
            //TODO: dismiss host fragment
        }
    }

    private void initMenu(Menu menu, MenuInflater inflater) {
        if(adapter.getSelectedFiles() != null && adapter.getSelectedFiles().size() > 0){
            inflater.inflate(R.menu.jc_selecting_men, menu);
        }
        else{
            inflater.inflate(R.menu.jc_standard_menu, menu);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = null;
        if(getParentFragment() instanceof JCPickerHostFragment){
            toolbar = ((JCPickerHostFragment) getParentFragment()).getToolbar();
        }

        if(toolbar != null){
            getActivity().invalidateOptionsMenu();
            toolbar.setTitle(getTitle());
            toolbar.setSubtitle(null);

            int naviIconRes;
            if(adapter.getSelectedFiles() != null && adapter.getSelectedFiles().size() > 0){
                naviIconRes = R.drawable.jcpick_close_icon;

                String pickItemStr = "";
                switch (enterOption.getPickType()){
                    case JCConstant.PICK_TYPE_IMAGE:
                        pickItemStr = getContext().getResources().getString(R.string.jc_image);
                        break;
                    case JCConstant.PICK_TYPE_FILE:
                        pickItemStr = getContext().getResources().getString(R.string.jc_file);
                        break;
                }
                if(adapter.getSelectedFiles().size() > 1){
                    pickItemStr += "s";
                }

                toolbar.setSubtitle(String.format("%d %s selected", adapter.getSelectedFiles().size(), pickItemStr));
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.resetSelectedFiles();
                        initToolbar();
                    }
                });
            }
            else if(isRoot()){
                naviIconRes = R.drawable.jcpick_menu_icon;
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((JCPickerHostFragment) getParentFragment()).toggleSlideMenu();
                    }
                });
            }
            else{
                naviIconRes = R.drawable.jcpick_back_icon;
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((JCPickerHostFragment) getParentFragment()).pop();
                    }
                });
            }

            toolbar.setNavigationIcon(naviIconRes);
        }
    }

    private String getTitle() {
        String title = "";
        if(isRoot()){
            switch (enterOption.getPickType()){
                case JCConstant.PICK_TYPE_IMAGE:
                    title = getContext().getResources().getString(R.string.jc_slide_menu_image_title);
                    break;
                case JCConstant.PICK_TYPE_FILE:
                    title = getContext().getResources().getString(R.string.jc_slide_menu_file_title);
                    break;
            }
        }
        else{
            title = enterOption.getParentName();
        }
        return title;
    }

    private boolean isRoot(){
        return TextUtils.isEmpty(enterOption.getPath()) && TextUtils.isEmpty(enterOption.getAlbumName());
    }


    protected View initView(LayoutInflater inflater, ViewGroup container){
        return inflater.inflate(R.layout.fragment_file_list, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_ENTER_OPTION, enterOption);
        super.onSaveInstanceState(outState);
    }

    private void init(View root, Bundle savedInstanceState) {
        prepareData(savedInstanceState);
        presenter = initPresenter();
        presenter.setView(this);
        initView(root);
        loadFiles();
    }

    protected JCFileListPresenter initPresenter() {
        return new JCFileListPresenter();
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

    private void initView(View root) {
        initViewComponents(root);
        initRecyclerView();
    }

    private void initViewComponents(View root) {
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.file_list_swipeLayout);
        fileList = (RecyclerView) root.findViewById(R.id.file_list);
    }

    protected void initRecyclerView() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFiles();
            }
        });

        adapter = initListAdapter();
        adapter.setOnItemClickedListener(this);
        fileList.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = createLayoutManager();
        fileList.setLayoutManager(layoutManager);

        addItemDecorationIfNeed(fileList);
    }

    protected JCBaseFileListAdapter initListAdapter(){
        return new JCFileListAdapter();
    }

    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected void addItemDecorationIfNeed(RecyclerView fileList) {/*to be implemented bu child*/}

    protected void loadFiles() {
        //to be implemented bu child
        throw new RuntimeException("loadFiles() must Override in child.");
    }

    protected void showLoadingIndicator(final boolean isShow) {
        if(isAdded() && swipeRefreshLayout != null){
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(isShow);
                }
            });
        }
    }

    protected void processPreparedFiles(ArrayList<JCFile> files) {
        JCPickerBaseFileListFragment.this.files = files;
        adapter.setFiles(JCPickerBaseFileListFragment.this.files);
        showLoadingIndicator(false);
    }

    @Override
    public void updateToolbar() {
        initToolbar();
    }

    @Override
    public boolean onBackClicked() {
        boolean hasProcess = false;
        if(adapter.getSelectedFiles() != null && adapter.getSelectedFiles().size() > 0){
            hasProcess = true;
            adapter.resetSelectedFiles();
            initToolbar();
        }
        return hasProcess;
    }

    ///////////////////////////////////// Presenter callback ////////////////////////////////////
    @Override
    public void onLoadingFiles() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLoadingIndicator(true);
            }
        });
    }

    @Override
    public void onFilePrepared(final ArrayList<JCFile> files) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                processPreparedFiles(files);
            }
        });
    }



    ///////////////////////////////////// Adapter ////////////////////////////////////
    @Override public void onOpenFolder(JCFile file, int position) {/*should override by child*/}

    @Override public void onOpenImage(JCFile file, int position) {
        if(getParentFragment() instanceof JCPickerHostFragment){
            ((JCPickerHostFragment)getParentFragment()).pushFragment(JCImagePreviewFragment.getInstance(file.getUrl()));
        }
    }

    @Override
    public void onItemSelected(ArrayList<JCFile> selectedFiles) {
        if(JCPickerClient.getDefaultInstance(getContext()).isSinglePicking()){
            JCPickerClient.getDefaultInstance(getContext()).completePicker(adapter.getSelectedFiles());
            dismissPicker();
        }
        else{
            if(previousSelectedFilesCount == 0 && selectedFiles != null && selectedFiles.size() > 0
                    || (previousSelectedFilesCount > 0 && (selectedFiles == null || selectedFiles.size() == 0)))
            {
                getActivity().invalidateOptionsMenu();
            }

            initToolbar();
            previousSelectedFilesCount = selectedFiles != null ? selectedFiles.size() : 0;
        }
    }

    @Override
    public void onSelectedItemReachLimitation() {
        Resources resources = getContext().getResources();
        int maxPickCount = JCPickerClient.getDefaultInstance(getContext()).getMaxSelectedItemCount();

        String errorMsg = String.format(resources.getString(R.string.jc_reach_limitation),//Cannot pick more than %d %s%s
                maxPickCount,
                resources.getString(enterOption.getPickType() == JCConstant.PICK_TYPE_FILE ? R.string.jc_file : R.string.jc_image),
                maxPickCount > 1 ? "s" : "");

        JCSingleToast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }


}
