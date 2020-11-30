package com.example.webook;

import android.widget.EditText;

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

import static org.junit.Assert.assertTrue;

public class BorrowerRequestButtonTest {
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
    public void correctActivity(){
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnText("REQUESTS");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", BorrowerRequestPageActivity.class);
    }

    @Test
    public void topButtonsCheck(){
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.sleep(1000);
        solo.clickOnText("REQUESTS");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", BorrowerRequestPageActivity.class);
        assertTrue(solo.waitForText("TestBook5", 1, 5000, true, true));
        assertTrue(solo.waitForText("TestBook6", 1, 5000, true, true));
        solo.clickOnText("Accepted");
        assertTrue(solo.waitForText("TestBook11", 1, 5000, true, true));
        assertTrue(solo.waitForText("TestBook12", 1, 5000, true, true));
        assertTrue(solo.waitForText("Owner: TestOwner1", 2, 5000, true, true));
        solo.clickOnText("Borrowed");
        assertTrue(solo.waitForText("TestBook7", 1, 5000, true, true));
        assertTrue(solo.waitForText("TestBook8", 1, 5000, true, true));
        assertTrue(solo.waitForText("Owner: TestOwner1", 2, 5000, true, true));
    }


    @Test
    public void BorrowerAcceptedDeliveryCheck() {
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.sleep(1000);
        solo.clickOnText("REQUESTS");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", BorrowerRequestPageActivity.class);
        solo.clickOnText("Accepted");
        solo.sleep(4000);
        solo.clickInList(0);
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", BorrowerRequestDelivery.class);
        assertTrue(solo.waitForText("Title: TestBook11", 1, 5000, true, true));
        assertTrue(solo.waitForText("ISBN: 1100000000000", 1, 5000, true, true));
        assertTrue(solo.waitForText("Owner: TestOwner1", 1, 5000, true, true));
        assertTrue(solo.waitForText("Status: accepted", 1, 5000, true, true));
        assertTrue(solo.waitForText("2025-12-5", 1, 5000, true, true));
        solo.clickOnText("Deliver Scan");
        solo.sleep(4000);
        solo.assertCurrentActivity("Wrong Activity", CodeScanner.class);
    }


    @Test
    public void BorrowerBorrowedReturnCheck(){
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnText("REQUESTS");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", BorrowerRequestPageActivity.class);
        solo.clickOnText("Borrowed");
        solo.sleep(1000);
        solo.clickOnMenuItem("TestBook7");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", BorrowerReturn.class);
        assertTrue(solo.waitForText("Title: TestBook7", 1, 5000, true, true));
        assertTrue(solo.waitForText("ISBN: 7000000000000", 1, 5000, true, true));
        assertTrue(solo.waitForText("Owner: TestOwner1", 1, 5000, true, true));
        assertTrue(solo.waitForText("Status: borrowed", 1, 5000, true, true));
        assertTrue(solo.waitForText("2020-9-22", 1, 5000, true, true));
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
