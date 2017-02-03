package jp.ac.jec.a16cm0209.english_study;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class UpdateActivity extends AppCompatActivity {
    final String DATABASE_NAME = "DictEJ.sqlite";
    final int RESQUEST_TAKE_PHOTO = 123;
    final int RESQUEST_CHOOSE_PHOTO = 321;
    String key;

    Button btnTakeU, btnUploadU, btnSaveU, btnCanelU;
    EditText edtEngU, edtJaU;
    ImageView imgLoadU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        addControls();
        initUI();
        addEvents();
    }

    private void addEvents() {
        btnUploadU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        btnTakeU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        btnSaveU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        btnCanelU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    private void cancel() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void update() {
        String eng = edtEngU.getText().toString();
        String ja = edtJaU.getText().toString();
        byte[] img = getByteArrayFromImageView(imgLoadU);

        ContentValues contentValues = new ContentValues();
        contentValues.put("eng", eng);
        contentValues.put("ja", ja);
        contentValues.put("img", img);

        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        database.update("dict", contentValues, "eng = ?", new String[]{key});
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private byte[] getByteArrayFromImageView(ImageView imgLoad) {
        BitmapDrawable drawable = (BitmapDrawable) imgLoad.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RESQUEST_TAKE_PHOTO);
    }

    private void choosePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESQUEST_CHOOSE_PHOTO);
    }

    private void initUI() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras!= null){
            key = extras.getString("key");
        }

            SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM dict WHERE eng = ? ", new String[]{key});
            cursor.moveToFirst();
                String english = cursor.getString(0);
                String japan = cursor.getString(1);
                byte[] image = cursor.getBlob(2);

                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imgLoadU.setImageBitmap(bitmap);
                edtEngU.setText(english);
                edtJaU.setText(japan);
    }
    private void addControls() {
        btnTakeU = (Button) findViewById(R.id.btnTakeU);
        btnUploadU = (Button) findViewById(R.id.btnUploadU);
        btnSaveU = (Button) findViewById(R.id.btnSaveU);
        btnCanelU = (Button) findViewById(R.id.btnCanelU);
        edtEngU = (EditText) findViewById(R.id.edtEngU);
        edtJaU = (EditText) findViewById(R.id.edtJaU);
        imgLoadU = (ImageView) findViewById(R.id.imgLoadU);
    }
}
