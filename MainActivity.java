package com.Bonte.MyJournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    Toolbar toolbar;
    TextView noItemText;
    SimpleDatabase simpleDatabase;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore dbFire = FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    private List<JournalEntry> journalEntryList;
    private RecyclerView recyclerView;
    private Adapter journalRecyclerAdapter;

    private CollectionReference collectionReference = dbFire.collection("Journal");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        noItemText = findViewById(R.id.noItemText);
        journalEntryList = new ArrayList<>();

        recyclerView = findViewById(R.id.allNotesList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);


        // Check the database to see if there are entry records. If not, display empty text.
//        if(allJournalEntries.isEmpty()){
//            noItemText.setVisibility(View.VISIBLE);
//        }else {
//            noItemText.setVisibility(View.GONE);
//            displayList(allJournalEntries);
//        }

    }

//    private void displayList(List<JournalEntry> allJournalEntries) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new Adapter(this, allJournalEntries);
//        recyclerView.setAdapter(adapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    // adds an intent to switch to the AddJournalEntry class.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                //take user to add journal activity
                if (user != null && firebaseAuth != null) {
                    Toast.makeText(this, "Add New Entry", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, AddJournalEntry.class));
                    //finish();
                }
                break;
            case R.id.action_logout:
                //sign user out
                if (user != null && firebaseAuth != null) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(MainActivity.this, Login.class));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        collectionReference.whereEqualTo("userID", JournalApi.getInstance()
                .getUserId())
                .get()
        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for(QueryDocumentSnapshot journals : queryDocumentSnapshots) {
                        JournalEntry journal = journals.toObject(JournalEntry.class);
                        journalEntryList.add(journal);
                    }

                    journalRecyclerAdapter = new Adapter(MainActivity.this, journalEntryList);
                    recyclerView.setAdapter(journalRecyclerAdapter);
                    journalRecyclerAdapter.notifyDataSetChanged();
                }
                else {

                    //noItemText.setVisibility(View.VISIBLE);

                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    // show the database list of weights
    @Override
    protected void onResume() {
        super.onResume();

        collectionReference.whereEqualTo("userID", JournalApi.getInstance()
                .getUserId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for(QueryDocumentSnapshot journals : queryDocumentSnapshots) {
                                JournalEntry journal = journals.toObject(JournalEntry.class);
                                journalEntryList.add(journal);
                            }

                            journalRecyclerAdapter = new Adapter(MainActivity.this, journalEntryList);
                            recyclerView.setAdapter(journalRecyclerAdapter);
                            journalRecyclerAdapter.notifyDataSetChanged();
                        }
                        else {

                            //noItemText.setVisibility(View.VISIBLE);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

}
