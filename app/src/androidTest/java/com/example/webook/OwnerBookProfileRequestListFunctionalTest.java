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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OwnerBookProfileRequestListFunctionalTest {
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
    }

    @Test
    public void ownerRequestListFunctionalTest() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestOwner1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");
        solo.sleep(3000);
        solo.clickOnText("Requested");
        solo.sleep(2000);
        solo.clickOnMenuItem("TestBook5");
        solo.sleep(1000);
        solo.clickOnButton("REQUESTS LIST");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", SameBookRequestList.class);
        assertTrue(solo.waitForText("TestBorrower1", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBorrower2", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBorrower3", 1, 1000, true, true));
        solo.clickOnMenuItem("TestBorrower2");
        solo.sleep(1000);

        assertTrue(solo.waitForText("Title: TestBook5", 1, 1000, true, true));
        assertTrue(solo.waitForText("Author: TestBook5 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("Owner: TestOwner1", 1, 1000, true, true));
        assertTrue(solo.waitForText("Borrower: TestBorrower2", 1, 1000, true, true));
        solo.clickOnText("Decline");
        solo.assertCurrentActivity("Wrong Activity", SameBookRequestList.class);
        assertFalse(solo.waitForText("TestBorrower2", 1, 2000, true, true));

        solo.clickOnMenuItem("TestBorrower3");
        solo.clickOnText("Cancel");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", SameBookRequestList.class);
        solo.clickOnMenuItem("TestBorrower3");
        solo.clickOnText("Accept");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", OwnerBookProfile.class);
        solo.clickOnButton("REQUESTS LIST");
        solo.assertCurrentActivity("Wrong Activity", SameBookRequestList.class);
        assertTrue(solo.waitForText("TestBorrower3", 1, 1000, true, true));
        assertFalse(solo.waitForText("TestBorrower1", 1, 1000, true, true));
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
