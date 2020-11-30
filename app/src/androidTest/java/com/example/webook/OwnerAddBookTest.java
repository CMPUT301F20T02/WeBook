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

@RunWith(AndroidJUnit4.class)
public class OwnerAddBookTest {
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
    public void checkAddBook(){
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.sleep(2000);
        solo.clickOnText("ME");
        solo.assertCurrentActivity("Wrong Activity", OwnerProfileActivity.class);
        solo.clickOnButton("ADD BOOK");
        solo.assertCurrentActivity("Wrong Activity", AddBookActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editTextBookTitle), "testing book");
        solo.enterText((EditText) solo.getView(R.id.editTextBookAuthor), "testing author");
        solo.enterText((EditText) solo.getView(R.id.editTextISBN), "1300000000000");
        solo.enterText((EditText) solo.getView(R.id.editTextDescription), "testing description");
        solo.clickOnButton("CONFIRM");
        solo.waitForActivity("OwnerProfileActivity",5000);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.waitForText("testing book", 1, 5000);
        solo.waitForText("testing author", 1, 5000);
        solo.sleep(2000);
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
