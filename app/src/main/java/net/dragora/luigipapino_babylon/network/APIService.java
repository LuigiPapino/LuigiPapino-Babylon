package net.dragora.luigipapino_babylon.network;

import net.dragora.luigipapino_babylon.model.Contact;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by nietzsche on 28/01/16.
 */
public interface APIService {
    @GET("/contacts")
    Observable<List<Contact>> getContacts();
}
