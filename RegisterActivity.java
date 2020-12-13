package com.Bonte.MyJournal;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText userNameEditText;
    private Button createAcctButton;
    private TextView login;
    //private DatabaseHelper db;

    //Connection to firestore
    private FirebaseFirestore dbFire = FirebaseFirestore.getInstance();

    private CollectionReference collectionReference = dbFire.collection("Users");

    //Setting authentication variables for Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        //db = new DatabaseHelper(this);
        emailEditText = findViewById(R.id.etName);
        passwordEditText = findViewById(R.id.etpassword);
        userNameEditText = findViewById(R.id.usernameEditText);
        createAcctButton = findViewById(R.id.bregister);
        login = findViewById(R.id.login);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) {
                    //user is already logged in
                }
                else {
                    //no user yet...
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(RegisterActivity.this, Login.class);
                startActivity(LoginIntent);
            }
        });
        createAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(emailEditText.getText().toString())
                        && !TextUtils.isEmpty(passwordEditText.getText().toString())
                        && !TextUtils.isEmpty(userNameEditText.getText().toString())) {

                    String username = userNameEditText.getText().toString().trim();
                    String email = emailEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();

                    createUserEmailAccount(email, username, password);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Empty Fields Not Allowed", Toast.LENGTH_SHORT).show();

                }

//                if(pwd.equals(cnf_pwd)) {
//                    long val = db.addUser(user, pwd);
//                    if(val > 0) {
//                        Toast.makeText(RegisterActivity.this,"You have registered",Toast.LENGTH_SHORT).show();
//                        Intent moveToLogin = new Intent(RegisterActivity.this,Login.class);
//                        startActivity(moveToLogin);
//                    }
//                    else {
//                        Toast.makeText(RegisterActivity.this,"Registration error",Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//                else {
//                    Toast.makeText(RegisterActivity.this,"Password does not match",Toast.LENGTH_SHORT).show();
//
//                }
            }
        });
    }
    private void createUserEmailAccount(String email, final String username, String password) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {

            //TODO may add progress bar here

            //This will create the user with their email and password
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        //we take user to main activity once they are successfully created
                        Toast.makeText(RegisterActivity.this, "Success1", Toast.LENGTH_SHORT).show();
                        currentUser = firebaseAuth.getCurrentUser();
                        assert currentUser != null;
                        final String currentUserId = currentUser.getUid();

                        //Create a user Map so we can create a user in the User collection
                        Map<String, String> userObj = new HashMap<>();
                        userObj.put("userId", currentUserId);
                        userObj.put("username", username);

                        //save the user to firestore DB
                        collectionReference.add(userObj)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                documentReference.get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(Objects.requireNonNull(task.getResult()).exists()){

                                            String name = task.getResult().getString("username");

                                            //TODO may add progress bar here

                                            JournalApi journalApi = JournalApi.getInstance();
                                            journalApi.setUserId(currentUserId);
                                            journalApi.setUsername(name);

                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            intent.putExtra("username", name);
                                            intent.putExtra("userId", currentUserId);
                                            startActivity(intent);
                                        } else {

                                        }
                                    }
                                });
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, "error1", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else {
                        //something went wrong
                        Toast.makeText(RegisterActivity.this, "Password does not meet criteria", Toast.LENGTH_SHORT).show();
                    }

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, "error3", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {

        }
    }

    // On Start we get the current user to see if they are already logged in
    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}