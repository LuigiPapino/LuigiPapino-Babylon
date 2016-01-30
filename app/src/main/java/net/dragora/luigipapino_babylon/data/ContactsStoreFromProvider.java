package net.dragora.luigipapino_babylon.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.dragora.luigipapino_babylon.data.schematic_providers.ContactColumns;
import net.dragora.luigipapino_babylon.data.schematic_providers.ContactsProvider;
import net.dragora.luigipapino_babylon.model.Contact;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by nietzsche on 28/01/16.
 * <p>
 * Implementation of @ContactsStore that retrieve the data from a ContentProvider
 */
public class ContactsStoreFromProvider implements ContactsStore {
    private static final String TAG = ContactsStoreFromProvider.class.getSimpleName();
    private static Gson gson = new GsonBuilder().create();
    private BehaviorSubject<List<Contact>> behaviorSubject = BehaviorSubject.create();
    private Context context;

    public ContactsStoreFromProvider(@NonNull Context context) {
        Log.d(TAG, "ContactsStoreFromProvider() called with: " + "context = [" + context + "]");
        this.context = context;
        // register for changes
        getContentResolver()
                .registerContentObserver(ContactsProvider.Contacts.CONTACTS, true, getContentObserver());
        //init subject
        queryAndNext();
    }

    @Override
    @NonNull
    public Observable<List<Contact>> getContacts() {
        Log.d(TAG, "getContacts() called with: " + "");
        return behaviorSubject.asObservable();
    }

    @Override
    public void setContacts(@Nullable List<Contact> contacts) {
        getContentResolver()
                .delete(ContactsProvider.Contacts.CONTACTS, null, null);
        if (contacts == null)
            return;
        ContentValues[] valuesArray = new ContentValues[contacts.size()];
        for (int i = 0; i < valuesArray.length; i++) {
            ContentValues values = new ContentValues();
            valuesArray[i] = values;
            Contact contact = contacts.get(i);

            values.put(ContactColumns._ID, contact.getId() );
            values.put(ContactColumns.JSON, gson.toJson(contact));
        }

        getContentResolver().bulkInsert(ContactsProvider.Contacts.CONTACTS, valuesArray);
    }

    private ContentResolver getContentResolver() {
        return context.getContentResolver();
    }

    @Override
    @Nullable
    public Contact get(int id) {
        return null;
    }

    @NonNull
    protected ContentObserver getContentObserver() {
        HandlerThread handlerThread = new HandlerThread(this.getClass().getSimpleName());
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        return new ContentObserver(handler) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Log.v(TAG, "onChange(" + uri + ")");
                queryAndNext();
            }
        };
    }

    /**
     * Query the provider and add the result to the Subject
     */
    private void queryAndNext() {
        Cursor cursor = getContentResolver().query(ContactsProvider.Contacts.CONTACTS,
                new String[]{ContactColumns._ID, ContactColumns.JSON}
                , null, null, null);
        behaviorSubject.onNext(cursorToList(cursor));
    }

    @NonNull
    private List<Contact> cursorToList(@Nullable Cursor cursor) {
        ArrayList<Contact> contacts = new ArrayList<>(0);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    contacts = new ArrayList<>(cursor.getCount());
                    int jsonColIndex = cursor.getColumnIndex(ContactColumns.JSON);
                    do {
                        contacts.add(gson.fromJson(cursor.getString(jsonColIndex), Contact.class));
                    } while (cursor.moveToNext());
                }

            } finally {
                cursor.close();
            }
        }

        return contacts;
    }

}
