package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Account_Creation extends AppCompatActivity
{
Spinner s1;
String s=" ";
EditText t1,t2,t3,t4,t5,t6;
    String s2="";
    String name="",mail="",id="",password="",mobile="",cabino="";
    boolean f = false;


Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
       t1=findViewById(R.id.name);
       t2=findViewById(R.id.mailId);
               t3=findViewById(R.id.Id);
                       t4=findViewById(R.id.password1);
               t5=findViewById(R.id.cabinno);
t5.setEnabled(false);
                b1=findViewById(R.id.Submit);
               t6=findViewById(R.id.mobile);
        s1=findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> arr1=ArrayAdapter.createFromResource(this,R.array.signinas, android.R.layout.simple_list_item_1);
        s1.setAdapter(arr1);
  s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
s=adapterView.getItemAtPosition(i).toString();
          if(s.equals("Faculty"))
          {
              t5.setEnabled(true);
          }else {
              t5.setEnabled(false);
          }
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
  });

  b1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          int c=0;

          name=t1.getText().toString();
           mail=t2.getText().toString();
           id=t3.getText().toString();
         password=t4.getText().toString();
           mobile=t6.getText().toString();
           cabino="nothing";
          if(name.length()==0)
          {
              c=c-1;
              s=s+"Name Field Required \n";
          }
          if(mail.length()==0)
          {
              c=c-1;
              s=s+"Mail Field Required \n";
          }

          if(id.length()==0)
          {
              c=c-1;
              s=s+"ID Field Required \n";
          }
          if(s.equals("SignIn As"))
          {
              s=s+"Choose Student (or) Faculty \n";
              c=c-1;
          }
          if(s.equals("Faculty"))
          {
              cabino=t5.getText().toString();


              if(id.length()==5)
              {
                  c=c+1;
              }else {
                  c=c-1;
                  s=s+" Invalid ID\n";
              }
              if(cabino.equals("nothing"))
              {
                  c=c-1;
                  s=s+"Cabin No Field Required \n";
              }
          }

          if(password.length()>6)
          {
              c=c+1;

          }else if(password.length()!=0){
              c=c-1;
              s=s+"Password Length should greater than 6 characters \n";
          }else {
              c=c-1;
            s=s+" Password Field Required \n";
          }
          if(mobile.length()==10)
          {
              c=c+1;
          }else if(mobile.length()!=0){
              c=c-1;
             s=s+"Invalid Mobile Number \n";
          }else {
              c=c-1;
              s=s+"Mobile Number Field Required \n";
          }
          if(s.equals("Student"))
          {
              cabino="Student";
              if(id.length()==9)
              {
                  c=c+1;
              }else {
                 s=s+" Invalid ID\n";
              }
          }

          String a="";
          if(c==3)
          {

              FirebaseDatabase database = FirebaseDatabase.getInstance();
              if(cabino.equals("Student"))
              {


                          DatabaseReference myRef = database.getReference("Student_Accounts/" + id);
                          HashMap<String, Object> UserDetails = new HashMap<>();
                          // Inserting Data
                          UserDetails.put("ID", id);
                          UserDetails.put("Name", name);
                          UserDetails.put("Password", password);
                          UserDetails.put("Mail ID", mail);
                          UserDetails.put("Mobile No", mobile);
                          myRef.setValue(UserDetails);
                          a="Account Creation SuccessFul";
                  try {
                      Toast.makeText(getApplicationContext(),a,Toast.LENGTH_LONG).show();
                      Thread.sleep(2000);
                      Intent i=new Intent(Account_Creation.this,SignIn_Page.class);
                      startActivity(i);

                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }

//                  System.out.println(myRef);

              }else if(cabino.length()!=0)
              {


                       DatabaseReference myRef = database.getReference("Faculty_Accounts/" + id);
                       HashMap<String, Object> UserDetails = new HashMap<>();

                       UserDetails.put("ID", id);
                       UserDetails.put("Name", name);
                       UserDetails.put("Password", password);
                       UserDetails.put("Status", "Unavailable");
                       UserDetails.put("Mail ID", mail);
                       UserDetails.put("Mobile No", mobile);
                       UserDetails.put("Cabin No", cabino);

                       DatabaseReference myRef2 = database.getReference("Faculty_Name/" + name);
                       HashMap<String, Object> extra = new HashMap<>();
                       extra.put("ID", id);
                       myRef2.setValue(extra);
                       myRef.setValue(UserDetails);
                     a="Account Creation SuccessFul";
                  try {
                      Toast.makeText(getApplicationContext(),a,Toast.LENGTH_LONG).show();
                      Thread.sleep(2000);
                      Intent i=new Intent(Account_Creation.this,SignIn_Page.class);
                      startActivity(i);
                      mobile="";
                      cabino="";
                      name="";
                      id="";
                      password="";
                      mail="";

                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }


              }else {
                  a="Account Creation Not Successful";
                  Toast.makeText(getApplicationContext(),a,Toast.LENGTH_LONG).show();
                  mobile="";
                  cabino="";
                  name="";
                  id="";
                  password="";
                  mail="";
              }




              //Retriving Data
              
//             myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                 @Override
//                 public void onComplete(@NonNull Task<DataSnapshot> task) {
//                     if (!task.isSuccessful()) {
//                         Log.e("firebase", "Error getting data", task.getException());
//                     }
//                     else {
//                         Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                     }
//                 }
//             });



              //Intent i=new Intent(Account_Creation.this,SignIn_Page.class);
             // startActivity(i);

              t1.setText("");
              t2.setText("");
              t3.setText("");
              t4.setText("");
              t5.setText("");
              t6.setText("");
              mobile="";
              cabino="";
              name="";
              id="";
              password="";
              mail="";
          }else {

              t1.setText("");
              t2.setText("");
              t3.setText("");
              t4.setText("");
              t5.setText("");
              t6.setText("");
              Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
              s="";
              mobile="";
              cabino="";
              name="";
              id="";
              password="";
              mail="";

          }

      }
  });
    }

}