package com.example.webook;

import android.app.Instrumentation;
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

public class LoginPageTest {
    private Solo solo;
    private static DataBaseTestManager dataBaseTestManager;
    private static Solo soloCls;

    @ClassRule
    public static ActivityTestRule<MainActivity> ruleCls =
            new ActivityTestRule<MainActivity>(MainActivity.class, true, true);


    @BeforeClass
    public static void clsSetUp() {
        soloCls = new Solo(InstrumentationRegistry.getInstrumentation(), ruleCls.getActivity());
        dataBaseTestManager = new DataBaseTestManager();
        dataBaseTestManager.deleteSignedUpUsers();
        soloCls.sleep(1000);
        dataBaseTestManager.createTestData();
        soloCls.sleep(5000);
    }


    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);


    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void checkSignUpOwner() {
        solo.clickOnButton("Sign up");
        solo.waitForActivity("SignUpActivity", 3000);
        solo.enterText((EditText) solo.getView(R.id.username_signup), "SignUpOwner");
        solo.enterText((EditText) solo.getView(R.id.pwd_signup), "SignUpPWD");
        solo.enterText((EditText) solo.getView(R.id.phone_number_signup), "SignUpPhone");
        solo.enterText((EditText) solo.getView(R.id.email_signup), "SignUpEmail");
        solo.enterText((EditText) solo.getView(R.id.description_signup), "SignUpDescription");
        solo.clickOnButton("sign up as owner");
        solo.waitForActivity("MainActivity", 3000);
        solo.enterText((EditText) solo.getView(R.id.username_input), "SignUpOwner");
        solo.enterText((EditText) solo.getView(R.id.pwd_input), "SignUpPWD");
        solo.clickOnButton("Log in");
        solo.waitForActivity("OwnerHomepage", 3000);
    }


    @Test
    public void checkSignUpBorrower() {
        solo.clickOnButton("Sign up");
        solo.waitForActivity("SignUpActivity", 3000);
        solo.enterText((EditText) solo.getView(R.id.username_signup), "SignUpBorrower");
        solo.enterText((EditText) solo.getView(R.id.pwd_signup), "SignUpPWD");
        solo.enterText((EditText) solo.getView(R.id.phone_number_signup), "SignUpPhone");
        solo.enterText((EditText) solo.getView(R.id.email_signup), "SignUpEmail");
        solo.enterText((EditText) solo.getView(R.id.description_signup), "SignUpDescription");
        solo.clickOnButton("sign up as borrower");
        solo.waitForActivity("MainActivity", 3000);
        solo.enterText((EditText) solo.getView(R.id.username_input), "SignUpBorrower");
        solo.enterText((EditText) solo.getView(R.id.pwd_input), "SignUpPWD");
        solo.clickOnButton("Log in");
        solo.waitForActivity("BorrowerHomepage", 3000);
    }


    @Test
    public void checkCancelSignUp() {
        solo.clickOnButton("Sign up");
        solo.waitForActivity("SignUpActivity", 3000);
        solo.clickOnButton("Cancel");
        solo.waitForActivity("MainActivity", 3000);
    }


    @Test
    public void checkLoginOwner() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Did not ask for id and pwd", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input), "TestOwner1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input), "111");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Did not login", OwnerHomepage.class);
    }


    @Test
    public void checkLoginBorrower() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Did not ask for id and pwd", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input), "TestBorrower1");
        solo.enterText((EditText) solo.getView(R.id.pwd_input), "111");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Did not login", BorrowerHomepage.class);
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
