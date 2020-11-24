package com.example.webook;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OwnerBookProfileDeleteTest {
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
        solo.clickOnMenuItem("TestBook12");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        solo.clickOnButton("Delete");
        assertTrue(solo.waitForText("Are you sure you want to delete this book?", 1, 5000, true, true));
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.sleep(1000);
        assertFalse(solo.waitForText("TestBook12", 1, 3000, true, true));

        solo.clickOnText("Borrowed");
        solo.clickOnMenuItem("TestBook9");
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        solo.clickOnButton("Delete");
        assertTrue(solo.waitForText("Are you sure you want to delete this book?", 1, 5000, true, true));
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.sleep(1000);
        assertFalse(solo.waitForText("TestBook9", 1, 3000, true, true));
    }
}
