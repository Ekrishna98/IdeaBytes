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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

//import android.view.View;

public class Register extends AppCompatActivity {
    public TextView textView;
    public EditText etfn, etln, phone, etmail, etpass, etcnpass;
    RDHelper rd;
    public Button button;


    // Firebase data variable
    FirebaseFirestore mfb;
    FirebaseAuth mAuth;
    DatabaseReference DR = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ideabytes-5a089-default-rtdb.firebaseio.com/");


    // Navigation Drawer variables
    private ActionBarDrawerToggle toggle;
    private NavigationView nav;
    private DrawerLayout drawer1;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        button = findViewById(R.id.btn);
        textView = findViewById(R.id.tv4);

        etfn = findViewById(R.id.etFN);
        etln = findViewById(R.id.etLN);
        phone = findViewById(R.id.editTextPhone);
        etmail = findViewById(R.id.etMail1);
        etpass = findViewById(R.id.etPass);
        etcnpass = findViewById(R.id.etCnPass);

        rd = new RDHelper(Register.this);

//        TelephonyManager tm = (TelephonyManager)getSystemService(getApplicationContext().TELEPHONY_SERVICE);
//        tm.getNetworkCountryIso();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation();

            }

        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(MainActivity.this,MainActivity2.class);   ( or)
                startActivity(new Intent(Register.this, Login.class));
            }
        });


        //****  Navigation Drawer Stating...  ***//

        nav = findViewById(R.id.nvReg);
        drawer1 = findViewById(R.id.drawer1);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        nav.setVisibility(View.VISIBLE);

        toggle = new ActionBarDrawerToggle(this, drawer1, toolbar, R.string.open, R.string.close);
        drawer1.addDrawerListener(toggle);
        toggle.syncState();

        // Adding Click events to our navigation drawer
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.contactus:
                        Toast.makeText(Register.this, "contact open", Toast.LENGTH_SHORT).show();
                        drawer1.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.settings_menu:
                        Toast.makeText(Register.this, "settings open", Toast.LENGTH_SHORT).show();
                        drawer1.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout:
                        Toast.makeText(Register.this, "logout", Toast.LENGTH_SHORT).show();
                        //drawer1.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                }
                return true;
            }
        });    // ***   Navigation Drawer Ends.  **** //

    }


    private void Validation() {
        String efn = etfn.getText().toString();
        String eln = etln.getText().toString();
        String ephone = phone.getText().toString();
        String emailVarify = etmail.getText().toString();
        String etpass1 = etpass.getText().toString();
        String cnpass = etcnpass.getText().toString();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordcheck = "^(?=.*[A-Z])(?=.*[@_.]).*$";

        if (efn.isEmpty() || eln.isEmpty() || ephone.isEmpty() || emailVarify.isEmpty() || etpass1.isEmpty() || cnpass.isEmpty()) {
            Toast.makeText(Register.this, "please enter all the data...", Toast.LENGTH_SHORT).show();
            return;
        } else if (!emailVarify.matches(emailPattern)) {
            etmail.setError("invalid email");
            etmail.requestFocus();
            return;
        } else if (!etpass1.matches(passwordcheck)) {
            etpass.setError("invalid password");
            etpass.requestFocus();
            return;
        } else if (!etpass1.matches(cnpass)) {
            etcnpass.setError("password not match");
            etcnpass.requestFocus();
            return;
        } else
        {
            // **** Firebase Data store code ***//
        DR.child("employees").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(efn)){
                    Toast.makeText(Register.this, "Phone Number is already exsist", Toast.LENGTH_SHORT).show();
                }else if(!snapshot.hasChild(efn)){
                    DR.child("employees").child(efn).child("FirstName").setValue(efn);
                    DR.child("employees").child(efn).child("LastName").setValue(eln);
                    DR.child("employees").child(efn).child("phone").setValue(ephone);
                    DR.child("employees").child(efn).child("email").setValue(emailVarify);
                    DR.child("employees").child(efn).child("Password").setValue(etpass1);
                    Toast.makeText(Register.this, "Data store realtime", Toast.LENGTH_SHORT).show();

                    mAuth.createUserWithEmailAndPassword(emailVarify,etpass1)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        rd.addEmpData(efn, eln, ephone, emailVarify, etpass1);
                                        etfn.setText("");
                                        etln.setText("");
                                        phone.setText("");
                                        etmail.setText("");
                                        etpass.setText("");
                                        etcnpass.setText("");
                                        Toast.makeText(Register.this, "Rester Details added", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(Register.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Register.this, Login.class));
                                        finish();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //*** SQLite data store code **//
//            rd.addEmpData(efn, eln, ephone, emailVarify, etpass1);
//            Toast.makeText(Register.this, "Rester Details added", Toast.LENGTH_SHORT).show();
//            //startActivity(new Intent(Register.this, Login.class));


        }
    }

}



