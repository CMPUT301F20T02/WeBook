package com.example.webook;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BorrowerSearchTest {
    private static Solo solo;
    private static DataBaseTestManager dataBaseTestManager;

    @ClassRule
    public static ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);


    @BeforeClass
    public static void clsSetUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        dataBaseTestManager = new DataBaseTestManager();
        dataBaseTestManager.deleteUser();
        solo.sleep(1000);
        dataBaseTestManager.createTestData();
        solo.sleep(7000);
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestBorrower1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");
    }


    /**
     * Runs before all tests and creates solo instance.
     *
     * @throws Exception
     */
    /*@Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }*/


    /**
     * Gets the Activity
     *
     * @throws Exception
     */
    /*@Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }*/


    @Test
    public void borrowerSearchBookByTitleAvailable(){
        // login
        /*solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestBorrower1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");*/

        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"TestBook1");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);
        assertTrue(solo.waitForText("TestBook1",1,5000,true,true));
        assertTrue(solo.waitForText("available",1,5000,true,true));
        solo.clickOnMenuItem("TestBook1");

        // check for profile consistency
        assertTrue(solo.waitForText("TestBook1",1,5000,true,true));
        assertTrue(solo.waitForText("available",1,5000,true,true));
        assertTrue(solo.waitForText("TestBook1 Author",1,5000,true,true));
        assertTrue(solo.waitForText("This is TestBook1",1,5000,true,true));
        assertTrue(solo.waitForText("TestOwner1",1,5000,true,true));

    }


    @Test
    public void borrowerSearchBookByAuthorRequested(){
        // login
        /*solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestBorrower1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");*/

        // login success, switch to borrower homepage
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"TestBook4");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);
        assertTrue(solo.waitForText("TestBook4",1,4000));
        assertTrue(solo.waitForText("requested",1,5000,true,true));
        solo.clickOnMenuItem("TestBook4");

        // check for profile consistency
        assertTrue(solo.waitForText("TestBook4",1,5000,true,true));
        assertTrue(solo.waitForText("requested",1,5000,true,true));
        assertTrue(solo.waitForText("TestBook4 Author",1,5000,true,true));
        assertTrue(solo.waitForText("This is TestBook4",1,5000,true,true));
        assertTrue(solo.waitForText("TestOwner1",1,5000,true,true));

    }


    @Test
    public void borrowerSearchBookByISBNAvailable(){
        // login
        /*solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestBorrower1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");*/

        // login success, switch to borrower homepage
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"20000");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);
        assertTrue(solo.waitForText("TestBook2",1,4000));
        assertTrue(solo.waitForText("available",1,5000,true,true));
        solo.clickOnMenuItem("TestBook2");

        // check for profile consistency
        assertTrue(solo.waitForText("TestBook2",1,5000,true,true));
        assertTrue(solo.waitForText("available",1,5000,true,true));
        assertTrue(solo.waitForText("TestBook2 Author",1,5000,true,true));
        assertTrue(solo.waitForText("This is TestBook2",1,5000,true,true));
        assertTrue(solo.waitForText("TestOwner1",1,5000,true,true));

    }


    @Test
    public void borrowerSearchBookByDescriptionRequested(){
        // login
        /*solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestBorrower1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");*/

        // login success, switch to borrower homepage
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"is TestBook3");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);
        assertTrue(solo.waitForText("TestBook3",1,4000));
        assertTrue(solo.waitForText("available",1,5000,true,true));
        solo.clickOnMenuItem("TestBook3");

        // check for profile consistency
        assertTrue(solo.waitForText("TestBook3",1,5000,true,true));
        assertTrue(solo.waitForText("available",1,5000,true,true));
        assertTrue(solo.waitForText("TestBook3 Author",1,5000,true,true));
        assertTrue(solo.waitForText("This is TestBook3",1,5000,true,true));
        assertTrue(solo.waitForText("TestOwner1",1,5000,true,true));

    }


    @Test
    public void BorrowerSearchUserExist(){
        // login
        /*solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestBorrower1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");*/

        // login success, switch to borrower homepage
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"TestBorrower2");
        solo.clickOnButton("Users");

        // borrower search user, click on user item
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchUserPage.class);
        assertTrue(solo.waitForText("TestBorrower2",1,5000,true,true));
        assertTrue(solo.waitForText("ForBorrower2@",1,5000,true,true));
        assertTrue(solo.waitForText("6873",1,5000,true,true));
        solo.clickOnMenuItem("TestBorrower2");

        // check for profile consistency
        assertTrue(solo.waitForText("ForBorrower2",1,5000,true,true));
        assertTrue(solo.waitForText("ForBorrower2@",1,5000,true,true));
        assertTrue(solo.waitForText("6873",1,5000,true,true));
        assertTrue(solo.waitForText("borrower",1,5000,true,true));
        assertTrue(solo.waitForText("I'm TestBorrower2",1,5000,true,true));

    }


    @Test
    public void BorrowerSearchUserNotExist(){
        // login
        /*solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"ForBorrower1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");*/

        // login success, switch to borrower homepage
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"reallyNobody");
        solo.clickOnButton("Users");

        // borrower search user which should have no matches
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchUserPage.class);
        assertFalse(solo.waitForText("reallyNobody",1,5000,true,true));

    }


    @After
    public void goBack() {
        solo.goBackToActivity("BorrowerHomepage");
    }


    @AfterClass
    public static void clsTearDown() {
        dataBaseTestManager.deleteTestData();
        solo.sleep(5000);
    }
}