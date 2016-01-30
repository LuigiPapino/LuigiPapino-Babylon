package net.dragora.luigipapino_babylon.injection;

import android.content.Context;


import net.dragora.luigipapino_babylon.data.ContactsStore;
import net.dragora.luigipapino_babylon.data.ContactsStoreFromProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nietzsche on 27/12/15.
 */

@Module
public class DataStoreModule {

    @Singleton
    @Provides
    ContactsStore provideContactsStore(@ForApplication  Context context){
        return new ContactsStoreFromProvider(context);
    }

}
