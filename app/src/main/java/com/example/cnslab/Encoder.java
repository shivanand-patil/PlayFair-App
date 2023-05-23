package com.example.cnslab;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Encoder extends AppCompatActivity {

    EditText etenc,etenc1;
    TextView enctv;
    ClipboardManager cpb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encoder);

        // link the edittext and textview with its id
        etenc = findViewById(R.id.etVar1);//plaintext
        etenc1= findViewById(R.id.etVar2 );//key
        enctv = findViewById(R.id.tvVar1);//encrypt

        // create a clipboard manager variable to copy text
        cpb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    // onClick function of encrypt button
    public void enc(View view) {
        // get text from edittext
        String temp = etenc.getText().toString();//pt
        String temp1 =etenc1.getText().toString();//key

        // pass the string to the encryption
        // algorithm and get the encrypted code
        PlayfairEncode rv = new PlayfairEncode(temp1,temp);
        rv.cleanPlayFairKey();
        rv.generateCipherKey();
        String encryptedone = null;
        try {
            encryptedone= rv.encryptMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // set the code to the edit text
        enctv.setText(encryptedone);
    }

    // onClick function of copy text button
    public void cp2(View view) {
        // get the string from the textview and trim all spaces
        String data = enctv.getText().toString().trim();

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
