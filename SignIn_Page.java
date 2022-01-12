package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn_Page extends AppCompatActivity {
Button b1,b2,b3;
EditText t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        t1=findViewById(R.id.username);
        t2=findViewById(R.id.password);
        b3=findViewById(R.id.account_creation);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(SignIn_Page.this,Account_Creation.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                FirebaseDatabase database=FirebaseDatabase.getInstance();
                int c=0;
                String userid=t1.getText().toString();
                String password=t2.getText().toString();
                if(userid.length()==5)
                {
                    DatabaseReference myRef = database.getReference("Faculty_Accounts/" + userid);
                    myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            if(!task.isSuccessful())

                            {
                                Toast.makeText(getApplicationContext(),"Account Not Found",Toast.LENGTH_LONG).show();
                            }else {

                                String data=String.valueOf(task.getResult().getValue());
                                System.out.println(data);
                                String s1="";
                                for(int i=data.length()-1;i>=0;i--)
                                {
                                    if(data.charAt(i)!='}' && data.charAt(i)!='=')
                                    {
                                        s1=s1+data.charAt(i);
                                    }
                                    if(data.charAt(i)=='=')
                                    {
                                        break;
                                    }
                                }
                                StringBuffer builder=new StringBuffer(s1);




                                if(builder.reverse().toString().equals(password))
                                {
                                    Toast.makeText(getApplicationContext(),"Valid ",Toast.LENGTH_LONG).show();
                                    Intent i=new Intent(SignIn_Page.this,Teacher_Logged_In.class);
                                    i.putExtra("Id",userid);
                                    startActivity(i);

                                }else {

                                    Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                                    t1.setText("");
                                    t2.setText("");
                                }



                            }

                        }
                    });

                }else  {
                    DatabaseReference myRef = database.getReference("Student_Accounts/" + userid);
                    myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(!task.isSuccessful())

                            {
                                Toast.makeText(getApplicationContext(),"Account Not Found",Toast.LENGTH_LONG).show();
                            }else {
                                String data=String.valueOf(task.getResult().getValue());
                               System.out.println(data);
                                 String s1="";
                                 for(int i=data.length()-1;i>=0;i--)
                                 {
                                     if(data.charAt(i)!='}' && data.charAt(i)!='=')
                                     {
                                         s1=s1+data.charAt(i);
                                     }
                                     if(data.charAt(i)=='=')
                                     {
                                         break;
                                     }
                                 }
                               StringBuffer builder=new StringBuffer(s1);




                                 if(builder.reverse().toString().equals(password))
                                 {
                                     Toast.makeText(getApplicationContext(),"Login Successful ",Toast.LENGTH_LONG).show();
                                     Intent i=new Intent(SignIn_Page.this,LoggedIn_Page.class);
                                     i.putExtra("Id",userid);
                                     startActivity(i);

                                 }else {

                                     Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                                     t1.setText("");
                                     t2.setText("");
                                 }



                            }
                        }
                    });
                }


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.setText("");
                t2.setText("");
            }
        });
    }
}