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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class OwnerSearchTest {
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
    public void searchUserExist(){
        // login
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestOwner1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");

        // login success, switch to owner homepage
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"TestBorrower2");
        solo.clickOnButton("Users");

        // owner search user, click on user item
        solo.assertCurrentActivity("Wrong Activity", OwnerSearchUserPage.class);
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
    public void searchUserNotExist(){
        // login
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestOwner1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");

        // login success, switch to owner homepage
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.clickOnButton("Search");
        solo.enterText((EditText) solo.getView(R.id.search_input),"reallyNobody");
        solo.clickOnButton("Users");

        // owner search user which should have no matches
        solo.assertCurrentActivity("Wrong Activity", OwnerSearchUserPage.class);
        assertFalse(solo.waitForText("reallyNobody",1,5000,true,true));

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
