package coding.assignment.locus.view.activities;

import android.net.Uri;
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
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_full_screen_image);
        
        init();
        
        String imagePath = getIntent().getStringExtra(IMAGE_MAP_KEY);
        fullscreenImage.setImageURI(Uri.parse(imagePath));
    }
    
    private void init() {
        fullscreenImage = findViewById(R.id.full_screen_image_id);
    }
}
