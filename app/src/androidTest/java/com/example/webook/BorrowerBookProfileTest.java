package com.example.webook;


import android.app.Activity;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BorrowerBookProfileTest {
    private Solo solo;
    private static DataBaseTestManager dataBaseTestManager;
    private static Solo soloCls;

    @ClassRule
    public static ActivityTestRule<MainActivity> ruleCls =
            new ActivityTestRule<>(MainActivity.class, true, true);


    @BeforeClass
    public static void clsSetUp() {
        soloCls = new Solo(InstrumentationRegistry.getInstrumentation(), ruleCls.getActivity());
        dataBaseTestManager = new DataBaseTestManager();
        dataBaseTestManager.deleteUser();
        soloCls.sleep(1000);
        dataBaseTestManager.createTestData();
        soloCls.sleep(5000);
    }


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
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestBorrower1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");
        solo.sleep(1000);
    }

    @Test
    public void BorrowerBookProfileTest(){
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"TestBook3");
        solo.clickOnButton("Books");

        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);

        solo.clickOnMenuItem("TestBook3");

        // check for profile consistency
        solo.assertCurrentActivity("Wrong Activity", BorrowerBookProfile.class);

        assertTrue(solo.waitForText("TestBook3",1,3000,true,true));
        assertTrue(solo.waitForText("TestBook3 Author",1,3000,true,true));
        assertTrue(solo.waitForText("3000000000000",1,3000,true,true));
        assertTrue(solo.waitForText("Owned by TestOwner1",1,3000,true,true));
        assertTrue(solo.waitForText("Borrowed by",1,3000,true,true));
        assertTrue(solo.waitForText("This is TestBook3",1,3000,true,true));
    }

    //test the request button on borrower book profile page. When the status is available.
    @Test
    public void BorrowerBookProfileAvailableBookCheck(){
        // login

        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"TestBook3");
        solo.clickOnButton("Books");
        solo.sleep(1000);
        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);

        solo.clickOnMenuItem("TestBook3");
        solo.sleep(1000);

        // check for profile consistency
        solo.assertCurrentActivity("Wrong Activity", BorrowerBookProfile.class);

        solo.clickOnButton("Request");
        soloCls.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);
        solo.goBackToActivity("BorrowerHomepage");

        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"TestBook3");
        solo.clickOnButton("Books");

        assertTrue(solo.waitForText("TestBook3", 1, 5000, true, true));
        assertTrue(solo.waitForText("requested", 1, 5000, true, true));

        solo.clickOnMenuItem("TestBook3");
        solo.sleep(1000);
        assertTrue(solo.waitForText("Book Status: requested", 1, 5000, true, true));

    }

    //When the book status is "accept", test whether the request button works, since our borrower search
    // only find available and requested books, the search page show nothing.
    @Test
    public void BorrowerBookProfileBorrowedBookCheck() {

        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input), "TestBook9");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);

        assertFalse(solo.waitForText("TestBook9", 1, 5000, true, true));
        assertFalse(solo.waitForText("borrowed", 1, 5000, true, true));
    }

    //When the book status is "accept", test whether the request button works, since our borrower search
    // only find available and requested books, the search page show nothing.
    @Test
    public void BorrowerBookProfileAcceptedBookCheck() {

        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input), "TestBook12");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);

        assertFalse(solo.waitForText("TestBook12", 1, 5000, true, true));
        assertFalse(solo.waitForText("accepted", 1, 5000, true, true));
    }

    //When the book status is "requested", test whether the request button works, this function
    //is not fully complete since our borrower page is not complete.
    //will be completed in next part.
    @Test
    public void BorrowerBookProfileRequestedBookTest(){

        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"TestBook6");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);

        assertTrue(solo.waitForText("TestBook6",1,5000,true,true));
        assertTrue(solo.waitForText("requested",1,5000,true,true));
        solo.clickOnMenuItem("TestBook6");

        // check for profile consistency
        solo.assertCurrentActivity("Wrong Activity", BorrowerBookProfile.class);

        solo.clickOnButton("Request");
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);

    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


    @AfterClass
    public static void clsTearDown() {
        dataBaseTestManager.deleteTestData();
        soloCls.sleep(5000);
    }

}