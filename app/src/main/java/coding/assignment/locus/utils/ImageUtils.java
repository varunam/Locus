package coding.assignment.locus.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by varun.am on 28/03/19
 */
public class ImageUtils {
    
    private static final String TAG = ImageUtils.class.getSimpleName();
    public static final String LOCUS_IMAGES = "LocusImages";
    private static final String IMAGES_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + LOCUS_IMAGES;
    
    /**
     * creating image file.
     *
     * @return
     * @throws IOException
     */
    public static File createImageFile() throws IOException {
        String imageFileName = "Locus_" + new Date().getTime();
        File storageDir =
                new File(IMAGES_PATH);
        if (!storageDir.exists())
            storageDir.mkdirs();
        
        Log.d(TAG, IMAGES_PATH);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        
        return image;
    }
    
    public static String getImagespath() {
        return IMAGES_PATH;
    }
    
}
