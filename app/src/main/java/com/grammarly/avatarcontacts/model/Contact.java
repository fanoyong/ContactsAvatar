package com.grammarly.avatarcontacts.model;

/**
 * Simplified model for Contact
 * <p>
 * <ul>
 * <li>
 * Name (Concatenated with first name and last name)
 * <p>
 * </li>
 * <li>E-mail address (if any)</li>
 * <li>Phone number (if any)</li>
 * <li>Avatar Url</li>
 * </ul>
 */
public interface Contact {
    int getId();

    String getName();

    String getEmail();

    String getPhoneNumber();

    String getAvatarUrl();
}
