package com.example.zsombor.rootdetectordemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private TextView et;
    private Button checkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkButton = (Button) findViewById(R.id.button);
        et = (TextView) findViewById(R.id.textView);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String cap = "";
                if(isRooted()){
                    cap = "Rooted";
                } else {
                    cap = "Clean";
                }

                et.setText(cap);
                }
        });
    }

    public boolean isRooted() {
        Log.d("myapp", Log.getStackTraceString(new Exception()));

        // get from build info
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }

        // check if /system/app/Superuser.apk is present
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e1) {
            // ignore
        }

        // try executing commands
        return canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");

    }

    // executes a command on the system
    private boolean canExecuteCommand(String command) {
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }

        return executedSuccesfully;
    }
}
