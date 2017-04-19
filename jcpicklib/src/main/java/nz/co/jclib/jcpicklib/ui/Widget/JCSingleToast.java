package nz.co.jclib.jcpicklib.ui.Widget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Johnnie on 9/07/16.
 */

public class JCSingleToast {
    private static Toast mToast;

    private JCSingleToast() {
    }

    public static Toast makeText(Context context, CharSequence text, int duration) {
        if(mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(context, text, duration);
        return mToast;
    }

    public static Toast makeText(Context context, int resId, int duration) {
        if(mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(context, resId, duration);
        return mToast;
    }
}