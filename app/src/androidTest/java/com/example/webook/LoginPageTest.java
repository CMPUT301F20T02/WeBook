package com.example.webook;

import android.app.Instrumentation;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginPageTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void checkOwnerLogin(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Did not ask for id and pwd", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input), "owner2");
        solo.enterText((EditText) solo.getView(R.id.pwd_input), "111");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Did not login", OwnerHomepage.class);
    }

    @Test
    public void checkBorrowerLogin(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Did not ask for id and pwd", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_input), "test2");
        solo.enterText((EditText) solo.getView(R.id.pwd_input), "222");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Did not login", BorrowerHomepage.class);
    }
}
