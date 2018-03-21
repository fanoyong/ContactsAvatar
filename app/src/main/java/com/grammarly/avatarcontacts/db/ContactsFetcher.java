package com.grammarly.avatarcontacts.db;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import com.grammarly.avatarcontacts.db.entity.ContactEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.provider.ContactsContract.Contacts;

/**
 * Main source of truth that fetches contacts from local contacts DB.
 */
public class ContactsFetcher {

    public static List<ContactEntity> fetchContacts(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            return Collections.emptyList();
        }

        final String[] projectionBaseContact = new String[]{
                Contacts._ID,
                Contacts.DISPLAY_NAME,
                Contacts.HAS_PHONE_NUMBER
        };
        List<ContactEntity> contacts = new ArrayList<>();
        Cursor baseCursor = null;
        Cursor emailCursor = null;
        Cursor phoneNumberCursor = null;
        try {
            baseCursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, projectionBaseContact, null, null, null);
            if (baseCursor == null || baseCursor.getCount() < 1) {
                return Collections.emptyList();
            }

            final int colId = baseCursor.getColumnIndex(Contacts._ID);
            final int colName = baseCursor.getColumnIndex(Contacts.DISPLAY_NAME);

            while (baseCursor.moveToNext()) {
                int id = baseCursor.getInt(colId);
                String name = baseCursor.getString(colName);

                // Get Email
                String email = "No email :(";
                final String[] projectionEmail = new String[]{
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID,
                        ContactsContract.CommonDataKinds.Email.DATA,
                        ContactsContract.CommonDataKinds.Email.DISPLAY_NAME_PRIMARY};
                final String selectionEmail = ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?";
                final String[] selectionArgsEmail = new String[]{Integer.toString(id)};
                emailCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, projectionEmail, selectionEmail, selectionArgsEmail, null);
                if (emailCursor != null && emailCursor.getCount() > 0 && emailCursor.moveToFirst()) {
                    email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                }

                // Get Phone number
                String phoneNumber = "No phone number :(";
                final String[] projectionPhoneNumber = new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.NUMBER};
                final String selectionPhoneNumber = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
                final String[] selectionArgsPhoneNumber = new String[]{Integer.toString(id)};
                phoneNumberCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projectionPhoneNumber, selectionPhoneNumber, selectionArgsPhoneNumber, null);
                if (phoneNumberCursor != null && phoneNumberCursor.getCount() > 0 && phoneNumberCursor.moveToFirst()) {
                    phoneNumber = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                final ContactEntity contact = new ContactEntity();
                contact.setId(id);
                contact.setName(name);
                contact.setEmail(email);
                contact.setPhoneNumber(phoneNumber);

                // Use concatenation as identifier to get unique avatar url
                String identifier = id + name + email + phoneNumber;
                String avatarUrl = "https://api.adorable.io/avatars/100/" + identifier.hashCode();
                contact.setAvatarUrl(avatarUrl);
                contacts.add(contact);
            }
        } finally {
            maybeCloseCursor(baseCursor);
            maybeCloseCursor(emailCursor);
            maybeCloseCursor(phoneNumberCursor);
        }
        return contacts;
    }

    private static void maybeCloseCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }
}
