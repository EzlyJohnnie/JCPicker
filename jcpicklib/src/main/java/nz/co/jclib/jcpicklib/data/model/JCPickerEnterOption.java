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

    @Expose
    private int pickType;

    //TODO: to implement default path
    @Expose
    private String path;

    public static JCPickerEnterOption createDefaultOption(Context context){
        return JCPreferenceHelper.fromSharedPreference(context, SHARED_PREF_KEY, SHARED_PREF_KEY, JCPickerEnterOption.class);
    }

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

    protected JCPickerEnterOption(Parcel in) {
        pickType = in.readInt();
        path = in.readString();
    }







    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pickType);
        dest.writeString(path);
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