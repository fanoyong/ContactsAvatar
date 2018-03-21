package com.grammarly.avatarcontacts.db;

import com.grammarly.avatarcontacts.db.entity.ContactEntity;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class that holds values to be used for testing.
 */
public class TestData {
    static final ContactEntity
            CONTACT_ENTITY =
            new ContactEntity(1, "name1", "email1", "phone1", "url1");
    static final ContactEntity
            CONTACT_ENTITY2 =
            new ContactEntity(2, "name2", "email2", "phone2", "url2");
    static final List<ContactEntity> CONTACTS = Arrays.asList(CONTACT_ENTITY, CONTACT_ENTITY2);
}
