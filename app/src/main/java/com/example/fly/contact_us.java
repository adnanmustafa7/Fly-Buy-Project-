package com.example.fly;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class contact_us extends AppCompatActivity {

    EditText Subject, Message;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Subject = (EditText) findViewById(R.id.sub);
        Message = (EditText) findViewById(R.id.msg);
        Button Send = (Button) findViewById(R.id.send);


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!Message.getText().toString().isEmpty() && !Subject.getText().toString().isEmpty())
                {

                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"adnanax1234@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT,Subject.getText().toString());
                    intent.putExtra(Intent.EXTRA_TEXT,Message.getText().toString());
                    intent.setData(Uri.parse("mailto:"));
                    if (intent.resolveActivity(getPackageManager()) != null ){
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(contact_us.this,"There is no Application Support",
                                Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(contact_us.this,"Please Fill this First",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}