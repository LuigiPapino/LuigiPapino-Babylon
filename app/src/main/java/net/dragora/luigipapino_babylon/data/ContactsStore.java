package net.dragora.luigipapino_babylon.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.dragora.luigipapino_babylon.model.Contact;

import java.util.List;

import rx.Observable;

/**
 * Created by nietzsche on 28/01/16.
 * Provide the list of contacts
 */
public interface ContactsStore {


    @NonNull
    /**
     * return an observable that emit a list of contacts whenever there is a new or changed list
     */
    Observable<List<Contact>> getContacts();

    /**
     * Overwrite the current list of contacts
     * @param contacts list of contacts
     */
    void setContacts(@Nullable List<Contact> contacts);

    @Nullable
    Contact get(int id);

}
