package net.dragora.luigipapino_babylon.network;

import android.support.annotation.VisibleForTesting;
import android.util.Log;


import net.dragora.luigipapino_babylon.BuildConfig;
import net.dragora.luigipapino_babylon.data.ContactsStore;
import net.dragora.luigipapino_babylon.model.Contact;

import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;
import rx.subjects.PublishSubject;


public class NetworkApi {

    public static final String BASE_HOST = "http://fast-gorge.herokuapp.com/";
    private static final String TAG = NetworkApi.class.getSimpleName();
    public APIService apiService;
    @VisibleForTesting
    public RestAdapter restAdapter;
    public PublishSubject<Throwable> errorSubject = PublishSubject.create();
    public PublishSubject<Events> eventsSubject = PublishSubject.create();
    private ContactsStore contactsStore;
    public NetworkApi(ContactsStore contactsStore) {
        this.contactsStore = contactsStore;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_HOST)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();
        apiService = restAdapter.create(APIService.class);
    }

    /**
     * Fetch contacts list from network and store in ContactsStore
     *
     * @return observable that emits the list of contacts
     */
    public Observable<List<Contact>> fetchAndStoreContacts() {
        eventsSubject.onNext(Events.FETCHING);
        apiService.getContacts()
                .subscribe(this::setContacts, this::setError);
        return contactsStore.getContacts();
    }

    public Observable<List<Contact>> getContacts() {
        return contactsStore.getContacts();
    }

    private void setContacts(List<Contact> contacts) {
        contactsStore.setContacts(contacts);
        eventsSubject.onNext(Events.IDLE);
    }

    private void setError(Throwable throwable) {
        Log.d(TAG, "setError() called with: " + "throwable = [" + throwable + "]");
        eventsSubject.onNext(Events.ERROR);
        errorSubject.onNext(throwable);
    }

    /**
     * Return all errors raised from network calls
     *
     * @return observable that emits errors raised
     */
    public Observable<Throwable> getErrors() {
        return errorSubject.asObservable();
    }

    /**
     * Return the events for network calls
     *
     * @return observable that emits events
     */
    public Observable<Events> getEvents() {
        return eventsSubject.asObservable();
    }

    public enum Events {
        FETCHING, IDLE, ERROR
    }
}