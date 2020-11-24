package com.example.webook;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OwnerBookProfileRequestListFunctionalTest {
    private Solo solo;
    private DataBaseTestManager dataBaseTestManager;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        dataBaseTestManager = new DataBaseTestManager();
        dataBaseTestManager.deleteUser();
        solo.sleep(1000);
        dataBaseTestManager.createTestData();
        solo.sleep(7000);
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestOwner1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");
    }

    @After
    public void after(){
        dataBaseTestManager.deleteTestData();
        solo.sleep(5000);

    }

    @Test
    public void OwnerBookProfileRequestListFunctionalTest() {
        solo.sleep(1000);
        solo.clickOnText("Requested");
        solo.sleep(1000);
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
        solo.clickOnText("Accept");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", OwnerBookProfile.class);
        solo.clickOnButton("REQUESTS LIST");
        solo.assertCurrentActivity("Wrong Activity", SameBookRequestList.class);
        assertTrue(solo.waitForText("TestBorrower3", 1, 1000, true, true));
        assertFalse(solo.waitForText("TestBorrower1", 1, 1000, true, true));
    }
}
