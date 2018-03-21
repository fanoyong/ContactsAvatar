package com.grammarly.avatarcontacts.db;

import android.Manifest;
import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.grammarly.avatarcontacts.LiveDataTestUtil;
import com.grammarly.avatarcontacts.db.dao.ContactDao;
import com.grammarly.avatarcontacts.db.entity.ContactEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.grammarly.avatarcontacts.db.TestData.CONTACTS;
import static com.grammarly.avatarcontacts.db.TestData.CONTACT_ENTITY;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test the implementation of {@link ContactDao}
 */
@RunWith(AndroidJUnit4.class)
public class ContactDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_CONTACTS);

    private AppDatabase mDatabase;
    private ContactDao mContactDao;


    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase =
                Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                        AppDatabase.class)
                        // allowing main thread queries, just for testing
                        .allowMainThreadQueries().build();
        mContactDao = mDatabase.contactDao();
        mContactDao.insertAll(CONTACTS);
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getContactsAfterInserted() throws InterruptedException {
        List<ContactEntity> contacts = LiveDataTestUtil.getValue(mContactDao.loadAllContacts());

        assertThat(contacts.size(), is(CONTACTS.size()));
    }

    @Test
    public void getContactById() throws InterruptedException {
        ContactEntity
                contact =
                LiveDataTestUtil.getValue(mContactDao.loadContact(CONTACT_ENTITY.getId()));

        assertThat(contact.getId(), is(CONTACT_ENTITY.getId()));
        assertThat(contact.getName(), is(CONTACT_ENTITY.getName()));
        assertThat(contact.getEmail(), is(CONTACT_ENTITY.getEmail()));
        assertThat(contact.getPhoneNumber(), is(CONTACT_ENTITY.getPhoneNumber()));
        assertThat(contact.getAvatarUrl(), is(CONTACT_ENTITY.getAvatarUrl()));
    }
}
