package com.example.webook;

import android.util.Log;
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
public class BorrowerProfileEditTest {
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
    public void checkProfile(){
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnText("ME");
        solo.assertCurrentActivity("Wrong Activity",BorrowerProfileActivity.class);
        assertTrue(solo.waitForText("TestBorrower1", 1, 5000, true, true));
        assertTrue(solo.waitForText("borrower", 1, 5000, true, true));
        assertTrue(solo.waitForText("6472409903", 1, 5000, true, true));
        assertTrue(solo.waitForText("ThisIsATestEmailForBorrower1@gmail.com", 1, 5000, true, true));
        assertTrue(solo.waitForText("Hello, I'm TestBorrower1", 1, 5000, true, true));
    }

    @Test
    public void checkEdit() throws Exception{
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        solo.clickOnText("ME");
        solo.assertCurrentActivity("Wrong Activity",BorrowerProfileActivity.class);
        solo.clickOnText("EDIT PROFILE");
        solo.assertCurrentActivity("Wrong Activity",EditUserProfileActivity.class);
        solo.sleep(1000);
        solo.clickOnButton("Cancel");
        solo.sleep(1000);
        solo.clickOnText("EDIT PROFILE");
        solo.sleep(2000);
        solo.clearEditText((EditText) solo.getView(R.id.editUserPhone));
        solo.clearEditText((EditText) solo.getView(R.id.editUserEmail));
        solo.clearEditText((EditText) solo.getView(R.id.editUserDescription));
        solo.enterText((EditText) solo.getView(R.id.editUserPhone),"6466606670");
        solo.sleep(1000);
        solo.enterText((EditText) solo.getView(R.id.editUserEmail),"NewTestBorrower1@gmail.com");
        solo.sleep(1000);
        solo.enterText((EditText) solo.getView(R.id.editUserDescription),"This is new TestBorrower1");
        solo.sleep(1000);
        solo.clickOnButton("Confirm");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity",BorrowerProfileActivity.class);
        assertTrue(solo.waitForText("6466606670", 1, 5000, true, true));
        assertTrue(solo.waitForText("NewTestBorrower1@gmail.com", 1, 5000, true, true));
        assertTrue(solo.waitForText("This is new TestBorrower1", 1, 5000, true, true));
        solo.sleep(1000);
        solo.goBack();
    }


    @After
    public void tearDown() {
        Log.d("Arrived1"," I arrived here");
        solo.sleep(2000);
        solo.finishOpenedActivities();
        Log.d("Arrived2"," I arrived here");
    }


    @AfterClass
    public static void clsTearDown() {
        Log.d("Arrived3"," I arrived here");
        soloCls.sleep(2000);
        dataBaseTestManager.deleteTestData();
        soloCls.sleep(5000);
        Log.d("Arrived4"," I arrived here");
    }
}
