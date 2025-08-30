package com.example.contactdiary;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class personDetails extends AppCompatActivity {
    private DatabaseReference dbRef;
    TextView nameET,phoneET,emailET;
    String name,phone,email;
    int id;
    private DatabaseHandler databaseHandler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_person_details);
        nameET= (TextView) findViewById(R.id.name);
        phoneET =(TextView) findViewById(R.id.phone);
        emailET =(TextView) findViewById(R.id.email);
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        id= getIntent().getIntExtra("id",0);
        nameET.setText(name);
        phoneET.setText(phone);
        emailET.setText(email);
        databaseHandler = new DatabaseHandler(this);
        dbRef = FirebaseDatabase.getInstance().getReference("contacts");






    }
    public void Update(View view) {
        startActivity(new Intent(personDetails.this,AddContact.class)
                .putExtra("id",id)
                .putExtra("name",name)
                .putExtra("phone",phone)
                .putExtra("email",email)
        );
    }

    public void DeleteP(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure to delete? ");
        alert.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean status = databaseHandler.DeleteP(id);
                if(status){
                    dbRef.child(String.valueOf(id)).removeValue();
                    Toast.makeText(personDetails.this, "Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(personDetails.this,MainActivity.class));
                }
                else{
                    Toast.makeText(personDetails.this, "Not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alert.setNegativeButton("Cancel",null);
        alert.show();
    }


}