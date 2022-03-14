package com.samzuhalsetiawan.tesjava;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button mButton = findViewById(R.id.btnSendResult);
        mButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("EXTRA_DATA", "Hallo Dunia");
            setResult(RESULT_OK, intent);
            finish();
        });

    }
}