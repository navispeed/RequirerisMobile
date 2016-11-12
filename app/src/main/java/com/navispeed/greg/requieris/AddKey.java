package com.navispeed.greg.requieris;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddKey extends AppCompatActivity {

    KeyList keyList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.keyList = MainActivity.keyList;
        setContentView(R.layout.activity_add_key);

        final EditText keyName = (EditText) findViewById(R.id.editText2);
        final EditText keyValue = (EditText) findViewById(R.id.editText3);

        Button viewById = (Button) findViewById(R.id.button2);
        Button cancelButton = (Button) findViewById(R.id.button3);



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyName.getText().toString().length() == 0 || keyValue.getText().toString().length() == 0) {
                    Context context = v.getContext();
                    CharSequence text = "Cannot be empty";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }

                if (!keyList.addKey(keyName.getText().toString(), keyValue.getText().toString())) {
                    Context context = v.getContext();
                    CharSequence text = "Key name already exist";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                Context context = v.getContext();
                CharSequence text = "Successfully adding key named " + keyName.getText().toString();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                keyList.save();
                finish();
            }
        });
    }

    public void setKeyList(KeyList keyList) {
        this.keyList = keyList;
    }

}
