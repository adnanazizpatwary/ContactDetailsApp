package com.example.contactdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseHandler {
    String name,email,phone;
    int id;
    private DatabaseHelper databaseHelper;
    private ContactPerson person;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHandler(Context context) {
        databaseHelper = new DatabaseHelper(context);

    }
    private void open(){
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }
    private void close(){
        sqLiteDatabase.close();
    }
    public boolean Addperson(ContactPerson person){
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.col_name,person.getName());
        values.put(DatabaseHelper.col_phone,person.getPhone());
        values.put(DatabaseHelper.col_email,person.getEmail());
        long val= sqLiteDatabase.insert(DatabaseHelper.TableName,null,values);
        this.close();
        if(val>0){
            return true;
        }else{
            return false;
        }

    }
    public ArrayList<ContactPerson> getAllpersons(){
        this.open();
        ArrayList<ContactPerson> persons=new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TableName,null,null,
                null,null,null,null);
        cursor.moveToFirst();
        if(cursor != null&&cursor.getCount()>0){
            for (int i = 0; i < cursor.getCount(); i++) {
                id= cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.col_Id));
                name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.col_name));
                phone= cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.col_phone));
                email= cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.col_email));
                person = new ContactPerson(id,name,phone,email);
                persons.add(person);
                cursor.moveToNext();

            }

        }
        cursor.close();
        this.close();
        return persons;
    }
     public boolean Update(ContactPerson person){
        this.open();
        ContentValues  values = new ContentValues();
        values.put(DatabaseHelper.col_name,person.getName());
        values.put(DatabaseHelper.col_phone,person.getPhone());
        values.put(DatabaseHelper.col_email,person.getEmail());
        int val = sqLiteDatabase.update(DatabaseHelper.TableName,values,DatabaseHelper.col_Id+" =? ",new String[]{Integer.toString(person.getId())});
        this.close();
        if(val>0){
            return true;
        }else {
            return false;
        }


     }
     public boolean DeleteP(int row_id){
        this.open();
        int val =sqLiteDatabase.delete(DatabaseHelper.TableName,DatabaseHelper.col_Id+" =? ",new String[]{Integer.toString(row_id)});
        this.close();
        if(val>0){
            return true;

        }
        else {
            return false;
        }

     }
     public ArrayList<ContactPerson> searchpersons(String Keyword){
        ArrayList<ContactPerson> persons = new ArrayList<>();
        this.open();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TableName,null,
                DatabaseHelper.col_name+ " LIKE ? OR "+
                DatabaseHelper.col_phone+" LIKE ? OR "+
                DatabaseHelper.col_email+" LIKE ?",new String[]{
                        "%"+Keyword+ "%","%"+Keyword+ "%","%"+Keyword+"%"
                },null,null,null);
        cursor.moveToFirst();
        if(cursor !=null && cursor.getCount()>0){
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.col_Id));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.col_name));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.col_phone));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.col_email));
               person = new ContactPerson(id,name,phone,email);
               persons.add(person);
               cursor.moveToNext();

            }
        }cursor.close();
        this.close();
        return persons;

     }

}
