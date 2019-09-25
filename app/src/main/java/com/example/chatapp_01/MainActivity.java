package com.example.chatapp_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    User user;

    //cached
    EditText usernameEditText,passwordEditText;
    TextView usernameHint,passwordHint;

    //this function will run when server send back the response
    public void onSignupLoginResponse(int flag)
    {
        switch(flag)
        {
            case CommunicationFlags.SIGN_UP_SUCCESSFULL:
//                Toast.makeText(this,"SIGN_UP SUCCESSFULL",Toast.LENGTH_SHORT).show();
                break;
            case CommunicationFlags.LOGIN_SUCCESSFULL:
//                Toast.makeText(this,"LOG_IN SUCCESSFULL",Toast.LENGTH_SHORT).show();
                //Intent act2 = new Intent(this,act2.class);

                break;
            case CommunicationFlags.SIGN_UP_FAILED:
                usernameHint.setText("Invalid Username,It is taken by Another User!");
                break;
            case CommunicationFlags.LOGIN_FAILED:
                usernameHint.setText("Invalid Username!");
                passwordHint.setText("Invalid Password!");
                break;
        }
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

        user = new User(this,username,password);
        //Process for Authenticating user
        try
        {
            if(button.getId() == R.id.loginButton)
            {
                user.login();
            }
            else if(button.getId() == R.id.signupButton)
            {
                Toast.makeText(this,"Signing Up...",Toast.LENGTH_SHORT).show();
                user.signup();
            }
        }//try
        catch(IllegalArgumentException err)
        {
            Log.i("info",err.toString());
        }//catch
    }//onClickAuthenticate

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cache variables
        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);

        usernameHint = (TextView)findViewById(R.id.usernameHintTextView);
        passwordHint = (TextView)findViewById(R.id.passwordHintTextView);
    }
}
