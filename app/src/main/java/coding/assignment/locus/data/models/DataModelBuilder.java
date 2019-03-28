package coding.assignment.locus.data.models;

public class DataModelBuilder {
    private int viewType;
    private String id;
    private String title;
    private DataMap dataMap;
    
    public DataModelBuilder setViewType(int viewType) {
        this.viewType = viewType;
        return this;
    }
    
    public DataModelBuilder setId(String id) {
        this.id = id;
        return this;
    }
    
    public DataModelBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
    
    public DataModelBuilder setDataMap(DataMap dataMap) {
        this.dataMap = dataMap;
        return this;
    }
    
    public DataModel createDataModel() {
        return new DataModel(viewType, id, title, dataMap);
    }
}