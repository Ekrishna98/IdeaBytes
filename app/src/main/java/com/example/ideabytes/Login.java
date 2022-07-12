package com.example.ideabytes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    public TextView LogRegister;
    public EditText LoginEmail, LoginPass;
    public Button LoginBtn;

    // Navigation Drawer variables
    private ActionBarDrawerToggle toggle;
    private NavigationView nav;
    private DrawerLayout drawer;
    Toolbar toolbar;
    // firebaseFirestore variable
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;

    DatabaseReference DR = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ideabytes-5a089-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        LogRegister = findViewById(R.id.LogRegister);
        LoginEmail = findViewById(R.id.LoginMail);
        LoginPass = findViewById(R.id.LoginPassword);
        LoginBtn = findViewById(R.id.LoginBtn);


        // FirebasFirestore
        firestore= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validation1();
            }
        });

        LogRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent=new Intent(MainActivity2.this,MainActivity.class);
                startActivity(new Intent(Login.this, Register.class));
            }
        });

      // Navigation Drawer Stating...

        nav = findViewById(R.id.nvLog);
        drawer = findViewById(R.id.drawerLog);

        //getSupportActionBar().hide();
     toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        nav.setVisibility(View.VISIBLE);

        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Adding Click events to our navigation drawer
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.contactus:
                        Toast.makeText(Login.this, "contact open", Toast.LENGTH_SHORT).show();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.settings_menu:
                        Toast.makeText(Login.this, "settings open", Toast.LENGTH_SHORT).show();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout:
                        Toast.makeText(Login.this, "logout", Toast.LENGTH_SHORT).show();
                        //drawer.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                }
                return true;
            }
        });

    }

    // Email & Password  Validation check
    private void Validation1() {
        String emailVarify1 = LoginEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String pass = LoginPass.getText().toString();
        String passwordcheck = "^(?=.*[A-Z])(?=.*[@_.]).*$";


        if (emailVarify1.isEmpty() || !emailVarify1.matches(emailPattern)) {
            LoginEmail.setError("Invalid email");
            LoginEmail.requestFocus();
        }

        if (pass.isEmpty() || !pass.matches(passwordcheck)) {
            LoginPass.setError("Invalid password");
            LoginPass.requestFocus();
        } else {
            // Firebase email & password Validation
            mAuth.signInWithEmailAndPassword(emailVarify1, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                LoginEmail.setText("");
                                LoginPass.setText("");
                                startActivity(new Intent(Login.this, login_success.class));
                                finish();
                            } else {
                                Toast.makeText(Login.this, "invalid email/password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    }






