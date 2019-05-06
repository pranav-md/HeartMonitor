package pmd.comprssr.com.heartmonitor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_login);

//        Toast.makeText(this, "heyyy", Toast.LENGTH_SHORT).show();
        Button enter=findViewById(R.id.login);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username=(EditText) findViewById(R.id.u_name);
                EditText password=(EditText) findViewById(R.id.password);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference dataRef = database.getReference();
                dataRef.child("Login").child(username.getText().toString()).child(password.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            FancyToast.makeText(MainActivity.this,"Logged in Successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                            startActivity(new Intent(MainActivity.this, ShowAllPatients.class));
                            finish();
                        }
                        else
                        {
                            FancyToast.makeText(MainActivity.this,"Login failed",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
