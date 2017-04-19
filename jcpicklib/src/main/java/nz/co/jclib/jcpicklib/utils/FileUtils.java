package nz.co.jclib.jcpicklib.utils;

import android.content.Context;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;

import nz.co.jclib.jcpicklib.data.model.JCFile;


/**
 * Created by Johnnie on 16/03/16.
 */
public class FileUtils {

    public static String getFileNameFromPath(String path) {
        return  path.substring(path.lastIndexOf('/') + 1, path.length());
    }


    public static String getFileExtension(String fileName) {
        if (fileName.indexOf("?") > -1) {
            fileName = fileName.substring(0, fileName.indexOf("?"));
        }
        if (fileName.lastIndexOf(".") == -1) {
            return "";
        } else {
            String ext = fileName.substring(fileName.lastIndexOf("."));
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }

            if(ext.equals(".")){
                ext = "";
            }
            return ext.toLowerCase();
        }
    }


    public static String getFileTypeString(File file){
        return getFileTypeStringFromExtension(getFileExtension(file.getName()));
    }

    public static String getFileTypeStringFromFilename(String filename){
        return getFileTypeStringFromExtension(getFileExtension(filename));
    }

    public static String getFileTypeStringFromExtension(String fileExt){
        fileExt = fileExt.replace(".", "");
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExt);
    }

    public static boolean isContainExistFiles(ArrayList<JCFile> files, ArrayList<JCFile> existFiles) {
        boolean isExist = false;
        for(JCFile file : files){
            if(isFileExistInGivenFiles(file, existFiles)){
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public static boolean isFileExistInDir(JCFile file, String dir){
        File tempFile = new File(dir, file.getName());
        boolean isExist = tempFile.exists();
        return isExist;
    }

    public static boolean isFileExistInGivenFiles(JCFile file, ArrayList<JCFile> existFiles) {
        boolean isExist = false;
        for(JCFile existFile : existFiles){
            if(existFile.getId().equals(file.getId())){
                isExist = true;
            }
        }
        return isExist;
    }

    public static JCFile findExistFileFromGivenFiles(JCFile file, ArrayList<JCFile> existFiles) {
        JCFile result = null;
        for(JCFile existFile : existFiles){
            if(existFile.getName().equals(file.getName())){
                result = existFile;
            }
        }
        return result;
    }

    public static File getDownloadedFile(JCFile file){
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        return new File(path, file.getName());
    }

    public static boolean isFileHasNewVersion(File existFile, JCFile fileOnServer){
        return existFile.length() != fileOnServer.getSize();
    }

    public static String getDuplicateFileNameInLocal(JCFile file) {
        String ext = getFileExtension(file.getName());
        String formatStr = file.getName().replace(ext, "") + "(%d)";
        String newFileName = "";
        boolean found = false;
        int index = 1;
        while(!found){
            newFileName = String.format(formatStr, index++) + ext;
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File tempFile = new File(path, newFileName);
            if(!tempFile.exists()){
                found = true;
            }
        }
        return newFileName;
    }

    public static String getDuplicateFileName(String fileName, ArrayList<JCFile> existFiles) {
        String ext = getFileExtension(fileName);
        String formatStr = fileName.replace(ext, "") + "(%d)";
        String newFileName = "";

        boolean isNameExist = false;
        ArrayList<String> existFileNames = new ArrayList<>();
        for(JCFile obj : existFiles){
            existFileNames.add(obj.getName());
            if(obj.getName().equals(fileName)){
                isNameExist = true;
            }
        }
        if(!isNameExist){
            return fileName;
        }

        boolean found = false;
        int index = 1;
        while(!found){
            newFileName = String.format(formatStr, index++) + ext;
            if(!existFileNames.contains(newFileName)){
                found = true;
            }
        }
        return newFileName;
    }

    public static byte[] getBytesFromFile(File file){
        FileInputStream fileInputStream=null;

        byte[] bFile = new byte[(int) file.length()];

        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

            for (int i = 0; i < bFile.length; i++) {
                System.out.print((char)bFile[i]);
            }

            System.out.println("Done");
        }catch(Exception e){
            e.printStackTrace();
        }
        return  bFile;
    }

    public static String getSizeString(long size, boolean si) {
        int unit = si ? 1000 : 1024;
        if (size < unit) return size + " B";
        int exp = (int) (Math.log(size) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + "";
        return String.format("%.1f %sB", size / Math.pow(unit, exp), pre);
    }

    public static boolean hasFolder(ArrayList<JCFile> files){
        boolean hasFolder = false;
        for(JCFile file : files){
            if (file.isFolder()){
                hasFolder = true;
                break;
            }
        }

        return hasFolder;
    }

    /**
     * return new File
     * @param from
     * @param to
     * @return
     */
    public static void copyFile(File from, File to){
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        try {
            InputStream in = new FileInputStream(from);
            OutputStream out = new FileOutputStream(to);
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final static String getMD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
