package com.example.firstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Set;

public class BTMaster extends AppCompatActivity {



    private ArrayList<DeviceItem> deviceItemList;
    private static BluetoothAdapter bTAdapter;
    /**
     * The fragment's ListView/GridView.
     */

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
//    private ArrayAdapter<DeviceItem> mAdapter;


    private final BroadcastReceiver bReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.d("DEVICELIST", "Bluetooth device found\n");

            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btmaster);
        bTAdapter = BluetoothAdapter.getDefaultAdapter();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                btMethod(BTMaster.this);
            }
        });

        initMaster();



        ToggleButton scan = (ToggleButton) findViewById(R.id.scan);
        scan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                if (isChecked) {
//                    mAdapter.clear();
                    registerReceiver(bReciever, filter);
                    bTAdapter.startDiscovery();
                } else {
                    unregisterReceiver(bReciever);
                    bTAdapter.cancelDiscovery();
                }
            }
        });
    }
    public void initMaster(){

        Log.d("DEVICELIST", "Super called for DeviceListFragment onCreate\n");
        deviceItemList = new ArrayList<DeviceItem>();

        Set<BluetoothDevice> pairedDevices = bTAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                DeviceItem newDevice= new DeviceItem(device.getName(),device.getAddress(),"false");
                deviceItemList.add(newDevice);
                Log.d("DEVICELIST", newDevice.getDeviceName());
            }
        }

        // If there are no devices, add an item that states so. It will be handled in the view.
        if(deviceItemList.size() == 0) {
            deviceItemList.add(new DeviceItem("No Devices", "", "false"));
        }

        Log.d("DEVICELIST", "DeviceList populated\n");

//        mAdapter = new DeviceListAdapter(getActivity(), deviceItemList, bTAdapter);

        Log.d("DEVICELIST", "Adapter created\n");

    }
}
