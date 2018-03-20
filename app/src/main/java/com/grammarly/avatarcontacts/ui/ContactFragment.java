package com.grammarly.avatarcontacts.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.grammarly.avatarcontacts.R;
import com.grammarly.avatarcontacts.databinding.ContactFragmentBinding;
import com.grammarly.avatarcontacts.viewmodel.ContactViewModel;

public class ContactFragment extends Fragment {
    private static final String KEY_CONTACT_ID = "contact_id";
    private ContactFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        mBinding = DataBindingUtil.inflate(inflater, R.layout.contact_fragment, container, false);

        // Create and set the adapter for the RecyclerView.
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ContactViewModel.Factory
                factory =
                new ContactViewModel.Factory(getActivity().getApplication(),
                        getArguments().getInt(KEY_CONTACT_ID));

        final ContactViewModel
                model =
                ViewModelProviders.of(this, factory).get(ContactViewModel.class);

        mBinding.setContactViewModel(model);
        mBinding.setCallbackEmailAddress(mEmailClickCallback);
        mBinding.setCallbackPhoneNumber(mPhoneNumberClickCallback);

        subscribeToModel(model);
    }

    private void subscribeToModel(final ContactViewModel model) {

        model
                .getObservableContact()
                .observe(this, contactEntity -> model.setContact(contactEntity));
    }

    public static ContactFragment forContact(int contactId) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_CONTACT_ID, contactId);
        fragment.setArguments(args);
        return fragment;
    }

    private final PhoneNumberClickCallback mPhoneNumberClickCallback = new PhoneNumberClickCallback() {
        @Override
        public void onClick() {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                final Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mBinding.getContactViewModel().mContact.get().getPhoneNumber()));
                maybeSendIntent(intent);
            }
        }
    };


    private final EmailClickCallback mEmailClickCallback = new EmailClickCallback() {
        @Override
        public void onClick() {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, mBinding.getContactViewModel().mContact.get().getEmail());
                maybeSendIntent(intent);
            }
        }
    };

    private void maybeSendIntent(final Intent intent) {
        // Check if intent can be resolved
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "AvatarContacts"));
        }
    }
}
