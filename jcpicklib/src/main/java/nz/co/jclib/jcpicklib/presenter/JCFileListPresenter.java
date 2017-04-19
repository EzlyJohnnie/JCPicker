package nz.co.jclib.jcpicklib.presenter;

import java.util.ArrayList;

import nz.co.jclib.jcpicklib.data.JCFileProvider;
import nz.co.jclib.jcpicklib.data.model.JCFile;
import nz.co.jclib.jcpicklib.ui.viewinterface.JCFileListView;

/**
 * Created by Johnnie on 18/04/17.
 */
public class JCFileListPresenter extends JCPickerBasePresenter{

    private JCFileListView mView;

    public void setView(JCFileListView mView) {
        this.mView = mView;
    }

    public JCFileListPresenter() {}

    public void loadFileList(String path){

    }

    public void loadAlbum(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(mView != null){
                    mView.onLoadingFiles();
                    ArrayList<JCFile> files = JCFileProvider.provideAlbum(mView.getContext());
                    mView.onFilePrepared(files);
                }
            }
        });

        thread.start();
    }

    public void loadImageForAlbumName(String albumName){

    }
}
