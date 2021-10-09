package com.whats.app.statussaver.statusdownloader.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import androidx.multidex.MultiDex;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.whats.app.statussaver.statusdownloader.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by theapache64 on 16/7/17.
 */

public class App extends Application {
    private boolean DEBUGGABLE = false;
    private static void initImageLoader(final Context context) {

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();

        final DisplayImageOptions defaultImageOption = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        config.defaultDisplayImageOptions(defaultImageOption);

        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(100 * 1024 * 1024); // 100 MiB
        config.memoryCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        try {
//            MultiDex.install(this);
//        }catch (Exception e){
//            //
//        }
//    }
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto_Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

//        try {
//           BugMailer.init(this, new BugMailerConfig("theapache64@gmail.com"));
////            PackageInfo info  = getPackageManager().getPackageInfo(getPackageName(), 0);
////            int flags = info.applicationInfo.flags;
////            DEBUGGABLE = (0 != (flags & ApplicationInfo.FLAG_DEBUGGABLE));
//        } catch (BugMailerException e) {
//            e.printStackTrace();
//        }
//        catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
    }

}
//class MyApplication extends Application {
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }
//}

