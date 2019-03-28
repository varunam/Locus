package coding.assignment.locus.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import coding.assignment.locus.application.LocusApplication;
import coding.assignment.locus.data.models.DataMap;
import coding.assignment.locus.data.models.DataModel;
import coding.assignment.locus.data.models.DataModelBuilder;
import coding.assignment.locus.data.models.Options;
import coding.assignment.locus.view.ViewTypes;

/**
 * Created by varun.am on 28/03/19
 */
public class DataFactory {
    
    private static final String TAG = DataFactory.class.getSimpleName();
    
    public static ArrayList<DataModel> getInputFromJson() {
        String jsonArrayString = getInputArrayFromAssets();
        ArrayList<DataModel> dataModels = new ArrayList<>();
        Log.d(TAG, "Received string: " + jsonArrayString);
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                DataModelBuilder dataModelBuilder = new DataModelBuilder()
                        .setId(jsonObject.getString("id"))
                        .setTitle(jsonObject.getString("title"))
                        .setViewType(getViewTypeInt(jsonObject.getString("type")));
                JSONObject dataMap = jsonObject.getJSONObject("dataMap");
                if (dataMap.has("options")) {
                    JSONArray optionsArray = dataMap.getJSONArray("options");
                    dataModelBuilder.setDataMap(
                            new DataMap(
                                    new Options(
                                            optionsArray.getString(0),
                                            optionsArray.getString(1),
                                            optionsArray.getString(2)
                                    )
                            )
                    );
                } else {
                    dataModelBuilder.setDataMap(null);
                }
                dataModels.add(dataModelBuilder.createDataModel());
                Log.d(TAG, "Added model to list. Current size: " + dataModels.size());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Returning dataModels list of size " + dataModels.size());
        return dataModels;
    }
    
    private static int getViewTypeInt(String type) {
        switch (type) {
            case ViewTypes.PHOTO_S:
                return ViewTypes.PHOTO;
            case ViewTypes.SINGLE_CHOICE_S:
                return ViewTypes.SINGLE_CHOICE;
            case ViewTypes.COMMENT_S:
                return ViewTypes.COMMENT;
            default:
                return -1;
        }
    }
    
    private static String getInputArrayFromAssets() {
        String jsonArray = null;
        try {
            InputStream inputStream = LocusApplication.getAppContext().getAssets().open("input.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonArray = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
