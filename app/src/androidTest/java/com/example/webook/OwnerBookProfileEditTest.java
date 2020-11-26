package com.example.webook;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class OwnerBookProfileEditTest {
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
    public void checkOwnerBookProfileEditTest() {
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        assertTrue(solo.waitForText("TestBook3", 1, 5000, true, true));
        solo.clickOnMenuItem("TestBook3");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        solo.clickOnButton("Edit");
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfileBookEdit.class);
        solo.sleep(3000);

        assertTrue(solo.waitForText("TestBook3", 1, 5000, true, true));
        assertTrue(solo.waitForText("TestBook3 Author", 1, 5000, true, true));
        assertTrue(solo.waitForText("This is TestBook3", 1, 5000, true, true));
        assertTrue(solo.waitForText("3000000000000", 1, 5000, true, true));

        solo.clearEditText((EditText) solo.getView(R.id.editTextBookTitle));
        solo.clearEditText((EditText) solo.getView(R.id.editTextBookAuthor));
        solo.clearEditText((EditText) solo.getView(R.id.editTextDescription));
        solo.enterText((EditText) solo.getView(R.id.editTextBookTitle),"EditedTestBook3");
        solo.enterText((EditText) solo.getView(R.id.editTextBookAuthor),"EditedTestBook3 Author");
        solo.enterText((EditText) solo.getView(R.id.editTextDescription),"This is EditedTestBook3");

        solo.clickOnButton("CONFIRM");

        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);

        assertTrue(solo.waitForText("EditedTestBook3", 1, 5000, true, true));
        assertTrue(solo.waitForText("EditedTestBook3 Author", 1, 5000, true, true));
        assertTrue(solo.waitForText("This is EditedTestBook3", 1, 5000, true, true));

        solo.goBack();

        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);

        assertTrue(solo.waitForText("EditedTestBook3", 1, 5000, true, true));
        assertTrue(solo.waitForText("EditedTestBook3 Author", 1, 5000, true, true));

        assertTrue(solo.waitForText("TestBook4", 1, 5000, true, true));
        solo.clickOnMenuItem("TestBook4");
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        solo.clickOnButton("Edit");
        //solo.sleep(500);
        assertTrue(solo.waitForText("This book currently unavailable to change!", 1, 500, true, true));
    }

}
