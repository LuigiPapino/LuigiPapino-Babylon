package net.dragora.luigipapino_babylon.injection;


import net.dragora.luigipapino_babylon.data.ContactsStore;
import net.dragora.luigipapino_babylon.network.NetworkApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public final class NetworkModule {

    @Provides
    @Singleton
    public NetworkApi provideNetworkApi(ContactsStore contactsStore) {
        return new NetworkApi(contactsStore);
    }

}