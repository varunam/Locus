package coding.assignment.locus.view.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import coding.assignment.locus.R;

public class FullScreenImageActivity extends AppCompatActivity {
    
    public static final String IMAGE_MAP_KEY = "image_map";
    private ImageView fullscreenImage;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        
        init();
    
        Bitmap imageBitmap = getIntent().getParcelableExtra(IMAGE_MAP_KEY);
        fullscreenImage.setImageBitmap(imageBitmap);
    }
    
    private void init() {
        fullscreenImage = findViewById(R.id.full_screen_image_id);
    }
}
