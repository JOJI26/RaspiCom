package com.example.jojie.raspicom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class AnalysisActivity extends AppCompatActivity {

    String ip = "";
    int port = 0;
    TextView textView1;
    Paint paint;
    Canvas canvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_analysis );

        Intent intent = getIntent();
        ip = intent.getStringExtra( MainActivity.IP );
        port = Integer.parseInt( intent.getStringExtra( MainActivity.PORT ) );
        System.out.println("IP: "+ip);
        System.out.println("Port: "+ port);

        MyClientTask clientTask = new MyClientTask( "192.168.0.109", 8080 );
        clientTask.execute();

        textView1 = findViewById( R.id.textView );

        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
        myOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.engine,myOptions);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor( Color.BLUE);


        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);


        canvas = new Canvas(mutableBitmap);
        canvas.drawCircle(60, 50, 25, paint);

        ImageView imageView = findViewById(R.id.imageView_engine);
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(mutableBitmap);

    }

    public class MyClientTask extends AsyncTask<Void, Void, Void> {


        String dstAddress;
        int dstPort;
        String response = "";

        MyClientTask(String addr, int port) {
            dstAddress = addr;
            dstPort = port;

            Log.e( "init", "MyClientTask: " );
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                Socket socket = new Socket( dstAddress, dstPort );

                PrintWriter send = new PrintWriter( socket.getOutputStream(), true );

                BufferedReader receive = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

                Log.e( "background", "MyClientTask: " );

                while (true) {


                    String receiveData = receive.readLine();
                    receiveData = receiveData.replaceAll( "(\\r|\\n)", "" );

                    System.out.println("receiveData:"+ receiveData );
                    response = response + receiveData;

                    if (receiveData.equalsIgnoreCase( "10" )) {

                        Log.e( "inside blue", "MyClientTask: " );
                    }
                    else if(receiveData.equalsIgnoreCase( "20" )){

                        Log.e( "inside blue", "MyClientTask: " );
                    }




                    Log.e( "Under While", "MyClientTask: " );

                    if (receiveData.equalsIgnoreCase( "EXIT" )) {

                        socket.close();
                        Log.e( "Exit", "MyClientTask: " );
                        break;
                    }


                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.e( "Unknown Exception", e.getMessage() );
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.e( "IO Exception", e.getMessage() );
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute( aVoid );
            Log.e( "PostExecute", "MyClientTask: " );

            textView1.setText( response );
        }
    }
}
