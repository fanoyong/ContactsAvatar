package com.grammarly.avatarcontacts;

import android.app.Application;

import com.grammarly.avatarcontacts.db.AppDatabase;

/**
 * Android Application class. Used for accessing singletons.
 */
public class AvatarContactApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
