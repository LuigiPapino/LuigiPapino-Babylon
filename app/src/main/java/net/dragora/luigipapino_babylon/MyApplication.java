package net.dragora.luigipapino_babylon;

import android.app.Application;
import android.support.annotation.NonNull;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import net.dragora.luigipapino_babylon.injection.Graph;

/**
 * Created by nietzsche on 28/01/16.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    private Graph graph;
    private RefWatcher refWatcher;

    @NonNull
    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        graph = Graph.Initializer.init(this);
    }

    public Graph getGraph() {
        return graph;
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }
}
