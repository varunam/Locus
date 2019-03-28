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
    
    /**
     * method to get applicationContext throught the app
     * @return
     */
    public static Context getAppContext() {
        return appContext;
    }
    
    /**
     * method to get application instance
     * @return
     */
    public static Application getApplication() {
        return application;
    }
}
