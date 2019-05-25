package pmd.comprssr.com.heartmonitor;

import android.app.Activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.scan.ScanSettings;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import io.reactivex.disposables.Disposable;

public class ShowPatient extends Activity {

    ArrayList<String> address_list;
    private static final int REQUEST_BLUETOOTH = 1;
    BluetoothAdapter BA;
    RxBleClient rxBleClient;
    private BluetoothLeScanner mLEScanner;
    GraphView gview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_opened);
        Intent intent=getIntent();
        String name=intent.getStringExtra("NAME");
        GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 5),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6),
                new DataPoint(5, 5),
                new DataPoint(6, 5),
                new DataPoint(7, 3),
                new DataPoint(8, 2),
                new DataPoint(9, 6),
                new DataPoint(10, 5),
                new DataPoint(11, 5),
                new DataPoint(12, 3),
                new DataPoint(13, 2),
                new DataPoint(14, 6)

        });

       series.appendData(new DataPoint(15,2),false,20);
        graph.addSeries(series);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        Random rand = new Random();
        for(int i=16;i<50;i++)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            series.appendData(new DataPoint(i, rand.nextInt(10)),false,12);
            graph.addSeries(series);
        }


        address_list = new ArrayList<String>();
     //   setData(name);
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BA = bluetoothManager.getAdapter();
        mLEScanner = BA.getBluetoothLeScanner();

    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    void getBluetoothData() {
        if (!BA.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
        //bluetoothScanning();
       /* Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                addDevice(device.getName(),device.getAddress());
            }
        }*/
   /*     BluetoothLeScanner bluetoothScanner = BA.getBluetoothLeScanner();

        ScanCallback mScanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {

                Log.i("callbackType", String.valueOf(callbackType));

                BluetoothDevice btDevice = result.getDevice();
                addDevice(btDevice.getName(), btDevice.getAddress());
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                 for (ScanResult sr : results) {
                    Log.i("Scan Item: ", sr.toString());
                }
            }
        };
        BluetoothAdapter.LeScanCallback leScnCallback=new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                addDevice(device.getName(),device.getAddress());
            }


        };
        mLEScanner.startScan(mScanCallback);

        BA.startLeScan(leScnCallback);
*/
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    void bluetoothScanning() {

       BA.startDiscovery();
    }




 /*   void addDevice(String device_name, String device_address) {
        address_list.add(device_address);
  //      LinearLayout parent_address = findViewById(R.id.address_parent);
        //Log.d("name ", device_name);
       // Log.d("address ", device_address);

        LinearLayout list_layout = new LinearLayout(this);
        list_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        list_layout.setOrientation(LinearLayout.HORIZONTAL);

        //device name
        TextView item_name = new TextView(this);
        item_name.setTextColor(getResources().getColor(R.color.white));
        item_name.setTextSize(15);
        LinearLayout.LayoutParams name_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        name_params.setMargins(10, 0, 0, 10);
        name_params.weight = 1;
        item_name.setLayoutParams(name_params);

        item_name.setText(device_name);
        list_layout.addView(item_name);


        //device address
        TextView item_address = new TextView(this);
        item_address.setTextColor(getResources().getColor(R.color.white));
        item_address.setTextSize(15);
        LinearLayout.LayoutParams address_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        address_params.setMargins(50, 0, 0, 10);
        address_params.weight = 1;


        item_address.setLayoutParams(address_params);
        item_address.setText(device_address);
        list_layout.addView(item_address);


        parent_address.addView(list_layout);


    }

    /*
    void setData(String name)
    {
        ProgressDialog progressBar=new ProgressDialog(this);
        progressBar.setMessage("Loading Patient data");
        progressBar.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dataRef = database.getReference();
        dataRef.child("PatientData").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.dismiss();
                TextView pat_name=(TextView)findViewById(R.id.patient_name);
                TextView pat_age=(TextView)findViewById(R.id.age);
                TextView pat_gender=(TextView)findViewById(R.id.gender);
                TextView pat_address=(TextView)findViewById(R.id.address);

                pat_name.setText(dataSnapshot.child("Name").getValue().toString());
                pat_gender.setText("Gender :"+dataSnapshot.child("Gender").getValue().toString());
                pat_age.setText("Age :"+dataSnapshot.child("Age").getValue().toString());
                pat_address.setText("Address :"+dataSnapshot.child("Address").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
*/

}