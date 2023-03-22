package com.oguzkass.socialgo.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oguzkass.socialgo.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        //GİRİŞ YAPMIŞ KULLANICILAR İÇİN DİREKT FEED EKRANINA GİTME
        FirebaseUser user = auth.getCurrentUser();
        //GİRİŞ OLMUŞ MU OLMAMIŞ MI KONTROLÜ ONA GÖRE FEED EKRANINA YÖNLENDİRME
        if (user != null) {
            Intent intent = new Intent(MainActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();
        }

        
    }

    public void singInClicked (View view) {
        email = binding.emailText.getText().toString();
        password = binding.passwordText.getText().toString();
        if (email.equals("") || password.equals("")){
            Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
        }else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }



    }

    public void singUpClicked (View view) {

        email = binding.emailText.getText().toString();
        password = binding.passwordText.getText().toString();
        if (email.equals("") || password.equals("")){
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
        } else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT );
                }
            });
        }


    }

}
