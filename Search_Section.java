package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Search_Section extends AppCompatActivity {
Button b1;
TextView t1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_section);

    b1=findViewById(R.id.back);
    t1=findViewById(R.id.vals);
    Intent i=getIntent();
    String fname=i.getStringExtra("fname");
    String id=i.getStringExtra("id");

        String s="";
        t1.setText(fname);

    if(fname.length()!=0)
    {
        readData(fname);

    }



    b1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i=new Intent(Search_Section.this,LoggedIn_Page.class);
           i.putExtra("Id",id);
           startActivity(i);
        }
    });
    }
    private void readData(String fname)
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Faculty_Name");
        reference.child(fname).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {



                        DataSnapshot dataSnapshot=task.getResult();
                        String s=String.valueOf(dataSnapshot.child("ID").getValue().toString());
                        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                       DatabaseReference ref2=FirebaseDatabase.getInstance().getReference("Faculty_Accounts");
                       ref2.child(s).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<DataSnapshot> task1) {

                               String cabin="",mobile="",gmail="",status="";
                               DataSnapshot d1=task1.getResult();
                                cabin="Cabin No :"+String.valueOf(d1.child("Cabin No").getValue().toString());
                                mobile="Mobile No:"+String.valueOf(d1.child("Mobile No").getValue().toString());
                                gmail="Mail ID :"+String.valueOf(d1.child("Mail ID").getValue().toString());
                              status="Status :"+String.valueOf(d1.child("Status").getValue().toString());
                               String s="Name :"+fname+"\n\n"+cabin+"\n\n"+mobile+"\n\n"+gmail+"\n\n"+status;
                               t1.setText(s);
                               Toast.makeText(getApplicationContext(),"Faculty Found",Toast.LENGTH_LONG).show();


                           }
                       });



                    }else {
                        Toast.makeText(getApplicationContext(),"Faculty Not Found ",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}