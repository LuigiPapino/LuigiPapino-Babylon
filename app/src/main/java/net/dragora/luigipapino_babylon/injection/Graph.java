package net.dragora.luigipapino_babylon.injection;

import android.app.Application;


import net.dragora.luigipapino_babylon.ui.list.ContactListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataStoreModule.class, NetworkModule.class})
public interface Graph {

    void inject(ContactListActivity contactListActivity);

    final class Initializer {

        public static Graph init(Application application) {
            return DaggerGraph.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }

    }
}