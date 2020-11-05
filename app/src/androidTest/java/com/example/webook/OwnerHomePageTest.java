package com.example.webook;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.BatchUpdateException;

@RunWith(AndroidJUnit4.class)
public class OwnerHomePageTest {
    private Solo solo;

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
        solo.enterText((EditText) solo.getView(R.id.username_input), "owner2");
        solo.enterText((EditText) solo.getView(R.id.pwd_input), "111");
        solo.clickOnButton("Log in");
    }

    @Test
    public void checkOnItemClick(){
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        //This part need to be change
        solo.clickOnMenuItem("Harry Potter");
        solo.assertCurrentActivity("Wrong Activity", OwnerBookProfile.class);
        assertTrue(solo.searchText("Harry Potter"));
    }

    @Test
    public void checkRequestsButton(){
        checkOnItemClick();
        Button button =  (Button) solo.getCurrentActivity().findViewById(R.id.owner_requests_list_button);
        solo.clickOnView(button);
        solo.assertCurrentActivity("Wrong Activity", SameBookRequestList.class);
        assertTrue(solo.searchText("Harry Potter"));
    }

    @Test
    public void checkFragment(){
        checkRequestsButton();
        solo.clickOnMenuItem("Harry Potter");
        assertTrue(solo.searchText("Accept"));
    }

    @Test
    public void checkAddBook(){
        TextView me = (TextView) solo.getView(R.id.owner_me_tab);
        solo.clickOnView(me);
        solo.assertCurrentActivity("Wrong Activity", OwnerProfileActivity.class);
        solo.clickOnButton("ADD BOOK");
        solo.assertCurrentActivity("Wrong Activity", AddBookActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editTextBookTitle), "ui testing book");
        solo.enterText((EditText) solo.getView(R.id.editTextBookAuthor), "ui testing author");
        solo.enterText((EditText) solo.getView(R.id.editTextISBN), "ui testing isbn");
        solo.enterText((EditText) solo.getView(R.id.editTextDescription), "ui testing description");
        solo.clickOnView((Button) solo.getView(R.id.confirm_add_book_button));
        solo.waitForActivity("OwnerProfileActivity",5000);
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", OwnerHomepage.class);
        solo.waitForText("ui testing book", 1, 5000);
        solo.waitForText("ui testing author", 1, 5000);
    }

    @Test
    public void checkEditOwnerProfile(){
        TextView me = (TextView) solo.getView(R.id.owner_me_tab);
        solo.clickOnView(me);
        solo.assertCurrentActivity("Wrong Activity", OwnerProfileActivity.class);
        solo.clickOnView((Button) solo.getView(R.id.owner_editProfile));
        solo.assertCurrentActivity("Wrong Activity", EditUserProfileActivity.class);
        EditText email = (EditText) solo.getView(R.id.editUserEmail);
        EditText phone =  (EditText) solo.getView(R.id.editUserPhone);
        EditText description =  (EditText) solo.getView(R.id.editUserDescription);
        email.setText("");
        phone.setText("");
        description.setText("");
        solo.enterText(email, "ui testing email");
        solo.enterText(phone, "ui testing phone");
        solo.enterText(description, "ui testing user description");
        solo.clickOnView((Button) solo.getView(R.id.editUserConfirm));
        solo.assertCurrentActivity("Wrong Activity", OwnerProfileActivity.class);
        solo.waitForActivity("OwnerProfileActivity",5000);
        solo.waitForText("ui testing email", 1, 5000);
        solo.waitForText("ui testing phone", 1, 5000);
        solo.waitForText("ui testing user description", 1, 5000);
    }
}
