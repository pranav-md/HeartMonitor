package pmd.comprssr.com.heartmonitor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowAllPatients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_patients);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dataRef = database.getReference();
        ProgressDialog progressBar=new ProgressDialog(this);
        progressBar.setMessage("Loading Patients");
        progressBar.show();

        dataRef.child("AllPatients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.dismiss();
                LinearLayout parent_address = findViewById(R.id.pat_list);
                parent_address.removeAllViews();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {

                    //Log.d("name ", device_name);
                    // Log.d("address ", device_address);

                    LinearLayout list_layout = new LinearLayout(ShowAllPatients.this);


                    //device name
                    TextView item_name = new TextView(ShowAllPatients.this);
                    item_name.setTextColor(getResources().getColor(R.color.white));
                    item_name.setTextSize(25);
                    LinearLayout.LayoutParams name_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    name_params.setMargins(10, 0, 0, 10);
                    name_params.weight = 1;
                    item_name.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle_blue));
                    item_name.setLayoutParams(name_params);
                    item_name.setPadding(2,5,2,5);
                    item_name.setTag(ds.getKey());
                    item_name.setText(ds.getKey());
                    item_name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String pat_name=v.getTag().toString();
                            Intent intent=new Intent(ShowAllPatients.this,ShowPatient.class);
                            intent.putExtra("NAME",pat_name);
                            startActivity(intent);

                        }
                    });
                    parent_address.addView(item_name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Button add_patient=(Button) findViewById(R.id.add_patient);
        add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowAllPatients.this,EnterPatientDetails.class));

            }
        });
    }
}
