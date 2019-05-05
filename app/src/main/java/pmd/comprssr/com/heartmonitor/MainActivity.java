package pmd.comprssr.com.heartmonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_device_waveform);

//        Toast.makeText(this, "heyyy", Toast.LENGTH_SHORT).show();
        FancyToast.makeText(this,"Logged in Successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
    }
}
