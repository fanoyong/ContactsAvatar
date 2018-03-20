package com.grammarly.avatarcontacts.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
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
}
