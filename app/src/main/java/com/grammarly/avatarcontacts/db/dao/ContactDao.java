package com.grammarly.avatarcontacts.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.grammarly.avatarcontacts.db.entity.ContactEntity;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contacts")
    LiveData<List<ContactEntity>> loadAllContacts();

    // Update if existing data has been updated
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ContactEntity> contacts);

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    LiveData<ContactEntity> loadContact(int contactId);

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    ContactEntity loadContactSync(int contactId);
}
