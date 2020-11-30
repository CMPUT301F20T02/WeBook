package com.example.webook;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.BatchUpdateException;

@RunWith(AndroidJUnit4.class)
public class OwnerHomePageTest {
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
    public void checkOnItemClick(){
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        //This part need to be change
        assertTrue(solo.waitForText("TestBook3", 1, 5000, true, true));
        solo.clickOnMenuItem("TestBook3");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", OwnerBookProfile.class);
        assertTrue(solo.searchText("TestBook3"));
    }


    @Test
    public void checkRequestsButton(){
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.clickOnText("REQUESTS");
        solo.assertCurrentActivity("Wrong Activity", OwnerRequestPageActivity.class);
        assertTrue(solo.waitForText("TestBook4", 1, 5000, true, true));
    }


    @Test
    public void checkAvailableButton(){
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.sleep(5000);
        solo.clickOnText("Available");
        solo.sleep(2000);
        assertTrue(solo.waitForText("TestBook1", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook2", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook3", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook1 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook2 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook3 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("available", 3, 1000, true, true));
    }


    @Test
    public void checkRequestedButton(){
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.sleep(5000);
        solo.clickOnText("Requested");
        solo.sleep(2000);
        assertTrue(solo.waitForText("TestBook4", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook5", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook6", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook4 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook5 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook6 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("requested", 3, 1000, true, true));
    }


    @Test
    public void checkAcceptedButton(){
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.sleep(5000);
        solo.clickOnText("Accepted");
        solo.sleep(2000);
        assertTrue(solo.waitForText("TestBook10", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook11", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook12", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook10 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook11 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook12 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("accepted", 3, 1000, true, true));
    }


    @Test
    public void checkBorrowedButton(){
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.sleep(5000);
        solo.clickOnText("Borrowed");
        solo.sleep(2000);
        assertTrue(solo.waitForText("TestBook7", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook8", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook9", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook7 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook8 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("TestBook9 Author", 1, 1000, true, true));
        assertTrue(solo.waitForText("borrowed", 3, 1000, true, true));
    }


    @Test
    public void CheckAllButton(){
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        assertTrue(solo.waitForText("TestBook3", 1, 5000, true, true));
        assertTrue(solo.waitForText("TestBook3 Author", 1, 5000, true, true));
        assertTrue(solo.waitForText("available", 1, 5000, true, true));
        solo.clickOnText("ALL");
        assertTrue(solo.waitForText("TestBook4", 1, 5000, true, true));
        assertTrue(solo.waitForText("TestBook4 Author", 1, 5000, true, true));
        assertTrue(solo.waitForText("requested", 1, 5000, true, true));
        solo.clickOnText("ALL");
        assertTrue(solo.waitForText("TestBook7", 1, 5000, true, true));
        assertTrue(solo.waitForText("TestBook7 Author", 1, 5000, true, true));
        assertTrue(solo.waitForText("accepted", 1, 5000, true, true));
        solo.clickOnText("ALL");
        assertTrue(solo.waitForText("TestBook12", 1, 5000, true, true));
        assertTrue(solo.waitForText("TestBook12 Author", 1, 5000, true, true));
        assertTrue(solo.waitForText("borrowed", 1, 5000, true, true));
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
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
