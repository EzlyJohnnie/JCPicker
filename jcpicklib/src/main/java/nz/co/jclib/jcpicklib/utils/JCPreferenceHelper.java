package nz.co.jclib.jcpicklib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Johnnie on 4/04/17.
 */
public class JCPreferenceHelper {
    public static void toSharedPreference(Context context, String fileKey, String valueKey, String jsonStr){
        SharedPreferences preferences = context.getSharedPreferences(fileKey, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(valueKey, jsonStr);

        editor.apply();
    }

    public static <T> T fromSharedPreference(Context context, String fileKey, String valueKey, Class<T> objClass){
        SharedPreferences preferences = context.getSharedPreferences(fileKey, Context.MODE_WORLD_READABLE);
        String jsonString = preferences.getString(valueKey, "{}");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return (objClass.isInstance(String.class)) ? (T)jsonString : gson.fromJson(jsonString, objClass);
    }
}
