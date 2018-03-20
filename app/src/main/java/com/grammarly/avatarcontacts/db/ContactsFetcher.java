package com.grammarly.avatarcontacts.db;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.grammarly.avatarcontacts.db.entity.ContactEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.provider.ContactsContract.Contacts;

/**
 * Main source of truth that fetches contacts from local contacts DB.
 */
public class ContactsFetcher {

    private static final String TAG = "ContactsFetcher";

    public static List<ContactEntity> fetchContacts(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            return Collections.emptyList();
        }
        Cursor cur = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        final int colId = cur.getColumnIndex(Contacts._ID);
        final int colName = cur.getColumnIndex(Contacts.DISPLAY_NAME);
        List<ContactEntity> contacts = new ArrayList<>();
        Log.v(TAG, "count:" + cur.getCount());
        while (cur.moveToNext()) {
            ContactEntity contact = new ContactEntity();
            contact.setId(cur.getInt(colId));
            contact.setName(cur.getString(colName));
            contact.setEmail("email@email.com");
            contact.setPhoneNumber("1231231234");
            String
                    identifier =
                    contact.getName() + contact.getEmail() + contact.getPhoneNumber();

            String avatarUrl = "https://api.adorable.io/avatars/100/" +
                    identifier.hashCode();
            Log.v(TAG, "avatarUrl:" + avatarUrl);
            contact.setAvatarUrl(avatarUrl);
            contacts.add(contact);
        }

        return contacts;
    }
}
