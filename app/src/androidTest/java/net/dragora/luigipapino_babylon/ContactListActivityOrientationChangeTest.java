package net.dragora.luigipapino_babylon;

import android.content.pm.ActivityInfo;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.dragora.luigipapino_babylon.network.MockAPIService;
import net.dragora.luigipapino_babylon.ui.list.ContactListActivity_;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

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
@RunWith(AndroidJUnit4.class)
public class ContactListActivityOrientationChangeTest {

    @Rule
    public ActivityTestRule<ContactListActivity_> contactListActivity = new ActivityTestRule<ContactListActivity_>(ContactListActivity_.class);

    @BeforeClass
    public static void setUp() {

    }

    @AfterClass
    public static void tearDown() {

    }


    @Test
    public void testOrientationChange() {
        contactListActivity.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        onView(withId(R.id.ultimate_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withText(R.string.contact_details))
                .check(matches(isDisplayed()));


        contactListActivity.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withText(R.string.contact_details))
                .check(matches(isDisplayed()));

        Espresso.pressBack();
        onView(withId(R.id.ultimate_list))
                .check(matches(isDisplayed()));
        onView(withId(R.id.ultimate_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.name_big))
                .check(matches(isDisplayed()));
    }


}
