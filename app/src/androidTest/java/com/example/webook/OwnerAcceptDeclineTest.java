package com.example.webook;

import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class OwnerAcceptDeclineTest {

    private Solo solo;
    private BookRequest testRequest;
    private Book testBook;
    private ArrayList<String> requesters = new ArrayList<>();

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
        solo.enterText((EditText) solo.getView(R.id.username_input), "OwnerBookTest");
        solo.enterText((EditText) solo.getView(R.id.pwd_input), "8888");
        solo.clickOnButton("Log in");
        testBook = new Book("Part3TestBook", "5510289325214", "Peter", "available", "OwnerBookTest",
        "https://firebasestorage.googleapis.com/v0/b/webook-6e65f.appspot.com/o/images%2Fandroidx.appcompat.widget.AppCompatEditText%7B8bf8d05%20VFED..CL.%20........%20534%2C452-1036%2C565%20%237f0700ab%20app%3Aid%2FeditTextISBN%20aid%3D1073741826%7D?alt=media&token=cb43e8f9-1798-4d71-a3f6-077f226da834",
        "This is a test book");
        requesters.add("BorrowerBookTest");
        testRequest = new BookRequest(testBook, "OwnerBookTest", requesters, null, null);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("requests");
        collectionReference
                .document(testRequest.getBook().getISBN())
                .set(testRequest);
        collectionReference
                .document(testRequest.getBook().getISBN())
                .update("requester", FieldValue.arrayUnion("BorrowerBookTest2"));

    }

    @Test
    public void RequestedBookTest() {
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.clickOnText("Requested");
        assertTrue(solo.searchText(testBook.getTitle()));
    }

    @Test
    public void RequestedBookProfileTest(){
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.clickOnText("Requested");
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnMenuItem(testBook.getTitle());
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        assertTrue(solo.searchText(testBook.getTitle()));
    }

    @Test
    public void SameBookRequestsTest(){
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.clickOnText("Requested");
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnMenuItem(testBook.getTitle());
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnButton("REQUESTS LIST");
        solo.assertCurrentActivity("Wrong activity", SameBookRequestList.class);
        assertTrue(solo.searchText("BorrowerBookTest"));
        assertTrue(solo.searchText("BorrowerBookTest2"));
    }

    @Test
    public void acceptTest(){
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.clickOnText("Requested");
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnMenuItem(testBook.getTitle());
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnButton("REQUESTS LIST");
        solo.assertCurrentActivity("Wrong activity", SameBookRequestList.class);
        assertTrue(solo.searchText("BorrowerBookTest"));
        assertTrue(solo.searchText("BorrowerBookTest2"));
        solo.clickOnMenuItem("BorrowerBookTest2");
        solo.clickOnButton("Accept");
        solo.assertCurrentActivity("Wrong Activity", OwnerBookProfile.class);
    }

    @Test
    public void declineTest(){
        solo.assertCurrentActivity("Wrong activity", OwnerHomepage.class);
        solo.clickOnText("Requested");
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnMenuItem(testBook.getTitle());
        solo.assertCurrentActivity("Wrong activity", OwnerBookProfile.class);
        assertTrue(solo.searchText(testBook.getTitle()));
        solo.clickOnButton("REQUESTS LIST");
        solo.assertCurrentActivity("Wrong activity", SameBookRequestList.class);
        assertTrue(solo.searchText("BorrowerBookTest"));
        assertTrue(solo.searchText("BorrowerBookTest2"));
        solo.clickOnMenuItem("BorrowerBookTest2");
        solo.clickOnButton("Decline");
        solo.assertCurrentActivity("Wrong activity", SameBookRequestList.class);
    }

    @After
    public void reset() throws Exception{
        Thread.sleep(1000);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference documentReference = db
                .collection("requests")
                .document("5510289325214");
        documentReference
                .delete();
        final CollectionReference collectionReference = db.collection("books");
        collectionReference
                .document("5510289325214")
                .update("status", "requested");
    }
}
