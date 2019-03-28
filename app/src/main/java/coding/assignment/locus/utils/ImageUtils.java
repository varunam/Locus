package coding.assignment.locus.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import coding.assignment.locus.application.LocusApplication;

/**
 * Created by varun.am on 28/03/19
 */
public class ImageUtils {
    
    private static final String TAG = ImageUtils.class.getSimpleName();
    private static final String LOCUS_IMAGES = "LocusImages";
    
    private static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    
    private static String getRealPathFromURI(Uri uri) {
        String path = "";
        if (LocusApplication.getAppContext().getContentResolver() != null) {
            Cursor cursor = LocusApplication.getAppContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    
    public static File getImageStoredPath(Bitmap bm) {
        Log.d(TAG, "getImagePath");
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + LOCUS_IMAGES;
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, "Locus_" + new Date().getTime() + ".png");
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Returning file at " + file.getAbsolutePath());
        return file;
    }
    
    public static String getImagespath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + LOCUS_IMAGES;
    }
    
}
