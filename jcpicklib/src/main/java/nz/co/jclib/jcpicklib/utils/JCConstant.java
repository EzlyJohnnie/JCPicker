package nz.co.jclib.jcpicklib.utils;

import android.support.annotation.IntDef;

/**
 * Created by Johnnie on 4/04/17.
 */
public class JCConstant {

    @IntDef({PICK_TYPE_IMAGE, PICK_TYPE_FILE})
    public @interface PickType{}
    public static final int PICK_TYPE_IMAGE = 0;
    public static final int PICK_TYPE_FILE = 1;


}
