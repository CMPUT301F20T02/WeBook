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

public class OwnerBookProfileRequestListAssociationTest {
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
    public void ownerRequestListFunctionalTest2() {
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.sleep(1000);
        solo.clickOnText("Accepted");
        solo.sleep(1000);
        solo.clickOnMenuItem("TestBook12 Author");
        solo.assertCurrentActivity("Wrong Activity", OwnerBookProfile.class);
        solo.sleep(1000);
        solo.clickOnButton("REQUESTS LIST");
        assertTrue(solo.waitForText("This book's request is already accepted/borrowed, no waiting requests", 1, 2000, true, true));
        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.sleep(1000);
        solo.clickOnText("Borrowed");
        solo.sleep(1000);
        solo.clickOnMenuItem("TestBook8");
        solo.assertCurrentActivity("Wrong Activity", OwnerBookProfile.class);
        solo.sleep(1000);
        solo.clickOnButton("REQUESTS LIST");
        assertTrue(solo.waitForText("This book's request is already accepted/borrowed, no waiting requests", 1, 1000, true, true));
        solo.goBack();


        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.sleep(1000);
        solo.clickOnText("Available");
        solo.sleep(1000);
        solo.clickOnMenuItem("TestBook3");
        solo.assertCurrentActivity("Wrong Activity", OwnerBookProfile.class);
        solo.sleep(1000);
        solo.clickOnButton("REQUESTS LIST");
        assertTrue(solo.waitForText("There is no request for this book", 1, 1000, true, true));
        solo.sleep(1000);
    }
}
