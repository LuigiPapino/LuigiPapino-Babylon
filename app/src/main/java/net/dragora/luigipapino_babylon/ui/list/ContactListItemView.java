package net.dragora.luigipapino_babylon.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.dragora.luigipapino_babylon.R;
import net.dragora.luigipapino_babylon.model.Contact;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by nietzsche on 30/01/16.
 */
@EViewGroup(R.layout.contact_list_item_view)
public class ContactListItemView extends LinearLayout{
    @ViewById
    ImageView avatar;
    @ViewById
    TextView name;
    @ViewById
    FrameLayout itemLayout;


    public ContactListItemView(Context context) {
        super(context);
    }

    public ContactListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ContactListItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Contact contact;
    public void bind(@NonNull Contact contact){
        this.contact = contact;
        Glide.with(getContext())
                .load("http://api.adorable.io/avatars/285/" + contact.getEmail())
                .centerCrop()
                .crossFade()
                .fallback(R.drawable.ic_account_box_black_48dp)
                .placeholder(R.drawable.ic_account_box_black_48dp)
                .into(avatar);
        name.setText(contact.getName());
    }

    @Click
    protected void itemLayout(){
        if (getContext() instanceof ContactListActivity){
            ContactListActivity activity = (ContactListActivity) getContext();
            activity.showDetail(contact);
        }
    }
}
