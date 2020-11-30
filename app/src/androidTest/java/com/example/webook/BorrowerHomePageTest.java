package com.example.webook;

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

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BorrowerHomePageTest {
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
    public void checkOnItemClick(){
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        //This part need to be change
        assertTrue(solo.waitForText("TestBook11", 1, 5000, true, true));
        solo.clickOnMenuItem("TestBook11");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", ShowBookDetail.class);
        assertTrue(solo.searchText("TestBook11 Author"));
    }


    @Test
    public void checkRequestsAndBooksButton(){
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnText("REQUESTS");
        solo.assertCurrentActivity("Wrong Activity", BorrowerRequestPageActivity.class);
        assertTrue(solo.waitForText("TestBook5", 1, 5000, true, true));
        solo.clickOnText("BOOKS");
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
    }


    @Test
    public void checkRequestedFilter(){
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.sleep(5000);
        solo.clickOnText("Requested");
        solo.sleep(2000);
        assertTrue(solo.waitForText("TestBook5", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook6", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook5 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook6 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("requested", 2, 1000, true, true));
    }


    @Test
    public void checkAcceptedFilter(){
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.sleep(5000);
        solo.clickOnText("Accepted");
        solo.sleep(2000);
        assertTrue(solo.waitForText("TestBook11", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook12", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook11 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook12 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("accepted", 2, 1000, true, true));
    }


    @Test
    public void checkBorrowedFilter(){
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.sleep(5000);
        solo.clickOnText("Borrowed");
        solo.sleep(2000);
        assertTrue(solo.waitForText("TestBook7", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook8", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook7 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook8 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("borrowed", 2, 1000, true, true));
    }


    @Test
    public void CheckAllFilter(){
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        assertTrue(solo.waitForText("TestBook5", 1, 5000, true, true));
        assertTrue(solo.waitForText("TestBook5 Author", 1, 5000, true, true));
        assertTrue(solo.waitForText("requested", 1, 5000, true, true));
        solo.clickOnText("ALL");
        assertTrue(solo.waitForText("TestBook7", 1, 5000, true, true));
        assertTrue(solo.waitForText("TestBook7 Author", 1, 5000, true, true));
        assertTrue(solo.waitForText("borrowed", 1, 5000, true, true));
        solo.clickOnText("ALL");
        assertTrue(solo.waitForText("TestBook11", 1, 5000, true, true));
        assertTrue(solo.waitForText("TestBook11 Author", 1, 5000, true, true));
        assertTrue(solo.waitForText("accepted", 1, 5000, true, true));
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
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
