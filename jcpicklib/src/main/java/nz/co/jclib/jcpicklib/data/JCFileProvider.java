package nz.co.jclib.jcpicklib.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

import nz.co.jclib.jcpicklib.data.model.JCFile;
import nz.co.jclib.jcpicklib.utils.FileUtils;

/**
 * Created by Johnnie on 18/04/17.
 */
public class JCFileProvider {

    public static ArrayList<JCFile> provideAlbum(Context context){
        final String COUNT = String.format("count(%s)", MediaStore.Images.Media.BUCKET_ID);
        ArrayList<JCFile> files = new ArrayList<>();

        // which image properties are we querying
        String[] projection = new String[] {
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                COUNT
        };

        // We want to order the albums by reverse chronological order. We abuse the
        // "WHERE" parameter to insert a "GROUP BY" clause into the SQL statement.
        // The template for "WHERE" parameter is like:
        //    SELECT ... FROM ... WHERE (%s)
        // and we make it look like:
        //    SELECT ... FROM ... WHERE (1) GROUP BY 1,(2)
        // The "(1)" means true. The "1,(2)" means the first two columns specified
        // after SELECT. Note that because there is a ")" in the template, we use
        // "(2" to match it.
        String BUCKET_GROUP_BY = "1) GROUP BY (1";

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        // Make the query.
        Cursor cursor = context.getContentResolver().query(images, projection, BUCKET_GROUP_BY, null, null);

        if (cursor.moveToFirst()) {
            String id;
            String name;
            String path;
            int count;

            int idColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
            int nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int pathColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            int countColumn = cursor.getColumnIndex(COUNT);

            do {
                // Get the field values
                id = cursor.getString(idColumn);
                name = cursor.getString(nameColumn);
                path = cursor.getString(pathColumn);
                count = cursor.getInt(countColumn);

                JCFile file = new JCFile();
                file.setId(id);
                file.setName(name);
                file.setUrl(path);
                file.setChildCount(count);
                file.setAlbum(true);

                files.add(file);


            } while (cursor.moveToNext());
        }

        return files;
    }

    public static ArrayList<JCFile> provideImageForAlbum(Context context, String albumID) {
        ArrayList<JCFile> files = new ArrayList<>();

        // which image properties are we querying
        String[] projection = new String[] {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA
        };

        String where = String.format("%s=\'%s\'", MediaStore.Images.Media.BUCKET_ID, albumID);

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        // Make the query.
        Cursor cursor = context.getContentResolver().query(images, projection, where, null, null);

        if (cursor.moveToFirst()) {
            String id;
            String path;

            int idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
            int pathColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

            do {
                // Get the field values
                id = cursor.getString(idColumn);
                path = cursor.getString(pathColumn);

                JCFile file = new JCFile();
                file.setId(id);
                file.setUrl(path);
                file.setName(FileUtils.getFileNameFromPath(path));

                files.add(file);


            } while (cursor.moveToNext());
        }

        return files;
    }
}
