package nz.co.jclib.jcpicklib.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.IOException;

import nz.co.jclib.jcpicklib.R;
import nz.co.jclib.jcpicklib.ui.Widget.JCSingleToast;

/**
 * Created by Johnnie on 2/11/15.
 */

public class UIHelper {
    public enum DialogType{
        NEW_FILE,
        CREATE_ITEM,
        STANDARD_MENU,
        STANDARD_FILE_SELECTED_MENU
    }

    private static final int TABLET_SMALLEST_WIDTH_DP = 600;
    public static final int TYPEFACE_REGULAR = 0;
    public static final int TYPEFACE_BOLD    = 1;
    public static final int TYPEFACE_LIGHT   = 2;

    public static int getRelativeX(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeX((View) myView.getParent());
    }

    public static int getRelativeY(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getTop();
        else
            return myView.getTop() + getRelativeY((View) myView.getParent());
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    public static void setMenuItemColor(MenuItem menuItem, int color){
        if(menuItem != null && menuItem.getIcon() != null){
            menuItem.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    public static void displayYesNoDialog(final Context context, String message, DialogInterface.OnClickListener onYesClick){
        displayYesNoDialog(context, "", message, onYesClick);
    }

    public static void displayYesNoDialog(final Context context, String tittle, String message, DialogInterface.OnClickListener onYesClick){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Yes", onYesClick);

        if(TextUtils.isEmpty(tittle)){
            dialogBuilder.setTitle(message);
        }
        else{
            dialogBuilder.setTitle(tittle)
                    .setMessage(message);
        }

        final AlertDialog dialog = dialogBuilder.create();
        changeDialogButtonTextColor(context, dialog);
        dialog.show();
    }

    public static void displayConfirmDialog(Context context, String message){
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        changeDialogButtonTextColor(context, dialog);
        dialog.show();
    }

    public static void displayConfirmDialog(Context context, String title, String message){
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        changeDialogButtonTextColor(context, dialog);
        dialog.show();
    }

    public static void displayConfirmDialog(Context context, String message, DialogInterface.OnClickListener onOKClick){
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(message)
                .setPositiveButton("Ok", onOKClick)
                .create();
        changeDialogButtonTextColor(context, dialog);
        dialog.show();
    }

    private static void changeDialogButtonTextColor(final Context context, final AlertDialog dialog){
        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                if(dialog.getButton(AlertDialog.BUTTON_NEGATIVE) != null){
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.jc_dialog_button_text_color));
                }

                if(dialog.getButton(AlertDialog.BUTTON_NEUTRAL) != null){
                    dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.jc_dialog_button_text_color));
                }

                if(dialog.getButton(AlertDialog.BUTTON_POSITIVE) != null){
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.jc_dialog_button_text_color));
                }
            }
        });
    }

    public static void onBackClick(){
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static boolean isTabletSize(Context context, View view) {
        float widthInDp = px2dip(context, view.getWidth());
        return widthInDp >= TABLET_SMALLEST_WIDTH_DP;
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public static void hideKeyBoard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        if (differenceX > 5 || differenceY > 5) {
            return false;
        }
        return true;
    }

    public static boolean isPointInsideView(float x, float y, View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        // point is inside view bounds
        return ((x > viewX && x < (viewX + view.getWidth())) &&
                (y > viewY && y < (viewY + view.getHeight())));
    }

    public static int hexToIntColor(String color) {
        //Default value
        int mColor = Color.BLACK;

        if(color != null)  {
            try {
                mColor = Color.parseColor("#"+color);
            }
            catch (NumberFormatException e) {
                Log.e("UIHelper", "NumberFormatException for "+color);
            }
            catch (Exception e) {
                Log.e("UIHelper", "Unknown color exception "+color);
            }
        }

        return mColor;
    }

    public static void presentView(Activity activity, View view){
        Dialog dlg = new Dialog(activity, R.style.jc_dialog_bottom_in);
        dlg.setContentView(view);
        Window window = dlg.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        WindowManager wm = activity.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        lp.width = width;
        window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        lp.dimAmount = 0.2f;
        window.setAttributes(lp);
        dlg.show();
    }

}
