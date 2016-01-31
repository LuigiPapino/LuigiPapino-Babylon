package net.dragora.luigipapino_babylon;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.text.TextUtils;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.luigipapino_babylon.model.Contact;
import net.dragora.luigipapino_babylon.ui.list.ContactListActivity;
import net.dragora.luigipapino_babylon.ui.list.ContactRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by nietzsche on 31/01/16.
 */
public class Utils {

    public static List<Contact> getTestContacts() {
        ArrayList<Contact> contacts = new ArrayList<>(15);
        for (int i = 0; i < 15; i++) {
            Contact contact = new Contact();
            contacts.add(contact);

            contact.setId(i);
            contact.setFirstName("First" + i);
            contact.setSurname("Last" + i);
            contact.setCreatedAt("2015-11-24T01:26:52.000Z"); //TODO generate different date
            contact.setUpdatedAt("2015-11-24T01:26:52.000Z"); //TODO generate different date
            contact.setEmail(String.format("test%d@test.com", i));
            if (i % 2 == 0)
                contact.setAddress("Address" + i);
            if (i % 3 == 0)
                contact.setPhoneNumber("Phone" + i);


        }
        return contacts;
    }

    /**
     * Click on all contacts and check for corrects data displayed
     *
     * @param isLandscape
     */
    public static void traverseList(ContactListActivity activity, boolean isLandscape) {
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
        UltimateRecyclerView ultimateRecyclerView = (UltimateRecyclerView) activity.findViewById(R.id.ultimate_recycler_view);
        ContactRecyclerAdapter adapter = (ContactRecyclerAdapter) ultimateRecyclerView.getAdapter();

        for (int i = 0; i < adapter.getAdapterItemCount(); i++) {
            Contact contact = adapter.getItems().get(i);
            //LIST
            onView(withId(R.id.ultimate_list))
                    .perform(RecyclerViewActions.scrollToPosition(i));
            onView(withText(contact.getName()))
                    .check(matches(isDisplayed())); //TODO contacts with same name
            onView(withId(R.id.ultimate_list))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));
            //DETAILS
            onView(withId(R.id.name_big))
                    .check(matches(isDisplayed()))
                    .check(matches(withText(contact.getName())));
            onView(withId(R.id.createdLabel))
                    .check(matches(isDisplayed()));
            onView(withId(R.id.updatedLabel))
                    .check(matches(isDisplayed()));
            if (TextUtils.isEmpty(contact.getPhoneNumber()))
                onView(withId(R.id.phoneAction))
                        .check(matches(not(isDisplayed())));
            if (TextUtils.isEmpty(contact.getEmail()))
                onView(withId(R.id.emailAction))
                        .check(matches(not(isDisplayed())));
            if (TextUtils.isEmpty(contact.getAddress()))
                onView(withId(R.id.addressAction))
                        .check(matches(not(isDisplayed())));

            onView(withId(R.id.avatar_big))
                    .check(matches(isLandscape ?
                            not(isDisplayed()) :
                            isDisplayed()
                    ));

            if (!isLandscape)
                Espresso.pressBack();

        }
    }
}
