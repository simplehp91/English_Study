package jp.ac.jec.a16cm0209.english_study;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStart, btnEdit, btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControl() {
        btnStart = (Button) findViewById(R.id.btnStart);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnList = (Button) findViewById(R.id.btnList);
    }
}
