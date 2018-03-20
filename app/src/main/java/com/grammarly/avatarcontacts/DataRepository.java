package com.grammarly.avatarcontacts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.Context;

import com.grammarly.avatarcontacts.db.AppDatabase;
import com.grammarly.avatarcontacts.db.entity.ContactEntity;

import java.util.List;

public class DataRepository {
    private static DataRepository sInstance;
    private final AppDatabase mDatabase;
    private MediatorLiveData<List<ContactEntity>> mObservableContacts;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableContacts = new MediatorLiveData<>();

        mObservableContacts.addSource(mDatabase.contactDao().loadAllContacts(), contactEntities -> {
            if (mDatabase.getDatabaseCreated().getValue() != null) {
                mObservableContacts.postValue(contactEntities);
            }
        });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public void fetchContactsFromLocal(Context context, AppExecutors appExecutors) {
        mDatabase.insertDataFromLocalContacts(context, appExecutors);
    }

    public LiveData<List<ContactEntity>> getContacts() {
        return mObservableContacts;
    }

    public LiveData<ContactEntity> loadContact(final int contactId) {
        return mDatabase.contactDao().loadContact(contactId);
    }
}
