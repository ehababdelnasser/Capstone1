package com.example.lenovo.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivty extends AppCompatActivity  {

    private static final String TAG = "Login";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText pass;
    @BindView(R.id.login)
    AppCompatButton loginbut;
    @BindView(R.id.signup)
    TextView createacc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylogin);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();


        loginbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaitext = email.getText().toString();
                String passwordtext = pass.getText().toString();
                if (emaitext.equals("") || passwordtext.equals("")) {
                    Toast.makeText(LoginActivty.this,R.string.fill_all , Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(emaitext, passwordtext)
                            .addOnCompleteListener(LoginActivty.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(LoginActivty.this, R.string.Succ, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivty.this, MainActivity.class);
                                        startActivity(intent);

                                    } else {

                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivty.this,R.string.failed,
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });

        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivty.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivty.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }



}
