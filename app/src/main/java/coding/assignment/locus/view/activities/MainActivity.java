package coding.assignment.locus.view.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
        
        return dataModels;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (!cameraPermissionGiven())
            requestCameraPermission();
    }
    
    private boolean cameraPermissionGiven() {
        return ContextCompat.checkSelfPermission(LocusApplication.getAppContext(), Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED;
    }
    
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST);
    }
    
    private void onCaptureImageResult(Intent data) {
        
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        Log.d(TAG,"sending bitmap: " + imageBitmap);
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
    
    public Uri getImageUri(Context inContext, Bitmap inImage, String imageName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, imageName, null);
        return Uri.parse(path);
    }
    
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    
    public Bitmap convertSrcToBitmap(String imageSrc) {
        Bitmap myBitmap = null;
        File imgFile = new File(imageSrc);
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return myBitmap;
    }
    
    @Override
    public void onImageClicked(int position, boolean imageLoaded) {
        openCamera();
        this.position = position;
        Log.d(TAG, "Image clicked at " + position + " where imageLocaded: " + imageLoaded);
    }
    
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
        Log.d(TAG, "Opening camera");
    }
}
