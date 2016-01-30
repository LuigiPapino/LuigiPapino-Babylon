package net.dragora.luigipapino_babylon.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import net.dragora.luigipapino_babylon.R;
import net.dragora.luigipapino_babylon.commons.BaseActivity;
import net.dragora.luigipapino_babylon.model.Contact;
import net.dragora.luigipapino_babylon.ui.list.ContactListActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

/**
 * An activity representing a single Contact detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ContactListActivity}.
 */
@EActivity(R.layout.contact_detail_activity)
public class ContactDetailActivity extends BaseActivity {


    private static final String TAG = ContactDetailActivity.class.getSimpleName();
    @ViewById
    Toolbar detailToolbar;
    @ViewById
    AppBarLayout appBar;
    @ViewById
    FrameLayout contactDetailContainer;


    @AfterViews
    protected void setup() {
        Log.d(TAG, "setup() called with: " + "");
        setSupportActionBar(detailToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.contact_details);

        }
        if (shouldAddFragment) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contact_detail_container,
                            ContactDetailFragment_.builder().contact(contact).build())
                    .commit();
        }
    }

    @Extra
    Contact contact;

    private boolean shouldAddFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            shouldAddFragment = true;
        }
    }


    @OptionsItem(android.R.id.home)
    protected void menuBack(){
        navigateUpTo(new Intent(this, ContactListActivity.class));
    }
}
