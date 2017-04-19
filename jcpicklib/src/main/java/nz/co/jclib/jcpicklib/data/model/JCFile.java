package nz.co.jclib.jcpicklib.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.File;

import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.utils.FileUtils;

/**
 * Created by Johnnie on 28/03/17.
 */
public class JCFile implements Parcelable {
    private String id;
    private String name;
    private long size;
    private String url;
    private int childCount;//for album use only
    private boolean isSelected;
    private boolean isAlbum;

    public JCFile() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isAlbum() {
        return isAlbum;
    }

    public void setAlbum(boolean album) {
        isAlbum = album;
    }

    public boolean isFile() {
        File file = new File(url);
        return file.isFile();
    }

    public boolean isFolder() {
        File file = new File(url);
        return file.isDirectory();
    }

    public boolean isImage(){
        boolean isImage = false;
        String ext = getExtension();
        switch (ext){
            case ".png":
            case ".jpg":
            case ".jpeg":
            case ".bmp":
                isImage = true;
                break;
        }
        return isImage;
    }


    public boolean isVideo() {
        boolean isVideo = false;
        String ext = getExtension();
        switch (ext){
            case ".mp4":
            case ".avi":
            case ".mov":
            case ".wmv":
                isVideo = true;
                break;
        }
        return isVideo;
    }

    public String getExtension(){
        return FileUtils.getFileExtension(TextUtils.isEmpty(url) ? name : url);
    }

    public int getFileImageResID(){
        int result = 0;
        if(isFolder()){
            result = R.drawable.jcpick_folder;
        }
        else if(getExtension() != null){
            switch (getExtension()){
                case ".avi":
                    result = R.drawable.jcpick_file_avi_icon;
                    break;
                case ".css":
                    result = R.drawable.jcpick_file_css_icon;
                    break;
                case ".csv":
                    result = R.drawable.jcpick_file_csv_icon;
                    break;
                case ".doc":
                case ".docx":
                    result = R.drawable.jcpick_file_doc_icon;
                    break;
                case ".eps":
                    result = R.drawable.jcpick_file_eps_icon;
                    break;
                case ".exe":
                    result = R.drawable.jcpick_file_exe_icon;
                    break;
                case ".gif":
                    result = R.drawable.jcpick_file_gif_icon;
                    break;
                case ".html":
                    result = R.drawable.jcpick_file_html_icon;
                    break;
                case ".jpg":
                case ".jpeg":
                    result = R.drawable.jcpick_file_jpg_icon;
                    break;
                case ".mov":
                    result = R.drawable.jcpick_file_mov_icon;
                    break;
                case ".mp3":
                    result = R.drawable.jcpick_file_mp3_icon;
                    break;
                case ".mp4":
                    result = R.drawable.jcpick_file_mp4_icon;
                    break;
                case ".pdf":
                    result = R.drawable.jcpick_file_pdf_icon;
                    break;
                case ".png":
                    result = R.drawable.jcpick_file_png_icon;
                    break;
                case ".ppt":
                case ".pptx":
                    result = R.drawable.jcpick_file_ppt_icon;
                    break;
                case ".psd":
                    result = R.drawable.jcpick_file_psd_icon;
                    break;
                case ".rar":
                    result = R.drawable.jcpick_file_rar_icon;
                    break;
                case ".raw":
                    result = R.drawable.jcpick_file_raw_icon;
                    break;
                case ".txt":
                    result = R.drawable.jcpick_file_txt_icon;
                    break;
                case ".wav":
                    result = R.drawable.jcpick_file_wav_icon;
                    break;
                case ".xls":
                case ".xlsx":
                    result = R.drawable.jcpick_file_xls_icon;
                    break;
                case ".zip":
                    result = R.drawable.jcpick_file_zip_icon;
                    break;
                default:
                    result = R.drawable.jcpick_file_unknown_icon;
                    break;
            }
        }
        return result;
    }

    ///////////////////////////////////// Parcelable ////////////////////////////////////
    protected JCFile(Parcel in) {
        id = in.readString();
        name = in.readString();
        size = in.readLong();
        url = in.readString();
        childCount = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeLong(size);
        dest.writeString(url);
        dest.writeInt(childCount);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<JCFile> CREATOR = new Parcelable.Creator<JCFile>() {
        @Override
        public JCFile createFromParcel(Parcel in) {
            return new JCFile(in);
        }

        @Override
        public JCFile[] newArray(int size) {
            return new JCFile[size];
        }
    };

}