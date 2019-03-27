package coding.assignment.locus.data.models;

import android.graphics.Bitmap;

/**
 * Created by varun.am on 27/03/19
 */
public class DataModel {
    
    private int viewType;
    private String id;
    private String title;
    private DataMap dataMap;
    private Bitmap imageBitmap;
    
    public DataModel(int viewType, String id, String title, DataMap dataMap) {
        this.viewType = viewType;
        this.id = id;
        this.title = title;
        this.dataMap = dataMap;
    }
    
    public int getViewType() {
        return viewType;
    }
    
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public DataMap getDataMap() {
        return dataMap;
    }
    
    public void setDataMap(DataMap dataMap) {
        this.dataMap = dataMap;
    }
    
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
    
    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}
