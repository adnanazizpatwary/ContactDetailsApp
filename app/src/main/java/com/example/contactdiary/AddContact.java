package com.example.contactdiary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddContact extends AppCompatActivity {
    private EditText nameEt,phoneEt,emailEt;
    private ContactPerson contactPerson;
    private DatabaseHandler databaseHandler;
    private DatabaseReference dbRef;
    private Button addbtnTV;
    String name,phone,email;
    int id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.addcontact);
        nameEt =(EditText) findViewById(R.id.Cname);
        phoneEt =(EditText) findViewById(R.id.Cnumber);
        emailEt =(EditText) findViewById(R.id.Cemail);
        addbtnTV=(Button)findViewById(R.id.addbtn) ;
        databaseHandler = new DatabaseHandler(this);
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        id = getIntent().getIntExtra("id",0);
        if(id>0){
            addbtnTV.setText("Update");
            nameEt.setText(name);
            phoneEt.setText(phone);
            emailEt.setText(email);

        }
        dbRef = FirebaseDatabase.getInstance().getReference("contacts");



    }

    public void AddCon(View view) {
        String name = nameEt.getText().toString();
        String phone = phoneEt.getText().toString();
        String email = emailEt.getText().toString();
        if(name.isEmpty()){
            nameEt.setError("This field must not be empty");
        }
        else if(phone.isEmpty()){
            phoneEt.setError("This field must not be empty");
        }
        else if (email.isEmpty()) {
            emailEt.setError("This field must not be empty");

        }else{
            if(id>0){
                ContactPerson person = new ContactPerson(id,name,phone,email);
                boolean status = databaseHandler.Update(person);
                if(status){
                    dbRef.child(String.valueOf(id)).setValue(person);
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddContact.this,MainActivity.class));
                }else{
                    Toast.makeText(this, "Not Updated Try Again", Toast.LENGTH_SHORT).show();
                }
            }else{

            contactPerson = new ContactPerson(name,phone,email);
            boolean status = databaseHandler.Addperson(contactPerson);
            if(status){
                DatabaseReference newRef = dbRef.push();
                newRef.setValue(contactPerson);
                Toast.makeText(this, "Successfully Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddContact.this,MainActivity.class));
            }
            else{
                Toast.makeText(this, "Unsuccessful try again", Toast.LENGTH_SHORT).show();
            }
            

        }}


    }
}