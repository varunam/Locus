package coding.assignment.locus.view.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import coding.assignment.locus.R;
import coding.assignment.locus.application.LocusApplication;
import coding.assignment.locus.data.models.DataMap;
import coding.assignment.locus.data.models.DataModel;
import coding.assignment.locus.data.models.DataModelBuilder;
import coding.assignment.locus.data.models.Options;
import coding.assignment.locus.view.ViewTypes;
import coding.assignment.locus.view.adapters.ImageClickedCallbacks;
import coding.assignment.locus.view.adapters.LocusAdapter;

import static coding.assignment.locus.view.activities.FullScreenImageActivity.IMAGE_MAP_KEY;

public class MainActivity extends AppCompatActivity implements ImageClickedCallbacks {
    
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private LocusAdapter locusAdapter;
    
    private static final int PERMISSIONS_REQUEST = 101;
    private int position = -1;
    
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
        locusAdapter.setDataList(getDummyData());
    }
    
    private ArrayList<DataModel> getDummyData() {
        ArrayList<DataModel> dataModels = new ArrayList<>();
        
        DataModelBuilder dataModelBuilder = new DataModelBuilder()
                .setViewType(ViewTypes.PHOTO)
                .setTitle("Photo Title")
                .setId("photo_id")
                .setDataMap(null);
        dataModels.add(dataModelBuilder.createDataModel());
        
        DataModelBuilder singleChoiceBuilder = new DataModelBuilder()
                .setId("single_choice_id")
                .setTitle("Single Choice Title")
                .setViewType(ViewTypes.SINGLE_CHOICE)
                .setDataMap(new DataMap(
                        new Options(
                                "Good",
                                "Bad",
                                "Worse"
                        )
                ));
        dataModels.add(singleChoiceBuilder.createDataModel());
        
        DataModelBuilder commentBuilder = new DataModelBuilder()
                .setViewType(ViewTypes.COMMENT)
                .setTitle("Comment boxX")
                .setId("comment_id")
                .setTitle("This is sample comment")
                .setDataMap(null);
        dataModels.add(commentBuilder.createDataModel());
        
        DataModelBuilder dataModelBuilder1 = new DataModelBuilder()
                .setViewType(ViewTypes.PHOTO)
                .setTitle("Photo Title 1")
                .setId("photo_id")
                .setDataMap(null);
        dataModels.add(dataModelBuilder1.createDataModel());
        
        DataModelBuilder singleChoiceBuilder1 = new DataModelBuilder()
                .setId("single_choice_id")
                .setTitle("Single Choice Title 1")
                .setViewType(ViewTypes.SINGLE_CHOICE)
                .setDataMap(new DataMap(
                        new Options(
                                "Good 1",
                                "Bad 1",
                                "Worse 1"
                        )
                ));
        dataModels.add(singleChoiceBuilder1.createDataModel());
        
        DataModelBuilder commentBuilder1 = new DataModelBuilder()
                .setViewType(ViewTypes.COMMENT)
                .setId("comment_id")
                .setDataMap(null);
        dataModels.add(commentBuilder1.createDataModel());
        
        return dataModels;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (!cameraPermissionGiven())
            requestPermissions();
    }
    
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
    
    private void onCaptureImageResult(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        Log.d(TAG, "sending bitmap: " + imageBitmap);
        locusAdapter.setImageInItem(position, imageBitmap);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (data != null && requestCode == 100) {
                onCaptureImageResult(data);
            }
        } else {
            Log.d(TAG, "Activity cancelled");
        }
    }
    
    @Override
    public void onImageClicked(int position, Bitmap loadedImageBitmap) {
        if (loadedImageBitmap == null) {
            openCamera();
            this.position = position;
            Log.d(TAG, "Image clicked at " + position + " where imageBitmap: " + loadedImageBitmap);
        } else {
            Intent fullScreenIntent = new Intent(this, FullScreenImageActivity.class);
            fullScreenIntent.putExtra(IMAGE_MAP_KEY, loadedImageBitmap);
            startActivity(fullScreenIntent);
        }
    }
    
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
        Log.d(TAG, "Opening camera");
    }
}
