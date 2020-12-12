package com.vkvish19.myexpensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Toast toast;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        firebaseAuth = FirebaseAuth.getInstance();
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    public void loginValidation(View view) {
        EditText id = findViewById(R.id.emailAddress);
        EditText password = findViewById(R.id.password);

        if (id.getText().toString().isEmpty()) {
            displayMessage(R.string.emptyIDError, id);
            return;
        }
        if (password.getText().toString().isEmpty()) {
            displayMessage(R.string.emptyPasswordError, password);
            return;
        } else {
            firebaseAuth.signInWithEmailAndPassword(id.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "No Records found, please Sign Up.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
        }

        LocalDatabaseHelper dbHelper = new LocalDatabaseHelper(LoginActivity.this, null, null, 1);

        // remove this in the final product
        if(id.getText().toString().equals("a") && password.getText().toString().equals("a")) {
            Intent toDashboard = new Intent(this, HomeActivity.class);
            UserData.userID = 1;
            startActivity(toDashboard);
        }

        IDType idType;
        if(id.getText().toString().contains("@")) {
            idType = IDType.Email;
        }
        else {
            idType = IDType.PhoneNumber;
        }

        if(password.getText().toString().equals(dbHelper.getPassword(id.getText().toString(), idType))) {
            Intent toDashboard = new Intent(this, HomeActivity.class);
            UserData.userID = dbHelper.getUserID(id.getText().toString(), idType);
            startActivity(toDashboard);
        }
        else {
            displayMessage(R.string.loginErrorMessage);
        }
    }

    private void displayMessage(int message) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(120);

        toast.setText(message);
        toast.show();
    }

    private void displayMessage(int message, View view) {
        Animation animationShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        view.setAnimation(animationShake);
        view.startAnimation(animationShake);

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(120);

        toast.setText(message);
        toast.show();
    }
}