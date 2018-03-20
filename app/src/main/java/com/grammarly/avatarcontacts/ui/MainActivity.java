package com.grammarly.avatarcontacts.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.grammarly.avatarcontacts.R;
import com.grammarly.avatarcontacts.model.Contact;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 65535;
    private View mContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mContainer = findViewById(R.id.fragment_container);

        if (savedInstanceState == null) {
            ContactListFragment fragment = new ContactListFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, ContactListFragment.TAG)
                    .commit();
        }
    }

    public void show(Contact contact) {

        ContactFragment contactFragment = ContactFragment.forContact(contact.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("mContact")
                .replace(R.id.fragment_container, contactFragment, null)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission
                            .READ_CONTACTS)) {
                Snackbar
                        .make(mContainer,
                                "Please allow permission to read your contacts.",
                                Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK",
                                view -> ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.READ_CONTACTS},
                                        PERMISSIONS_REQUEST_READ_CONTACTS))
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            // no-op
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // TODO Initiate ContactsFetcher here
                } else {
                    // TODO Maybe continue?
                }
                return;
            }
        }
    }
}
