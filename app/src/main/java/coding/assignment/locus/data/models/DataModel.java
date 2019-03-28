package coding.assignment.locus.data.models;

/**
 * Created by varun.am on 27/03/19
 */
public class DataModel {
    
    private int viewType;
    private String id;
    private String title;
    private DataMap dataMap;
    private String imageFilePath;
    
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
    
    public String getImageFilePath() {
        return imageFilePath;
    }
    
    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }
}
