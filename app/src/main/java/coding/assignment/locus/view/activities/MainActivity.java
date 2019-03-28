package coding.assignment.locus.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import coding.assignment.locus.R;
import coding.assignment.locus.application.LocusApplication;
import coding.assignment.locus.data.DataFactory;
import coding.assignment.locus.utils.ImageUtils;
import coding.assignment.locus.view.adapters.ImageClickedCallbacks;
import coding.assignment.locus.view.adapters.LocusAdapter;

import static coding.assignment.locus.utils.ImageUtils.LOCUS_IMAGES;
import static coding.assignment.locus.view.activities.FullScreenImageActivity.IMAGE_MAP_KEY;

public class MainActivity extends AppCompatActivity implements ImageClickedCallbacks {
    
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private LocusAdapter locusAdapter;
    
    private static final int PERMISSIONS_REQUEST = 101;
    private static final int CAMERA_OPEN_REQUEST = 100;
    private int position = -1;
    private String imageFilePath = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();
    }
    
    private void init() {
        recyclerView = findViewById(R.id.home_rv_id);
        locusAdapter = new LocusAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(locusAdapter);
        locusAdapter.setDataList(DataFactory.getInputFromJson());
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (!cameraPermissionGiven())
            requestPermissions();
    }
    
    /**
     * checking if all the required permissions are given
     * @return
     */
    private boolean cameraPermissionGiven() {
        return ContextCompat.checkSelfPermission(LocusApplication.getAppContext(), Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(LocusApplication.getAppContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(LocusApplication.getAppContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED;
    }
    
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA_OPEN_REQUEST && imageFilePath != null) {
            locusAdapter.setImageInItem(position, imageFilePath);
            imageFilePath = null;
        } else if (resultCode == RESULT_CANCELED) {
            imageFilePath = null;
        }
    }
    
    @Override
    public void onImageClicked(int position, String imageFilePath) {
        this.position = position;
        if (imageFilePath == null) {
            openCamera();
            Log.d(TAG, "Image clicked at " + position + " where imageBitmap: " + imageFilePath);
        } else {
            Intent fullScreenIntent = new Intent(this, FullScreenImageActivity.class);
            fullScreenIntent.putExtra(IMAGE_MAP_KEY, imageFilePath);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, ((LocusAdapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition(position)).photoView, getResources().getString(R.string.image_transition));
            startActivity(fullScreenIntent, options.toBundle());
        }
    }
    
    /**
     * opening camera with action to store taken image.
     */
    private void openCamera() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = ImageUtils.createImageFile();
                imageFilePath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, getPackageName() + ".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent,
                        CAMERA_OPEN_REQUEST);
            }
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.submit_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit_menu_id:
                logImagesPath();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * toasting and logging image storage path.
     */
    private void logImagesPath() {
        Toast.makeText(getApplicationContext(), "Images are stored at " + Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + LOCUS_IMAGES, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Images are stored in the path: " + ImageUtils.getImagespath());
        Log.d(TAG, "Individual path will be logged on taking the image itself");
    }
}
