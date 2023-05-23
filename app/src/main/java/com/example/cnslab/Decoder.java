package com.example.cnslab;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Decoder extends AppCompatActivity {

    EditText etdec,etdec2;
    TextView dectv;
    ClipboardManager cpb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);

        // link the edittext and textview with its id
        etdec = findViewById(R.id.etVar1);
        etdec2 = findViewById(R.id.etVar2);

        dectv = findViewById(R.id.tvVar2);

        // create a clipboard manager variable to copy text
        cpb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    // onClick function of encrypt button
    public void dec(View view) {
        // get code from edittext
        String temp = etdec.getText().toString();
        String temp1 = etdec2.getText().toString();
        Log.e("dec", "text - " + temp);

        // pass the string to the decryption algorithm
        // and get the decrypted text

        PlayfairDecode rv = new PlayfairDecode(temp1,temp);
        rv.cleanPlayFairKey();
        rv.generateCipherKey();
        // set the text to the edit text for display
        String decryptedOne=rv.decryptMessage();

        dectv.setText(decryptedOne);
        Log.e("dec", "text - " + rv);
    }

    // onClick function of copy text button
    public void cpl(View view) {

        // get the string from the textview and trim all spaces
        String data = dectv.getText().toString().trim();

        // check if the textview is not empty
        if (!data.isEmpty()) {

            // copy the text in the clip board
            ClipData temp = ClipData.newPlainText("text", data);
            cpb.setPrimaryClip(temp);
            // display message that the text has been copied
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
        }
    }
}
