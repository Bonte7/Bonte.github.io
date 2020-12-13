package com.Bonte.MyJournal;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView register;
    //private DatabaseHelper db;

    //Connection to firestore
    private FirebaseFirestore dbFire = FirebaseFirestore.getInstance();

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    private CollectionReference collectionReference = dbFire.collection("Users");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //db = new DatabaseHelper(this);
        username = findViewById(R.id.etName);
        password = findViewById(R.id.etpassword);
        login = findViewById(R.id.blogin);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Login.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginEmailPasswordUser(username.getText().toString().trim(),
                        password.getText().toString().trim());

//                String user = username.getText().toString().trim();
//                String pwd = password.getText().toString().trim();
//                Boolean res = db.checkUser(user, pwd);
//                if (res == true)
//                {
//                    //TODO: Create an intent to switch to the main app page if login is correct
//                    Toast.makeText(Login.this,"Success!",Toast.LENGTH_SHORT).show();
//                    Intent moveToMain = new Intent(Login.this,MainActivity.class);
//                    startActivity(moveToMain);
//                }
//                else {
//                    // If login is incorrect
//                    Toast.makeText(Login.this,"Login Error",Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }

    private void loginEmailPasswordUser(String email, String pwd) {

        if (!TextUtils.isEmpty(email) && !TextUtils.isDigitsOnly(pwd)) {

            firebaseAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;
                            final String currentUserId = user.getUid();

                            collectionReference
                                    .whereEqualTo("userId", currentUserId)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
                                            }
                                            if (!value.isEmpty()){

                                                for (QueryDocumentSnapshot snapshot : value){
                                                    JournalApi journalApi = JournalApi.getInstance();
                                                    journalApi.setUsername(snapshot.getString("username"));
                                                    journalApi.setUserId(snapshot.getString("userId"));
                                                }

                                                //Go to Main
                                                Toast.makeText(Login.this,"Success!",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Login.this, MainActivity.class));


                                            }
                                        }
                                    });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        } else {
            Toast.makeText(Login.this, "Please enter email and password", Toast.LENGTH_LONG).show();

        }

    }
}