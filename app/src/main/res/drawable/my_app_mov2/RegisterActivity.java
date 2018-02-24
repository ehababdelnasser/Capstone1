package com.example.lenovo.my_app_mov2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "register";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @BindView(R.id.email_reg)
    EditText e_reg;
    @BindView(R.id.password_reg)
    EditText pass_reg;
    @BindView(R.id.name)
    EditText n_reg;
    @BindView(R.id.signup)
    Button sign_up;
    @BindView(R.id.login)
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityregister);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String myemail = e_reg.getText().toString();
                String pass = pass_reg.getText().toString();
                String myname = n_reg.getText().toString();
                if (myemail.equals("") || pass.equals("") || myname.equals(""))
                    Toast.makeText(com.example.lenovo.my_app_mov2.RegisterActivity.this,R.string.fill_all, Toast.LENGTH_SHORT).show();
                else {
                    mAuth.createUserWithEmailAndPassword(myemail, pass)
                            .addOnCompleteListener(com.example.lenovo.my_app_mov2.RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Log.d(TAG, "createUserWithEmail:success");
                                        Toast.makeText(com.example.lenovo.my_app_mov2.RegisterActivity.this, R.string.succes, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(com.example.lenovo.my_app_mov2.RegisterActivity.this, com.example.lenovo.my_app_mov2.MainActivity.class);
                                        startActivity(intent);


                                    } else {

                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(com.example.lenovo.my_app_mov2.RegisterActivity.this, R.string.failed,
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.lenovo.my_app_mov2.RegisterActivity.this, com.example.lenovo.my_app_mov2.LoginActivty.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


}
