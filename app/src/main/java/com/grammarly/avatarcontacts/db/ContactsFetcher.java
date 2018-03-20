package com.grammarly.avatarcontacts.db;

import com.grammarly.avatarcontacts.db.entity.ContactEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Main source of truth that fetches contacts from local contacts DB. For early stage of
 * implementation it will use fake user data generated manually
 */
public class ContactsFetcher {
    private static final String[]
            FIRST =
            new String[]{"Special edition", "New", "Cheap", "Quality", "Used"};
    private static final String[]
            SECOND =
            new String[]{"Three-headed Monkey", "Rubber Chicken", "Pint of Grog", "Monocle"};

    // TODO Remove this once plug in for actual contacts DB is done
    public static List<ContactEntity> generateContacts() {
        List<ContactEntity> contacts = new ArrayList<>(FIRST.length * SECOND.length);

        for (int i = 0; i < FIRST.length; i++) {
            for (int j = 0; j < SECOND.length; j++) {
                ContactEntity contact = new ContactEntity();
                contact.setName(FIRST[i] + " " + SECOND[j]);
                contact.setEmail("asdasd@aslkdmalskdjalksdjalksdj.com");
                contact.setPhoneNumber("123123123");
                String
                        identifier =
                        contact.getName() + contact.getEmail() + contact.getPhoneNumber();
                contact.setAvatarUrl("https://api.adorable.io/avatars/100/" +
                                             identifier.hashCode());
                contacts.add(contact);
            }
        }
        return contacts;
    }
}
