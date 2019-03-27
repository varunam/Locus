package coding.assignment.locus.view.adapters;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

/**
 * Created by varun.am on 27/03/19
 */
public interface ImageClickedCallbacks {
    public void onImageClicked(int position, @Nullable Bitmap loadedImageBitmap);
}
