package pmd.comprssr.com.heartmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

public class EnterPatientDetails extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_patient);
        Button enter_details=(Button)findViewById(R.id.submit);
        enter_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference dataRef = database.getReference();
                EditText pat_name=(EditText)findViewById(R.id.name);
                EditText age=(EditText)findViewById(R.id.age);
                Spinner gender=(Spinner)findViewById(R.id.gender);
                EditText pat_address=(EditText)findViewById(R.id.address);

                dataRef.child("PatientData").child(pat_name.getText().toString())
                                            .child("Name").setValue(pat_name.getText().toString());

                dataRef.child("PatientData").child(pat_name.getText().toString())
                        .child("Age").setValue(age.getText().toString());

                dataRef.child("PatientData").child(pat_name.getText().toString())
                        .child("Gender").setValue(gender.getSelectedItem());

                dataRef.child("PatientData").child(pat_name.getText().toString())
                        .child("Address").setValue(pat_address.getText().toString());

                dataRef.child("AllPatients").child(pat_name.getText().toString())
                            .setValue(pat_address.getText().toString());
                FancyToast.makeText(EnterPatientDetails.this,"Entered Successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                finish();

            }
        });

    }
}
