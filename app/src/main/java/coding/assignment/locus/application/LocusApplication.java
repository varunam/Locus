package coding.assignment.locus.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by varun.am on 27/03/19
 */
public class LocusApplication extends Application {
    
    private static Context appContext;
    private static Application application;
    
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        application = this;
    }
    
    public static Context getAppContext() {
        return appContext;
    }
    
    public static Application getApplication() {
        return application;
    }
}
