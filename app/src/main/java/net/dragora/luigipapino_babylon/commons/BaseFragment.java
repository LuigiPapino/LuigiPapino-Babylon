package net.dragora.luigipapino_babylon.commons;

import android.support.v4.app.Fragment;

import net.dragora.luigipapino_babylon.MyApplication;

/**
 * Created by nietzsche on 28/01/16.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().getRefWatcher().watch(this);
    }
}
