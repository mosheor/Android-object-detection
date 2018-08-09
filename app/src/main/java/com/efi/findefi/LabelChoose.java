package com.efi.findefi;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class LabelChoose extends Activity {
    static final int REQUEST_CAMERA = 1337;
    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_label_choose);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.label);
        Button btn = findViewById(R.id.button);

        String[] arr = null;
        String label = autoCompleteTextView.getText().toString();
        List<String> items= new ArrayList<String>();

        try
        {
            InputStreamReader is = new InputStreamReader(getAssets().open("class-descriptions.csv"));
            BufferedReader buffer = new BufferedReader(is);
            String str_line;

            while ((str_line = buffer.readLine()) != null)
            {
                str_line = str_line.trim();
                if ((str_line.length()!=0))
                {
                    str_line = str_line.split(",")[1];
                    if(str_line.startsWith(label))
                        items.add(str_line);
                }
            }

            arr = (String[])items.toArray(new String[items.size()]);

        } catch (FileNotFoundException e) {
            Log.d("TAG","FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("TAG","IOException");
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arr);
        autoCompleteTextView.setAdapter(adapter);

        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED);

        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getContext(), CameraActivity.class);
                intent.putExtra("label",autoCompleteTextView.getText().toString());
                startActivity(intent);

                /*Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                }*/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        /*if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            ((VideoView)findViewById(R.id.video)).setVideoURI(videoUri);
        }*/
    }
}
