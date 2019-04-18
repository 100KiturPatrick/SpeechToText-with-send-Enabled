package com.example.speechtotext;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

     TextView txvResult;
     Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txvResult = (TextView) findViewById(R.id.txvResult);


    }
    public void shareText(View view)
    {
        String message = txvResult.getText().toString();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");

        Intent chooser = Intent.createChooser(sendIntent, "Share with friends");

        if(sendIntent.resolveActivity(getPackageManager()) != null){
            startActivity(chooser);
        }
    }

    public void getSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,10);
        }else {
            Toast.makeText(this, "Your device doesn't support speech input",Toast.LENGTH_SHORT).show();
        }

//        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.setText(result.get(0));
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
