package com.grammarly.avatarcontacts.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.grammarly.avatarcontacts.model.Contact;

/**
 * Contract for {@link Contact} and Room
 */
@Entity(tableName = "contacts")
public class ContactEntity implements Contact {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "email")
    private String mEmail;
    @ColumnInfo(name = "phoneNumber")
    private String mPhoneNumber;
    @ColumnInfo(name = "avatarUrl")
    private String mAvatarUrl;

    @Override
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    @Override
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    @Override
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    @Override
    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    @Ignore
    public ContactEntity() {
    }

    public ContactEntity(int id, String name, String email, String phoneNumber, String avatarUrl) {
        mId = id;
        mName = name;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mAvatarUrl = avatarUrl;
    }

    @Ignore
    public ContactEntity(Contact contact) {
        mId = contact.getId();
        mName = contact.getName();
        mEmail = contact.getEmail();
        mPhoneNumber = contact.getPhoneNumber();
        mAvatarUrl = contact.getAvatarUrl();
    }
}
