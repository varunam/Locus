package coding.assignment.locus.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import coding.assignment.locus.R;
import coding.assignment.locus.data.models.DataMap;
import coding.assignment.locus.data.models.DataModel;
import coding.assignment.locus.data.models.DataModelBuilder;
import coding.assignment.locus.data.models.Options;
import coding.assignment.locus.view.ViewTypes;
import coding.assignment.locus.view.adapters.LocusAdapter;

public class MainActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView;
    private LocusAdapter locusAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        init();
    }
    
    private void init() {
        recyclerView = findViewById(R.id.home_rv_id);
        locusAdapter = new LocusAdapter();
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
}
