package com.example.chatapp_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity
{
    EditText usernameEditText,passwordEditText;
    TextView usernameHint,passwordHint;

    public void test()
    {
        System.out.println("Test passed");
    }

    public void onClickAuthenticate(View view)
    {
        //@TODO create password manager to validate password by regex
        Button button = (Button)view;
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        usernameHint.setText("");
        passwordHint.setText("");

        if(username.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this,"Please Provide a Valid Username And Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(username.contains(" "))
        {
            usernameHint.setText("Please no Spaces in username.");
            return;
        }

        Authenticator authenticator = new Authenticator();
        //Process for Authenticating user
        try
        {
            if(button.getId() == R.id.loginButton)
            {
                Toast.makeText(this,"Logging you in...",Toast.LENGTH_SHORT).show();
                authenticator.execute(username,password,Integer.toString(CommunicationFlags.LOGIN));
            }
            else if(button.getId() == R.id.signupButton)
            {
                Toast.makeText(this,"Signing Up...",Toast.LENGTH_SHORT).show();
                authenticator.execute(username,password,Integer.toString(CommunicationFlags.SIGN_UP));
            }
        }//try
        catch(IllegalArgumentException err)
        {
            Log.i("info",err.toString());
        }
    }//onClickAuthenticate

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cache variables
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        usernameHint = findViewById(R.id.usernameHintTextView);
        passwordHint = findViewById(R.id.passwordHintTextView);
    }
 }
