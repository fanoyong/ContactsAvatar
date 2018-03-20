package com.grammarly.avatarcontacts.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.grammarly.avatarcontacts.R;
import com.grammarly.avatarcontacts.databinding.ContactItemBinding;
import com.grammarly.avatarcontacts.model.Contact;

import java.util.List;
import java.util.Objects;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    List<? extends Contact> mContactList;
    @Nullable
    private final ContactClickCallback mContactClickCallback;

    public ContactAdapter(@Nullable ContactClickCallback clickCallback) {
        mContactClickCallback = clickCallback;
    }

    public void setContactList(final List<? extends Contact> contactList) {
        if (mContactList == null) {
            mContactList = contactList;
            notifyItemRangeInserted(0, contactList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mContactList.size();
                }

                @Override
                public int getNewListSize() {
                    return contactList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mContactList.get(oldItemPosition).getId() ==
                            contactList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Contact newContactsList = contactList.get(newItemPosition);
                    Contact oldContactsList = mContactList.get(oldItemPosition);
                    return newContactsList.getId() == oldContactsList.getId() &&
                            Objects.equals(newContactsList.getName(), oldContactsList.getName()) &&
                            Objects.equals(newContactsList.getEmail(),
                                    oldContactsList.getEmail()) &&
                            Objects.equals(newContactsList.getPhoneNumber(),
                                    oldContactsList.getPhoneNumber()) &&
                            Objects.equals(newContactsList.getAvatarUrl(),
                                    oldContactsList.getAvatarUrl());
                }
            });
            mContactList = contactList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ContactItemBinding
                binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.contact_item,
                        parent,
                        false);
        binding.setCallback(mContactClickCallback);
        return new ContactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.mBinding.setContact(mContactList.get(position));
        holder.mBinding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return mContactList == null ? 0 : mContactList.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        final ContactItemBinding mBinding;

        public ContactViewHolder(ContactItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
