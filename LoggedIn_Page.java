package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoggedIn_Page extends AppCompatActivity
{
Button b1,b2,b3;
EditText e1,e2,e3,e4;
FirebaseDatabase db;
DatabaseReference dbref;
String password="",mobile="",id;
@Override
    protected void onCreate(Bundle savedInstanceState)
{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_page);

         b1=findViewById(R.id.search);
         b2=findViewById(R.id.update);
         b3=findViewById(R.id.signout);
         e1=findViewById(R.id.facultyname);

         e3=findViewById(R.id.updatepassword);
         e4=findViewById(R.id.UpdateMobileNumber);

         Intent i=getIntent();
         id=i.getStringExtra("Id");

         b1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String s="";
                 String fname=e1.getText().toString();

                 if(fname.length()!=0)
                 {
                     Intent i=new Intent(LoggedIn_Page.this,Search_Section.class);
                     i.putExtra("fname",fname);
                      i.putExtra("id",id);
                     startActivity(i);


                 }else {
                     s=s+" Nothing is given to Search";
                 }
                 Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
             }
         });

         //Update Section
    db=FirebaseDatabase.getInstance();
    dbref=db.getReference();

         b2.setOnClickListener(new View.OnClickListener()
         {

             @Override
             public void onClick(View view)
             {

                 String s="";

                  password=e3.getText().toString();
                 mobile=e4.getText().toString();
                 if(password.length()!=0 || mobile.length()!=0)
                 {
                     if (password.length() != 0)
                     {
                         if (password.length() <= 6)
                         {

                           s=s+"Password length should be greater than 6\n";
                             e3.setText("");

                         } else {

                             dbref.child("Student_Accounts").child(id).child("Password").setValue(password);
                             s = s + " Password Updated\n";
                             e3.setText("");
                            password="";
                         }
                     }
                     if (mobile.length() != 0)
                     {
                         if (mobile.length() == 10)
                         {

                             dbref.child("Student_Accounts").child(id).child("Mobile No").setValue(mobile);
                             s = s + " Mobile.no Update Successful\n";

                             e4.setText("");
                             mobile="";
                             password="";
                         }
                         else {
                            s=s+"Invalid Mobile Number\n";
                             e4.setText("");
                             mobile="";
                             password="";
                         }
                     }

                 }
                 else
                 {

                     s = s + " Nothing is given to Update ";
                     password = "";
                     mobile = "";
                 }
                 Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                 password = "";
                 mobile = "";


             }
         });


         //SignOut Section


         b3.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Intent i=new Intent(LoggedIn_Page.this,SignIn_Page.class);
                 startActivity(i);
                 password="";
                 mobile="";
             }
         });



}
}