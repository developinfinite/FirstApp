package com.example.firstapp;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*
#CODE WRITTEN BY SANDEEP MEHTA
*/
public class MainActivity extends AppCompatActivity {

    Button btnOnOff;
    TextView labelTextView;
    BluetoothAdapter mBlueToothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelTextView = (TextView) findViewById(R.id.textView);
        btnOnOff = (Button) findViewById(R.id.buttonOnOff);


        IntentFilter btFilter = new IntentFilter(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        registerReceiver(mBTBroadcastReceiver, btFilter );

        btnOnOff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                enableDisableBT();
            }
        });
        toggleBTButtonColor();
    }


    private final BroadcastReceiver mBTBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            String status = "Unknown";
            if(intentAction.equals(mBlueToothAdapter.ACTION_STATE_CHANGED)){
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state){
                    case BluetoothAdapter.STATE_ON:
                        status = "STATE_ON";
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        status = "STATE_OFF";
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        status = "STATE_CONNECTING";
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        status = "STATE_CONNECTED";
                        break;
                    case BluetoothAdapter.STATE_DISCONNECTED:
                        status = "STATE_DISCONNECTED";
                        break;
                    case BluetoothAdapter.STATE_DISCONNECTING:
                        status = "STATE_DISCONNECTING";
                        break;
                    default:
                        status = "STATE_UNKNOWN";
                        break;

                }
                Log.d("BT STATUS",status);
                labelTextView.setText(new String ("BT STATUS "+ status));
            }
        }
    };

    private void enableDisableBT() {

        mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBlueToothAdapter == null) {
            return;
        }

        if(!mBlueToothAdapter.isEnabled()){
            mBlueToothAdapter.enable();
            btnOnOff.setText("BT ON");
            btnOnOff.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
            Toast.makeText(getApplicationContext(),
                    "Bluetooth ON", Toast.LENGTH_LONG).show();
            //Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivity(enableBTIntent);

            //IntentFilter btFilter = new IntentFilter(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //registerReceiver(mBTBroadcastReceiver, btFilter );
        }

        else if(mBlueToothAdapter.isEnabled()){
            mBlueToothAdapter.disable();
            btnOnOff.setText("BT OFF");
            btnOnOff.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            Toast.makeText(getApplicationContext(),
                    "Bluetooth ON", Toast.LENGTH_LONG).show();

            //IntentFilter btFilter = new IntentFilter(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //registerReceiver(mBTBroadcastReceiver, btFilter );
        }
    }

    private void toggleBTButtonColor() {

        mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBlueToothAdapter == null) {
            return;
        }

        if(!mBlueToothAdapter.isEnabled()){
            btnOnOff.setText("BT ON");
            btnOnOff.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
        }else if(mBlueToothAdapter.isEnabled()){
            btnOnOff.setText("BT OFF");
            btnOnOff.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBTBroadcastReceiver);
    }
}
