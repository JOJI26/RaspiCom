package com.example.jojie.raspicom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    public static String IP= "com.example.jojie.raspi.MESSAGE";
    public static String PORT= "com.example.jojie.raspi.MESSAGE";


    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;

    Intent intent;

    OnClickListener buttonConnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {


            intent = new Intent( MainActivity.this, AnalysisActivity.class );
            intent.putExtra( IP, editTextAddress.getText().toString() );
            intent.putExtra( PORT, editTextPort.getText().toString() );
            startActivity( intent );
            //finish();

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        editTextAddress = findViewById( R.id.address );
        editTextPort = findViewById( R.id.port );
        buttonConnect = findViewById( R.id.connect );
        buttonClear = findViewById( R.id.clear );
        textResponse = findViewById( R.id.response );

        buttonConnect.setOnClickListener( buttonConnectOnClickListener );

        buttonClear.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {
                textResponse.setText( "" );
            }

        } );
    }


}