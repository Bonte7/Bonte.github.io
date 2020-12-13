package com.Bonte.MyJournal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddJournalEntry extends AppCompatActivity implements View.OnClickListener {
    public static final int GALLERY_CODE = 1;
    Toolbar toolbar;
    private EditText entryTitle,entryDetails;
    Calendar c;
    String todaysDate;
    String currentTime;
    private ProgressBar progressBar;
    private ImageView addPhotoButton;
    private ImageView imageView;

    private String currentUserId;
    private String currentUserName;

    //Connection to firebase
    private FirebaseFirestore dbFire = FirebaseFirestore.getInstance();

    //Setting the firestore data path to Journal/Entries
    private DocumentReference journalRef = dbFire.collection("Journal").document("Entries");

    //Reference to firebase user authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    //Create reference to firebase storage for images and files
    private StorageReference storageReference;
    private CollectionReference collectionReference = dbFire.collection("Journal");

    //Declaring key variables for use in firestore
    public static final String KEY_TITLE = "title";
    public static final String KEY_DETAILS = "details";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_entry);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Entry");

        entryDetails = findViewById(R.id.entryDetails);
        entryTitle = findViewById(R.id.entryTitle);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        imageView = findViewById(R.id.imageView2);
        progressBar = findViewById(R.id.progressBar);
        addPhotoButton = findViewById(R.id.entryCameraButton);
        addPhotoButton.setOnClickListener(this);

        progressBar.setVisibility(View.INVISIBLE);

        if (JournalApi.getInstance() != null){
            currentUserId = JournalApi.getInstance().getUserId();
            currentUserName = JournalApi.getInstance().getUsername();

        }

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null){

                } else {

                }
            }
        };


        entryTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // set current date and time
        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
        Log.d("DATE", "Date: "+todaysDate);
        currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
        Log.d("TIME", "Time: "+currentTime);

    }

    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save){
            if(entryTitle.getText().length() != 0){

                final String title = entryTitle.getText().toString().trim();
                final String details = entryDetails.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);

                final StorageReference filepath = storageReference
                        .child("journal_images")
                        .child("my_image_" + Timestamp.now().getSeconds()); // appending a timestamp so each image is unique

                filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();

                                //TODO: create a Journal Object
                                JournalEntry journal = new JournalEntry();
                                journal.setTitle(title);
                                journal.setContent(details);
                                journal.setImageUrl(imageUrl);
                                journal.setTimeAdded(new Timestamp(new Date()));
                                journal.setUserName(currentUserName);
                                journal.setUserId(currentUserId);

                                //TODO: invoke our collection reference
                                collectionReference.add(journal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        //startActivity(new Intent(AddJournalEntry.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                                //TODO: and save a Journal instance
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

//                //Hash map for data layout in firestore
//                final Map<String, Object> entryData = new HashMap<>();
//                entryData.put(KEY_TITLE, title);
//                entryData.put(KEY_DETAILS, details);
//                entryData.put(KEY_DATE, todaysDate);
//                entryData.put(KEY_TIME, currentTime);

//                //sending the journal entry to firestore
//                journalRef.set(entryData).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                        Toast.makeText(AddJournalEntry.this,"Success!",Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                                Toast.makeText(AddJournalEntry.this,"error",Toast.LENGTH_SHORT).show();
//
//                            }
//                        });

//                JournalEntry journalEntry = new JournalEntry(entryTitle.getText().toString(),entryDetails.getText().toString(),todaysDate,currentTime);
//                SimpleDatabase sDB = new SimpleDatabase(this);
//                long id = sDB.addEntry(journalEntry);
//                JournalEntry check = sDB.getEntry(id);
//                Log.d("inserted", "Entry: "+ id + " -> Title:" + check.getTitle()+" Date: "+ check.getDate());
                onBackPressed();

                Toast.makeText(this, "Journal Log Saved.", Toast.LENGTH_SHORT).show();
            }else {
                entryTitle.setError("Title field can not be blank.");
                progressBar.setVisibility(View.INVISIBLE);
            }

        }else if(item.getItemId() == R.id.delete){
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.entryCameraButton:
                //get image from device gallery
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK){
            if (data != null) {
                imageUri = data.getData();
                //Shows selected image
                imageView.setImageURI(imageUri);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuth != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
