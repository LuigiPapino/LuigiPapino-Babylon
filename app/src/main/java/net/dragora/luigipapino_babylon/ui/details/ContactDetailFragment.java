package net.dragora.luigipapino_babylon.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.dragora.luigipapino_babylon.R;
import net.dragora.luigipapino_babylon.commons.BaseFragment;
import net.dragora.luigipapino_babylon.model.Contact;
import net.dragora.luigipapino_babylon.ui.list.ContactListActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * A fragment representing a single Contact detail screen.
 * This fragment is either contained in a {@link ContactListActivity}
 * in two-pane mode (on tablets) or a {@link ContactDetailActivity}
 * on handsets.
 */
@EFragment(R.layout.contact_detail_fragment)
public class ContactDetailFragment extends BaseFragment {
    @FragmentArg
    Contact contact;

    @ViewById
    Toolbar detailToolbar;
    @ViewById
    ImageView avatarBig;
    @ViewById
    TextView nameBig;
    @ViewById
    ImageView phoneAction;
    @ViewById
    TextView phoneLabel;
    @ViewById
    LinearLayout phoneLayout;
    @ViewById
    ImageView emailAction;
    @ViewById
    TextView emailLabel;
    @ViewById
    LinearLayout emailLayout;
    @ViewById
    ImageView addressAction;
    @ViewById
    TextView addressLabel;
    @ViewById
    LinearLayout addressLayout;
    @ViewById
    TextView updatedLabel, createdLabel;

    @AfterViews
    protected void setup() {
        if (contact == null)
            return;

        nameBig.setText(contact.getName());
        phoneLabel.setText(contact.getPhoneNumber());
        addressLabel.setText(contact.getAddress());
        emailLabel.setText(contact.getEmail());

        if (!isTwoPane()) { //twoPane mode off
            Glide.with(getContext())
                    .load("http://api.adorable.io/avatars/285/" + contact.getEmail())
                    .fitCenter()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .fallback(R.drawable.ic_account_box_black_48dp)
                    .placeholder(R.drawable.avatar_placeholder)
                    .into(avatarBig);
        } else {
            avatarBig.setVisibility(View.GONE);
        }
        createdLabel.setText(getString(R.string.created_at, contact.getCreatedFormatted()));
        updatedLabel.setText(getString(R.string.updated_at, contact.getUpdatedFormatted()));

        if (TextUtils.isEmpty(contact.getPhoneNumber()))
            phoneLayout.setVisibility(View.INVISIBLE);
        if (TextUtils.isEmpty(contact.getAddress()))
            addressLayout.setVisibility(View.INVISIBLE);
        if (TextUtils.isEmpty(contact.getEmail()))
            emailLayout.setVisibility(View.INVISIBLE);
    }

    private boolean isTwoPane() {
        return getActivity() instanceof  ContactListActivity;
    }

    @Click
    protected void phoneAction() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
        startActivity(intent);
    }

    @Click
    protected void emailAction() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{contact.getEmail()});
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @Click
    protected void addressAction() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + contact.getAddress());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }


}
