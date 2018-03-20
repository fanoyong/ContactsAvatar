package com.grammarly.avatarcontacts.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.grammarly.avatarcontacts.model.Contact;

/**
 * Contract for {@link Contact} and Room
 */
@Entity(tableName = "contacts")
public class ContactEntity implements Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String avatarUrl;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public ContactEntity() {
    }

    public ContactEntity(int id, String name, String email, String phoneNumber, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatarUrl = avatarUrl;
    }

    public ContactEntity(Contact contact) {
        id = contact.getId();
        name = contact.getName();
        email = contact.getEmail();
        phoneNumber = contact.getPhoneNumber();
        avatarUrl = contact.getAvatarUrl();
    }
}
