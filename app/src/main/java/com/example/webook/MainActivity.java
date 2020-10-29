package com.example.webook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Sample";
    private TextView username;
    private TextView pwd;
    private Button login;
    private Button signUp;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username_input);
        pwd = findViewById(R.id.pwd_input);
        signUp = findViewById(R.id.signup_button);
        login = findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_text = username.getText().toString();
                String pwd_text = pwd.getText().toString();
                if (username_text.length() != 0 & pwd_text.length() != 0){
                    int a = username_text.length();
                    String b = Integer.toString(a);
                    //username.setText(b);

                    authenticate(username_text, pwd_text);
                }

            }
        });
        Owner user = new Owner("owner1", "test1@test1.com", "111", "111");
        //Borrower user = new Borrower("test1", "test1@test1.com", "111", "145");
        db.collection("users").document(user.getUsername())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


        //Map<String, Object> user = new HashMap<>();
        //user.put("first", owner);
        /*db.collection("users").document("test")
                .set(owner)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });*/

        /*
        Book book = new Book();
        Map<String, Object> user = new HashMap<>();
        user.put("first", book);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

*/
/*
        db.collection('users').doc('id').get()
                .then((docSnapshot) => {
        if (docSnapshot.exists) {
            db.collection('users').doc('id')
                    .onSnapshot((doc) => {
                    // do stuff with the data
            });
        }
  });

 */


    }


    public void authenticate(String username, final String pwd){
        DocumentReference userRef = db.collection("users").document(username);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    int duration = Toast.LENGTH_SHORT;
                    if (document.exists()) {
                        if (document.getString("pwd").equals(pwd)){
                            Intent intent;
                            if (document.getString("userType").equals("owner")){
                                intent = new Intent(MainActivity.this, OwnerHomepage.class);
                                startActivity(intent);
                            }else if (document.getString("userType").equals("borrower")) {
                                intent = new Intent(MainActivity.this, BorrowerHomepage.class);
                                startActivity(intent);
                            }


                        }else{
                            Toast toast = Toast.makeText(MainActivity.this, "Incorrect credentials!", duration);
                            toast.show();
                        }
                    } else {

                        Toast toast = Toast.makeText(MainActivity.this, "Incorrect credentials!", duration);
                        toast.show();
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }
}