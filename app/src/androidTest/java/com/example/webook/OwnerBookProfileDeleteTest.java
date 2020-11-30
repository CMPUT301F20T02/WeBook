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

public class OwnerBookProfileDeleteTest {
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
    public void checkOwnerBookProfileEditTest() {
        solo.waitForText("TestBook3");
        solo.clickOnMenuItem("TestBook3");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        solo.clickOnButton("Delete");
        assertTrue(solo.waitForText("Are you sure you want to delete this book?", 1, 5000, true, true));
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        solo.sleep(1000);
        assertFalse(solo.waitForText("TestBook3", 1, 3000, true, true));


        solo.clickOnText("Requested");
        solo.clickOnMenuItem("TestBook5");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        solo.clickOnButton("Delete");
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        solo.clickOnButton("Cancel");
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);

        solo.clickOnButton("Delete");
        assertTrue(solo.waitForText("Are you sure you want to delete this book?", 1, 5000, true, true));
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.sleep(1000);
        assertFalse(solo.waitForText("TestBook5", 1, 3000, true, true));

        solo.clickOnText("Accepted");
        solo.sleep(1000);
        solo.clickOnMenuItem("TestBook12 Author");
        solo.sleep(2000);
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        solo.clickOnButton("Delete");
        assertTrue(solo.waitForText("Are you sure you want to delete this book?", 1, 5000, true, true));
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.sleep(1000);
        assertFalse(solo.waitForText("TestBook12 Author", 1, 3000, true, true));

        solo.clickOnText("Borrowed");
        solo.clickOnMenuItem("TestBook9");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        solo.clickOnButton("Delete");
        assertTrue(solo.waitForText("Are you sure you want to delete this book?", 1, 5000, true, true));
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.sleep(1000);
        assertFalse(solo.waitForText("TestBook9 Author", 1, 3000, true, true));
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
