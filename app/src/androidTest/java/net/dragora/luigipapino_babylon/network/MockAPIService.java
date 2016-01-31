package net.dragora.luigipapino_babylon.network;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonObject;

import net.dragora.luigipapino_babylon.model.Contact;

import java.util.List;

import retrofit.MockRestAdapter;
import retrofit.RetrofitError;
import rx.Observable;

/**
 * Created by nietzsche on 31/01/16.
 */
public class MockAPIService implements APIService {

    private List<Contact> contacts;
    private Throwable throwable;

    public static void mock(NetworkApi networkApi, @Nullable List<Contact> contacts, @Nullable Throwable throwable) {

        // Setup MockRestAdapter without delays or possible errors
        MockRestAdapter mockRestAdapter = MockRestAdapter.from(networkApi.restAdapter);
        mockRestAdapter.setDelay(0);
        mockRestAdapter.setErrorPercentage(0);
        mockRestAdapter.setVariancePercentage(0);


        // Create the mocked service and replace it in
        MockAPIService mockService = new MockAPIService();
        mockService.contacts = contacts;
        mockService.throwable = throwable;

        // Replace the service instance with the mocked one
        networkApi.apiService = mockRestAdapter.create(APIService.class, mockService);
    }

    @Override
    public Observable<List<Contact>> getContacts() {
        if (contacts == null)
            return Observable.error(RetrofitError.unexpectedError("Mock error", throwable));
        else
            return Observable.just(contacts);
    }
}
