package pmd.comprssr.com.heartmonitor;

import android.app.Activity;

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
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.scan.ScanSettings;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.disposables.Disposable;

public class ShowPatient extends Activity {

    ArrayList<String> address_list;
    private static final int REQUEST_BLUETOOTH = 1;
    BluetoothAdapter BA;
    RxBleClient rxBleClient;
    private BluetoothLeScanner mLEScanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_opened);
        address_list = new ArrayList<String>();
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BA = bluetoothManager.getAdapter();
        mLEScanner = BA.getBluetoothLeScanner();
        Button scan=(Button)findViewById(R.id.bt_scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBluetoothData();
            }
        });
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
        BluetoothLeScanner bluetoothScanner = BA.getBluetoothLeScanner();

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

    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    void bluetoothScanning() {

       BA.startDiscovery();
    }




    void addDevice(String device_name, String device_address) {
        address_list.add(device_address);
        LinearLayout parent_address = findViewById(R.id.address_parent);
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



}