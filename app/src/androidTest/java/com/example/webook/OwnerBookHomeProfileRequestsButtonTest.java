package com.example.webook;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class OwnerBookHomeProfileRequestsButtonTest {
    private Solo solo;
    private DataBaseTestManager dataBaseTestManager;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        dataBaseTestManager = new DataBaseTestManager();
        dataBaseTestManager.createTestData();
        solo.sleep(5000);
    }

    @After
    public void after(){
        dataBaseTestManager.deleteTestData();
        solo.sleep(5000);
    }

    @Test
    public void checkRequestsButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

    }
}