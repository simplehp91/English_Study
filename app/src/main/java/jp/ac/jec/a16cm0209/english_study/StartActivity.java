package jp.ac.jec.a16cm0209.english_study;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    final String DATABASE_NAME = "DictEJ.sqlite";
    SQLiteDatabase database;

    Button btnBackS, btnNextS, btnShowS, btnEndS;
    ImageView imgViewS;
    TextView txtJaS, txtEngS;
    int num = 0;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        addControl();
        readData();
        addEvents();
    }

    private void addEvents() {
        btnEndS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnNextS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num < cursor.getCount() - 1){
                    num++;
                    setChanged();
                } else {
                    num = 0;
                    setChanged();
                }
            }
        });
        btnBackS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num > 0){
                    num--;
                    setChanged();
                } else {
                    num = cursor.getCount() - 1;
                    setChanged();
                }
            }
        });
    }

    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        cursor = database.rawQuery("SELECT * FROM dict ORDER BY eng", null);
            cursor.moveToPosition(num);
            String eng = cursor.getString(0);
            final String ja = cursor.getString(1);
            final byte[] image = cursor.getBlob(2);

        txtEngS.setText(eng);

        btnShowS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imgViewS.setImageBitmap(bitmap);
                txtJaS.setText(ja);
            }
        });
    }

    private void addControl() {
        btnBackS = (Button) findViewById(R.id.btnBackS);
        btnNextS = (Button) findViewById(R.id.btnNextS);
        btnShowS = (Button) findViewById(R.id.btnShowS);
        btnEndS = (Button) findViewById(R.id.btnEndS);
        imgViewS = (ImageView) findViewById(R.id.imgViewS);
        txtJaS = (TextView) findViewById(R.id.txtJaS);
        txtEngS = (TextView) findViewById(R.id.edtEngU);
    }
    private void setChanged(){
        txtJaS.setText("");
        imgViewS.setImageResource(0);
        readData();
    }
}
