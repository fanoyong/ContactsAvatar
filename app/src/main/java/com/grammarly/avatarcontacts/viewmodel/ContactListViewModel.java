package com.grammarly.avatarcontacts.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.grammarly.avatarcontacts.AvatarContactApp;
import com.grammarly.avatarcontacts.db.entity.ContactEntity;

import java.util.List;

public class ContactListViewModel extends AndroidViewModel {
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<ContactEntity>> mObservableContacts;

    public ContactListViewModel(Application application) {
        super(application);

        mObservableContacts = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableContacts.setValue(null);

        LiveData<List<ContactEntity>>
                contacts =
                ((AvatarContactApp) application).getRepository().getContacts();

        // observe the changes of the contacts from the database and forward them
        mObservableContacts.addSource(contacts, mObservableContacts::setValue);
    }

    public LiveData<List<ContactEntity>> getContacts() {
        return mObservableContacts;
    }
}
