package com.example.webook;

import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
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
import org.w3c.dom.Document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class OwnerAcceptDeclineTest {

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
        solo.enterText((EditText) solo.getView(R.id.username_input), "TestOwner1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input), "111");
        solo.clickOnButton("Log in");
        solo.sleep(1000);
    }
}
/*
    @Test
    public void SameBookRequestsTest(){
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.clickOnText("Requested");
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnMenuItem(testBook.getTitle());
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnButton("REQUESTS LIST");
        solo.assertCurrentActivity("Wrong activity", SameBookRequestList.class);
        assertTrue(solo.searchText("BorrowerBookTest"));
        assertTrue(solo.searchText("BorrowerBookTest2"));
    }

    @Test
    public void acceptTest(){
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.clickOnText("Requested");
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnMenuItem(testBook.getTitle());
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnButton("REQUESTS LIST");
        solo.assertCurrentActivity("Wrong activity", SameBookRequestList.class);
        assertTrue(solo.searchText("BorrowerBookTest"));
        assertTrue(solo.searchText("BorrowerBookTest2"));
        solo.clickOnMenuItem("BorrowerBookTest2");
        solo.clickOnButton("Accept");
        solo.assertCurrentActivity("Wrong Activity", OwnerBookProfile.class);
    }

    @Test
    public void declineTest(){
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.clickOnText("Requested");
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnMenuItem(testBook.getTitle());
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnButton("REQUESTS LIST");
        solo.assertCurrentActivity("Wrong activity", SameBookRequestList.class);
        assertTrue(solo.searchText("BorrowerBookTest"));
        assertTrue(solo.searchText("BorrowerBookTest2"));
        solo.clickOnMenuItem("BorrowerBookTest2");
        solo.clickOnButton("Decline");
        solo.assertCurrentActivity("Wrong activity", SameBookRequestList.class);
    }

    @After
    public void reset() throws Exception{
        Thread.sleep(1000);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference documentReference = db
                .collection("requests")
                .document("5510289325214");
        documentReference
                .delete();
        final CollectionReference collectionReference = db.collection("books");
        collectionReference
                .document("5510289325214")
                .update("status", "requested");
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
*/