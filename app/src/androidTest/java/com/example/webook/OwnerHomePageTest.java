package com.example.webook;

import android.app.Activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class OwnerHomePageTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<OwnerHomepage> rule =
            new ActivityTestRule<>(OwnerHomepage.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Gets the Activity
     *
     * @throws Exception
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }


    @Test
    public void checkOnItemClick(){
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        //This part need to be change
        solo.clickOnMenuItem("Harry Potter");
        solo.assertCurrentActivity("Wrong Activity", OwnerBookProfile.class);
        assertTrue(solo.searchText("Harry Potter"));
    }

    @Test
    public void checkRequestsButton(){
        checkOnItemClick();
        solo.clickOnButton("REQUESTS");
        solo.assertCurrentActivity("Wrong Activity", SameBookRequestList.class);
        assertTrue(solo.searchText("Harry Potter"));
    }

    @Test
    public void checkFragment(){
        checkRequestsButton();
        solo.clickOnMenuItem("Harry Potter");
        assertTrue(solo.searchText("Accept"));
    }

}
