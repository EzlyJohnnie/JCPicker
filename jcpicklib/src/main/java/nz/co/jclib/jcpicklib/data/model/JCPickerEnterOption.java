package nz.co.jclib.jcpicklib.data.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import nz.co.jclib.jcpicklib.utils.JCConstant;
import nz.co.jclib.jcpicklib.utils.JCPreferenceHelper;

/**
 * Created by Johnnie on 5/04/17.
 */
public class JCPickerEnterOption implements Parcelable {
    private final static String SHARED_PREF_KEY = "sharedPrefFileKey_enterOption";

    @Expose private int pickType;
    @Expose private String path;
    @Expose private String parentName;

    @Expose private String albumID;
    @Expose private String albumName;

    public static JCPickerEnterOption createDefaultOption(Context context){
        return JCPreferenceHelper.fromSharedPreference(context, SHARED_PREF_KEY, SHARED_PREF_KEY, JCPickerEnterOption.class);
    }

    public static JCPickerEnterOption createPickImageOption(){
        JCPickerEnterOption enterOption = new JCPickerEnterOption();
        enterOption.setPickType(JCConstant.PICK_TYPE_IMAGE);
        return enterOption;
    }

    public static JCPickerEnterOption createPickFileOption(){
        JCPickerEnterOption enterOption = new JCPickerEnterOption();
        enterOption.setPickType(JCConstant.PICK_TYPE_FILE);
        return enterOption;
    }

    public JCPickerEnterOption() {}

    public int getPickType() {
        return pickType;
    }

    public void setPickType(@JCConstant.PickType int pickType) {
        this.pickType = pickType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentName() {
        return parentName;
    }

    @Override
    public JCPickerEnterOption clone(){
        JCPickerEnterOption result = new JCPickerEnterOption();
        result.setPickType(pickType);
        result.setPath(path);
        result.setParentName(parentName);
        result.setAlbumID(albumID);
        result.setAlbumName(albumName);

        return result;
    }




    ///////////////////////////////////// Parcelable ////////////////////////////////////
    protected JCPickerEnterOption(Parcel in) {
        pickType = in.readInt();
        path = in.readString();
        parentName = in.readString();
        albumID = in.readString();
        albumName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pickType);
        dest.writeString(path);
        dest.writeString(parentName);
        dest.writeString(albumID);
        dest.writeString(albumName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<JCPickerEnterOption> CREATOR = new Parcelable.Creator<JCPickerEnterOption>() {
        @Override
        public JCPickerEnterOption createFromParcel(Parcel in) {
            return new JCPickerEnterOption(in);
        }

        @Override
        public JCPickerEnterOption[] newArray(int size) {
            return new JCPickerEnterOption[size];
        }
    };
}