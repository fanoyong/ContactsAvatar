package com.grammarly.avatarcontacts.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grammarly.avatarcontacts.R;
import com.grammarly.avatarcontacts.databinding.ListFragmentBinding;
import com.grammarly.avatarcontacts.viewmodel.ContactListViewModel;

public class ContactListFragment extends Fragment {
    public static final String TAG = "ContactListViewModel";
    private ContactAdapter mContactAdapter;
    private ListFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);

        mContactAdapter = new ContactAdapter(mContactClickCallback);
        mBinding.contactsList.setAdapter(mContactAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ContactListViewModel
                viewModel =
                ViewModelProviders.of(this).get(ContactListViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(ContactListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getContacts().observe(this, contacts -> {
            if (contacts != null) {
                mBinding.setIsLoading(false);
                mContactAdapter.setContactList(contacts);
            } else {
                mBinding.setIsLoading(true);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    private final ContactClickCallback mContactClickCallback = contact -> {

        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).show(contact);
        }
    };
}
