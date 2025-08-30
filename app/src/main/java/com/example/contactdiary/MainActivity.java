package com.example.contactdiary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ContactPerson>persons;
    private PersonAdapter personAdapter;
    private DatabaseHandler databaseHandler;
    private SearchView searchView;
    private DatabaseReference dbRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHandler = new DatabaseHandler(this);
        persons = databaseHandler.getAllpersons();
        personAdapter = new PersonAdapter(persons, position -> {
            ContactPerson clicked = persons.get(position);
            Toast.makeText(this, "Clicked: " + clicked.getName(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, personDetails.class).putExtra("name",persons.get(position).getName())
                    .putExtra("phone",persons.get(position).getPhone())
                    .putExtra("email",persons.get(position).getEmail())
                    .putExtra("id",persons.get(position).getId())

            );
        });
        recyclerView.setAdapter(personAdapter);
        searchView =(SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                persons = databaseHandler.searchpersons(query);
                personAdapter.updateArrayList(persons);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    persons = databaseHandler.getAllpersons();
                }else{
                    persons = databaseHandler.searchpersons(newText);
                }
                personAdapter.updateArrayList(persons);
                return true;
            }
        });
        dbRef = FirebaseDatabase.getInstance().getReference("contacts");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                persons.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    int id = Integer.parseInt(data.getKey());
                    String name = data.child("name").getValue(String.class);
                    String phone = data.child("phone").getValue(String.class);
                    String email = data.child("email").getValue(String.class);
                    persons.add(new ContactPerson(id,name,phone,email));
                }
                personAdapter.updateArrayList(persons);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Firebase Load Error", Toast.LENGTH_SHORT).show();


            }
        });



    }

    public void AddContact(View view) {
        startActivity(new Intent(MainActivity.this, AddContact.class));
    }
}