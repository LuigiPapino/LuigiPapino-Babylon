package net.dragora.luigipapino_babylon.ui.list;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.luigipapino_babylon.MyApplication;
import net.dragora.luigipapino_babylon.R;
import net.dragora.luigipapino_babylon.commons.BaseActivity;
import net.dragora.luigipapino_babylon.model.Contact;
import net.dragora.luigipapino_babylon.network.NetworkApi;
import net.dragora.luigipapino_babylon.ui.details.ContactDetailActivity_;
import net.dragora.luigipapino_babylon.ui.details.ContactDetailFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.internal.util.SubscriptionList;

/**
 * An activity representing a list of Persons. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link net.dragora.luigipapino_babylon.ui.details.ContactDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
@EActivity(R.layout.contact_list_activity)
public class ContactListActivity extends BaseActivity {

    private static final String TAG = ContactListActivity.class.getSimpleName();
    @ViewById
    Toolbar toolbar;
    @ViewById
    AppBarLayout appBar;
    @ViewById
    UltimateRecyclerView ultimateRecyclerView;
    @ViewById
    FrameLayout frameLayout;
    @ViewById
    FrameLayout contactDetailContainer;
    @ViewById
    FloatingActionButton fab;

    @Bean
    ContactRecyclerAdapter adapter;
    @Inject
    NetworkApi networkApi;
    @SystemService
    ConnectivityManager connectivityManager;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private SubscriptionList subscriptionList = new SubscriptionList();

    public ContactListActivity() {
        super();
        MyApplication.getInstance().getGraph().inject(this);

    }

    @AfterViews
    protected void setup() {
        Log.d(TAG, "setup() called with: " + "");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.contacts_list);
        if (contactDetailContainer != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        ultimateRecyclerView.setLayoutManager(new GridLayoutManager(this, mTwoPane ? 1 : 2));
        ultimateRecyclerView.setEmptyView(R.layout.empty_contact_list);
        ultimateRecyclerView.setAdapter(adapter);

        startFetch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscriptionList.add(
                networkApi.getEvents()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setNetworkEvent)
        );
        subscriptionList.add(
                networkApi.getErrors()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::showError)
        );
        subscriptionList.add(
                networkApi.getContacts()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(adapter::setItems)
        );

    }

    @Override
    protected void onPause() {
        super.onPause();
        subscriptionList.unsubscribe();
    }

    @Click
    protected void fab() {
        startFetch();
    }

    @android.support.annotation.UiThread
    private void setNetworkEvent(NetworkApi.Events event) {
        Log.d(TAG, "setNetworkEvent() called with: " + "event = [" + event + "]");
        switch (event) {
            case FETCHING:
                fab.setEnabled(false);
                ultimateRecyclerView.setRefreshing(true);
                break;
            case ERROR:
            case IDLE:
                fab.setEnabled(true);
                ultimateRecyclerView.setRefreshing(false);
                break;
        }
    }

    @android.support.annotation.UiThread
    void showError(Throwable throwable) {
        Log.d(TAG, "showError() called with: " + "throwable = [" + throwable + "]");
        throwable.printStackTrace();
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_network)
                .setMessage(throwable.getMessage())
                .setNeutralButton(R.string.ok, null)
                .show();
    }

    private void startFetch() {
        Log.d(TAG, "startFetch() called with: " + "");


        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected)
            networkApi.fetchAndStoreContacts();
        else {
            Snackbar.make(fab, R.string.no_connection, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry, v -> {
                        startFetch();
                    })
                    .show();
        }
    }

    public void showDetail(@NonNull Contact contact) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contact_detail_container,
                            ContactDetailFragment_.builder().contact(contact).build())
                    .commit();
        } else {

            ContactDetailActivity_.intent(this)
                    .contact(contact)
                    .start();
        }
    }

    @VisibleForTesting
    public NetworkApi getNetworkApi() {
        return networkApi;
    }


}
