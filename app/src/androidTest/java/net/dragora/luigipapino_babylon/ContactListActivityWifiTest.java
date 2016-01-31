package net.dragora.luigipapino_babylon;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.luigipapino_babylon.model.Contact;
import net.dragora.luigipapino_babylon.network.MockAPIService;
import net.dragora.luigipapino_babylon.ui.list.ContactListActivity_;
import net.dragora.luigipapino_babylon.ui.list.ContactRecyclerAdapter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by nietzsche on 31/01/16.
 */
@RunWith(AndroidJUnit4.class)
public class ContactListActivityWifiTest {

    @Rule
    public ActivityTestRule<ContactListActivity_> contactListActivity = new ActivityTestRule<ContactListActivity_>(ContactListActivity_.class);

    @BeforeClass
    public static void setUp() {

    }

    @AfterClass
    public static void tearDown() {

    }

    @Test
    public void testWifiDisabled() {
        WifiManager wifi = (WifiManager) contactListActivity.getActivity().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);

        onView(withId(R.id.fab))
                .perform(click());
        onView(withText(R.string.no_connection))
                .check(matches(isDisplayed()));
        //TODO write IdlingResource to wait wifi availability
        wifi.setWifiEnabled(true);
    }


}
