package com.grammarly.avatarcontacts.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.grammarly.avatarcontacts.AvatarContactApp;
import com.grammarly.avatarcontacts.DataRepository;
import com.grammarly.avatarcontacts.db.entity.ContactEntity;

public class ContactViewModel extends AndroidViewModel {
    private final LiveData<ContactEntity> mObservableContact;
    public ObservableField<ContactEntity> mContact = new ObservableField<>();
    private final int mContactId;

    public ContactViewModel(@NonNull Application application,
                            DataRepository repository,
                            final int contactId) {
        super(application);
        mContactId = contactId;

        mObservableContact = repository.loadContact(mContactId);
    }

    /**
     * Expose the LiveData Comments query so the UI can observe it.
     */
    public LiveData<ContactEntity> getObservableContact() {
        return mObservableContact;
    }

    public void setContact(ContactEntity contact) {
        mContact.set(contact);
    }

    /**
     * A creator is used to inject the mContact ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the mContact ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;
        private final int mContactId;
        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int contactId) {
            mApplication = application;
            mContactId = contactId;
            mRepository = ((AvatarContactApp) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ContactViewModel(mApplication, mRepository, mContactId);
        }
    }
}
