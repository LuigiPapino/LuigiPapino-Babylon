package net.dragora.luigipapino_babylon.ui.list;

import android.content.Context;
import android.view.ViewGroup;

import net.dragora.luigipapino_babylon.commons.UltimateRecyclerViewAdapterBase;
import net.dragora.luigipapino_babylon.commons.ViewWrapper;
import net.dragora.luigipapino_babylon.model.Contact;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by nietzsche on 30/01/16.
 */
@EBean
public class ContactRecyclerAdapter extends UltimateRecyclerViewAdapterBase<Contact, ContactListItemView> {

    @RootContext
    Context context;

    @Override
    protected ContactListItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ContactListItemView_.build(context);
    }


    @Override
    public void onBindViewHolder(ViewWrapper<ContactListItemView> holder, int position) {
        ContactListItemView view = holder.getView();
        Contact item = items.get(position);
        view.bind(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
