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

        // Try to fetch local contacts DB on application create.
        // This will be ignored for the 'first' time since it does not have permission
        getRepository().fetchContactsFromLocal(getApplicationContext(), mAppExecutors);
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
