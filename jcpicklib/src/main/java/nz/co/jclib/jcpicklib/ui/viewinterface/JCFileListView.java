package nz.co.jclib.jcpicklib.ui.viewinterface;

import java.util.ArrayList;

import nz.co.jclib.jcpicklib.data.model.JCFile;

/**
 * Created by Johnnie on 18/04/17.
 */
public interface JCFileListView extends JCPickerBaseViewInterface {
    void onLoadingFiles();
    void onFilePrepared(ArrayList<JCFile> files);
}
