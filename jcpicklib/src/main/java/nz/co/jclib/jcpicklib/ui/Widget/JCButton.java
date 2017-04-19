package nz.co.jclib.jcpicklib.ui.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;

import nz.co.jclib.jcpicklib.R;


/**
 * Created by Johnnie on 16/03/16.
 */
public class JCButton extends Button {

    private Context context;

    public JCButton(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public JCButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public JCButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
