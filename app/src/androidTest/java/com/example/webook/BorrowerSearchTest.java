package com.example.webook;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BorrowerSearchTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

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
    public void borrowerSearchBookByTitleAvailable(){
        // login
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"test1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"145");
        solo.clickOnButton("Log in");

        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"b1title");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);
        assertTrue(solo.waitForText("b1title",1,5000,true,true));
        assertTrue(solo.waitForText("available",1,5000,true,true));
        solo.clickOnMenuItem("b1title");

        // check for profile consistency
        assertTrue(solo.waitForText("b1title",1,5000,true,true));
        assertTrue(solo.waitForText("available",1,5000,true,true));
        assertTrue(solo.waitForText("b1author",1,5000,true,true));
        assertTrue(solo.waitForText("b1search",1,5000,true,true));
        assertTrue(solo.waitForText("b1owner",1,5000,true,true));
    }


    @Test
    public void borrowerSearchBookByAuthorRequested(){
        // login
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"test1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"145");
        solo.clickOnButton("Log in");

        // login success, switch to borrower homepage
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"b2author");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);
        assertTrue(solo.waitForText("b2title",1,4000));
        assertTrue(solo.waitForText("requested",1,5000,true,true));
        solo.clickOnMenuItem("b2title");

        // check for profile consistency
        assertTrue(solo.waitForText("b2title",1,5000,true,true));
        assertTrue(solo.waitForText("requested",1,5000,true,true));
        assertTrue(solo.waitForText("b2author",1,5000,true,true));
        assertTrue(solo.waitForText("b2 test",1,5000,true,true));
        assertTrue(solo.waitForText("b2owner",1,5000,true,true));
    }


    @Test
    public void borrowerSearchBookByISBNAvailable(){
        // login
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"test1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"145");
        solo.clickOnButton("Log in");

        // login success, switch to borrower homepage
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"b3test");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);
        assertTrue(solo.waitForText("b3title",1,4000));
        assertTrue(solo.waitForText("requested",1,5000,true,true));
        solo.clickOnMenuItem("b3title");

        // check for profile consistency
        assertTrue(solo.waitForText("b3title",1,5000,true,true));
        assertTrue(solo.waitForText("requested",1,5000,true,true));
        assertTrue(solo.waitForText("b3author",1,5000,true,true));
        assertTrue(solo.waitForText("b3 test",1,5000,true,true));
        assertTrue(solo.waitForText("b3owner",1,5000,true,true));
    }

    @Test
    public void borrowerSearchBookByDescriptionRequested(){
        // login
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"test1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"145");
        solo.clickOnButton("Log in");

        // login success, switch to borrower homepage
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"b4 status");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);
        assertTrue(solo.waitForText("b4title",1,4000));
        assertTrue(solo.waitForText("available",1,5000,true,true));
        solo.clickOnMenuItem("b4title");

        // check for profile consistency
        assertTrue(solo.waitForText("b4title",1,5000,true,true));
        assertTrue(solo.waitForText("available",1,5000,true,true));
        assertTrue(solo.waitForText("b4author",1,5000,true,true));
        assertTrue(solo.waitForText("b4 status",1,5000,true,true));
        assertTrue(solo.waitForText("b4owner",1,5000,true,true));
    }

    @Test
    public void BorrowerSearchUserExist(){
        // login
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"test1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"145");
        solo.clickOnButton("Log in");

        // login success, switch to borrower homepage
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"sampleuser1");
        solo.clickOnButton("Users");

        // borrower search user, click on user item
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchUserPage.class);
        assertTrue(solo.waitForText("sampleuser1",1,5000,true,true));
        assertTrue(solo.waitForText("10010@",1,5000,true,true));
        assertTrue(solo.waitForText("10086",1,5000,true,true));
        solo.clickOnMenuItem("sampleuser1");

        // check for profile consistency
        assertTrue(solo.waitForText("sample user 1",1,5000,true,true));
        assertTrue(solo.waitForText("10010@",1,5000,true,true));
        assertTrue(solo.waitForText("10086",1,5000,true,true));
        assertTrue(solo.waitForText("borrower",1,5000,true,true));
        assertTrue(solo.waitForText("sampleuser1",1,5000,true,true));
    }

    @Test
    public void BorrowerSearchUserNotExist(){
        // login
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"test1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"145");
        solo.clickOnButton("Log in");

        // login success, switch to borrower homepage
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"reallynobody");
        solo.clickOnButton("Users");

        // borrower search user which should have no matches
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchUserPage.class);
        assertFalse(solo.waitForText("reallynobody",1,5000,true,true));
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}
