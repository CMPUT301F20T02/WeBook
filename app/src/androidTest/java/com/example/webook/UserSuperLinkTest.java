package com.example.webook;

import android.content.Intent;
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

public class UserSuperLinkTest {
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
    public void testSuperLinkInOwner(){
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestOwner1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        assertTrue(solo.waitForText("TestBook7", 1, 5000, true, true));
        solo.clickOnMenuItem("TestBook7");
        assertTrue(solo.waitForText("Owned by TestOwner1", 1, 5000, true, true));
        assertTrue(solo.waitForText("Borrowed by TestBorrower1", 1, 5000, true, true));
        solo.clickOnText("TestOwner1");
        solo.assertCurrentActivity("Wrong Activity", ShowUserDetail.class);
        assertTrue(solo.waitForText("TestOwner1", 1, 5000, true, true));
        assertTrue(solo.waitForText("owner", 1, 5000, true, true));
        assertTrue(solo.waitForText("6476854770", 1, 5000, true, true));
        assertTrue(solo.waitForText("ThisIsATestEmail@gmail.com", 1, 5000, true, true));
        assertTrue(solo.waitForText("Hello, I'm TestOwner1", 1, 5000, true, true));
        solo.goBack();
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", OwnerBookProfile.class);
        solo.clickOnText("TestBorrower1");
        solo.assertCurrentActivity("Wrong Activity", ShowUserDetail.class);
        assertTrue(solo.waitForText("TestBorrower1", 1, 5000, true, true));
        assertTrue(solo.waitForText("borrower", 1, 5000, true, true));
        assertTrue(solo.waitForText("6472409903", 1, 5000, true, true));
        assertTrue(solo.waitForText("ThisIsATestEmailForBorrower1@gmail.com", 1, 5000, true, true));
        assertTrue(solo.waitForText("Hello, I'm TestBorrower1", 1, 5000, true, true));
    }

    @Test
    public void testSuperLinkInBorrower() {
        solo.enterText((EditText) solo.getView(R.id.username_input),"TestBorrower1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input),"111");
        solo.clickOnButton("Log in");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", BorrowerHomepage.class);
        assertTrue(solo.waitForText("TestBook8", 1, 5000, true, true));
        solo.clickOnMenuItem("TestBook8");
        assertTrue(solo.waitForText("TestOwner1", 1, 5000, true, true));
        assertTrue(solo.waitForText("estBorrower1", 1, 5000, true, true));
        solo.clickOnText("TestOwner1");
        solo.assertCurrentActivity("Wrong Activity", ShowUserDetail.class);
        assertTrue(solo.waitForText("TestOwner1", 1, 5000, true, true));
        assertTrue(solo.waitForText("owner", 1, 5000, true, true));
        assertTrue(solo.waitForText("6476854770", 1, 5000, true, true));
        assertTrue(solo.waitForText("ThisIsATestEmail@gmail.com", 1, 5000, true, true));
        assertTrue(solo.waitForText("Hello, I'm TestOwner1", 1, 5000, true, true));
        solo.goBack();

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
