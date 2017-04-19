package nz.co.jclib.jcpicklib.ui.Widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import nz.co.jclib.jcpicklib.R;


/**
 * Created by Johnnie on 5/09/15.
 */
public class JCTextView extends TextView {

    private Context context;

    public JCTextView(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public JCTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public JCTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public JCTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JCTextView, 0, 0);
        String typeface = typedArray.getString(R.styleable.JCTextView_typeface);

        if (!isInEditMode())
            setTypeface(TextUtils.isEmpty(typeface) ? "" : typeface);

        typedArray.recycle();
    }

    public void setTypeface(String typeface) {
//        switch (typeface) {
//            case "bold":
//                setTypeface(Typeface.createFromAsset(context.getAssets(), "pingfang_bold.ttf"));
//                break;
//            case "light":
//                setTypeface(Typeface.createFromAsset(context.getAssets(), "pingfang_light.ttf"));
//                break;
//            case "regular":
//            default:
//                setTypeface(Typeface.createFromAsset(context.getAssets(), "pingfang_regular.ttf"));
//                break;
//        }
    }
}
