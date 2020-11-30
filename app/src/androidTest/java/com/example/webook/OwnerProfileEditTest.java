package com.example.webook;

import android.widget.Button;
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

@RunWith(AndroidJUnit4.class)
public class OwnerProfileEditTest {
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
    public void checkEditOwnerProfile(){
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.clickOnText("ME");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", OwnerProfileActivity.class);
        solo.clickOnButton("EDIT PROFILE");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", EditUserProfileActivity.class);
        solo.clickOnButton("Cancel");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", OwnerProfileActivity.class);


        solo.clickOnButton("EDIT PROFILE");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", EditUserProfileActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.editUserEmail));
        solo.clearEditText((EditText) solo.getView(R.id.editUserPhone));
        solo.clearEditText((EditText) solo.getView(R.id.editUserDescription));
        EditText email = (EditText) solo.getView(R.id.editUserEmail);
        EditText phone =  (EditText) solo.getView(R.id.editUserPhone);
        EditText description =  (EditText) solo.getView(R.id.editUserDescription);
        solo.enterText(email, "TestOwner1EditedEmail@gmail.com");
        solo.enterText(phone, "6476855590");
        solo.enterText(description, "This description has changed");
        solo.clickOnView((Button) solo.getView(R.id.editUserConfirm));
        solo.assertCurrentActivity("Wrong Activity", OwnerProfileActivity.class);
        solo.waitForActivity("OwnerProfileActivity",5000);
        solo.waitForText("TestOwner1EditedEmail@gmail.com", 1, 5000);
        solo.waitForText("6476855590", 1, 5000);
        solo.waitForText("This description has changed", 1, 5000);
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
