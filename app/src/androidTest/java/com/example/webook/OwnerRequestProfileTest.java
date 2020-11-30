package com.example.webook;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.maps.MapView;
import com.robotium.solo.By;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.webook.R.id.deliver_location_map;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class OwnerRequestProfileTest {
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
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestOwner1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");
        solo.sleep(1000);
    }

    @Test
    public void checkRequestButton(){
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.clickOnText("REQUESTS");
        solo.assertCurrentActivity("Wrong Activity", OwnerRequestPageActivity.class);
    }

    @Test
    public void checkRequestedList() {
        solo.sleep(2000);
        solo.clickOnText("REQUESTS");
        solo.sleep(2000);
        solo.clickOnText("Requested");
        solo.sleep(1000);
        solo.waitForText("TestBook4", 1, 5000);
        solo.waitForText("TestBook5", 1, 5000);
        solo.waitForText("TestBook6", 1, 5000);
        solo.clickOnMenuItem("TestBook5");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", SameBookRequestList.class);
        solo.waitForText("Requested by TestBorrower1", 1, 5000);
        solo.waitForText("Requested by TestBorrower2", 1, 5000);
        solo.waitForText("Requested by TestBorrower3", 1, 5000);
        solo.clickOnMenuItem("TestBorrower2");
        solo.sleep(1000);
        solo.waitForText("Title: TestBook5", 1, 5000);
        solo.waitForText("Author: TestBook5 Author", 1, 5000);
        solo.waitForText("Owner: TestOwner1", 1, 5000);
        solo.waitForText("Borrower: TestBorrower2", 1, 5000);
        solo.clickOnButton("Cancel");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", SameBookRequestList.class);
        solo.clickOnMenuItem("TestBorrower2");
        solo.sleep(1000);
        solo.clickOnButton("Decline");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", SameBookRequestList.class);
        assertFalse(solo.waitForText("TestBorrower2", 1, 5000));

        solo.clickOnMenuItem("TestBorrower3");
        solo.clickOnButton("Accept");
        assertFalse(solo.waitForText("TestBorrower3", 1, 5000));

        solo.clickOnText("Accepted");
        assertTrue(solo.waitForText("TestBorrower3", 1, 5000));
    }

    @Test
    public void checkAcceptedList() {
        solo.sleep(2000);
        solo.clickOnText("REQUESTS");
        solo.sleep(2000);
        solo.clickOnText("Accepted");
        solo.sleep(1000);
        solo.waitForText("TestBook10", 1, 5000);
        solo.waitForText("TestBook11", 1, 5000);
        solo.waitForText("TestBook12", 1, 5000);
        solo.clickOnMenuItem("TestBook10");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", RequestProfile.class);
        solo.waitForText("Title: TestBook10", 1, 5000);
        solo.waitForText("ISBN: 0100000000000", 1, 5000);
        solo.waitForText("Owner: TestOwner1", 1, 5000);
        solo.waitForText("Requester: TestBorrower2", 1, 5000);
        solo.waitForText("Status: accepted", 1, 5000);
        solo.clickOnText("Deliver Scan");
        solo.assertCurrentActivity("Wrong Activity", CodeScanner.class);
        solo.sleep(2000);
        solo.goBackToActivity("MainActivity");
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestOwner1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");
        solo.sleep(1000);
        solo.clickOnText("REQUESTS");
        solo.sleep(1000);
        solo.clickOnText("Accepted");
        solo.sleep(1000);
        solo.clickOnMenuItem("TestBook10");
        solo.sleep(1000);
        solo.clickOnText("Set Deliver Time");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", TimePickerActivity.class);
        solo.clickOnText("PM");
        solo.sleep(1000);
        solo.clickOnButton("Set Time");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", RequestProfile.class);
        solo.clickOnText("CONFIRM");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", OwnerRequestPageActivity.class);
    }

    @Test
    public void checkBorrowedList() {
        solo.sleep(2000);
        solo.clickOnText("REQUESTS");
        solo.sleep(2000);
        solo.clickOnText("Borrowed");
        solo.sleep(1000);
        solo.waitForText("TestBook7", 1, 5000);
        solo.waitForText("TestBook8", 1, 5000);
        solo.waitForText("TestBook9", 1, 5000);
        solo.clickOnMenuItem("TestBook8");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", OwnerReturn.class);
        solo.waitForText("Title: TestBook8", 1, 5000);
        solo.waitForText("ISBN: 8000000000000", 1, 5000);
        solo.waitForText("Owner: TestOwner1", 1, 5000);
        solo.waitForText("Borrower: TestBorrower1", 1, 5000);
        solo.waitForText("Status: borrowed", 1, 5000);
        solo.clickOnText("Return Scan");
        solo.sleep(4000);
        solo.assertCurrentActivity("Wrong Activity", CodeScanner.class);
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