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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BorrowerBookProfileTest {
    private Solo solo;
    private String status;
    private static final String TAG = "Sample";


    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    //as a borrower, the user name always test2, and the password is 222.
    //before we run the test, we initialize our three test cases's status.
    @Before
    public void setUp() throws Exception {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.enterText((EditText) solo.getView(R.id.username_input), "test2");
        solo.enterText((EditText) solo.getView(R.id.pwd_input), "222");
        solo.clickOnButton("Log in");
        final CollectionReference collectionReference2 = db.collection("requests");
        collectionReference2
                .document("BorrowerProfileTest(requested)")
                .delete();
        final CollectionReference collectionReference = db.collection("requests");
        collectionReference
                .document("BorrowerProfileTest(available)")
                .delete();
    }

    //after each test, delete new request,reset the books' status, this step double check the status which
    //makes sure we have expected status.
    @After
    public void after() throws Exception {
        Thread.sleep(1000);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("books").document("BorrowerProfileTest(available)");
        docRef.update("status","available");
        docRef = db.collection("books").document("BorrowerProfileTest(accepted)");
        docRef.update("status","accepted");
        docRef = db.collection("books").document("BorrowerProfileTest(requested)");
        docRef.update("status","requested");
        final CollectionReference collectionReference2 = db.collection("requests");
        collectionReference2
                .document("BorrowerProfileTest(requested)")
                .delete();
        final CollectionReference collectionReference = db.collection("requests");
        collectionReference
                .document("BorrowerProfileTest(available)")
                .delete();
    }

    //test the request button on borrower book profile page.
    @Test
    public void BorrowerBookProfileRequestButtonAvailableTest(){
        // login

        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"BorrowerProfileTest1");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);

        solo.clickOnMenuItem("BorrowerProfileTest1");

        // check for profile consistency
        solo.assertCurrentActivity("Wrong Activity", BorrowerBookProfile.class);

        solo.clickOnButton("Request");
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);

    }

    //When the book status is "available", test whether the request button works, this function
    //is not fully complete since our borrower page is not complete.
    //will be completed in next part.
    @Test
    public void BorrowerBookProfileAvailableBookTest(){

        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"BorrowerProfileTest1");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);

        solo.clickOnMenuItem("BorrowerProfileTest1");

        // check for profile consistency
        solo.assertCurrentActivity("Wrong Activity", BorrowerBookProfile.class);

        solo.clickOnButton("Request");
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);


    }

    //When the book status is "accept", test whether the request button works, since our borrower search
    // only find available and requested books, the search page show nothing.
    @Test
    public void BorrowerBookProfileAcceptedBookCheck() {

        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input), "BorrowerProfileTest2");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);

        assertFalse(solo.waitForText("BorrowerProfileTest2", 1, 5000, true, true));
        assertFalse(solo.waitForText("accepted", 1, 5000, true, true));
    }

    //When the book status is "requested", test whether the request button works, this function
    //is not fully complete since our borrower page is not complete.
    //will be completed in next part.
    @Test
    public void BorrowerBookProfileRequestedBookTest(){

        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"BorrowerProfileTest3");
        solo.clickOnButton("Books");

        // borrower search books, show result list, click on item to show profile
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);

        assertTrue(solo.waitForText("BorrowerProfileTest3",1,5000,true,true));
        assertTrue(solo.waitForText("requested",1,5000,true,true));
        solo.clickOnMenuItem("BorrowerProfileTest3");

        // check for profile consistency
        solo.assertCurrentActivity("Wrong Activity", BorrowerBookProfile.class);
        assertTrue(solo.waitForText("BorrowerProfileTest3",1,5000,true,true));
        assertTrue(solo.waitForText("BorrowerProfileTest3",1,5000,true,true));
        assertTrue(solo.waitForText("BorrowerProfileTest3",1,5000,true,true));
        assertTrue(solo.waitForText("BorrowerProfileTest3",1,5000,true,true));
        assertTrue(solo.waitForText("BorrowerProfileTest3",1,5000,true,true));

        solo.clickOnButton("Request");
        solo.assertCurrentActivity("Wrong Activity", BorrowerSearchBookPage.class);


    }

}