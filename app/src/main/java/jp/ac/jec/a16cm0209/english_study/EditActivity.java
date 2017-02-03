package jp.ac.jec.a16cm0209.english_study;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditActivity extends AppCompatActivity {
    final String DATABASE_NAME = "DictEJ.sqlite";
    final int RESQUEST_TAKE_PHOTO = 123;
    final int RESQUEST_CHOOSE_PHOTO = 321;

    Button btnTakeE, btnUploadE, btnSaveE, btnCanelE;
    EditText edtEngE, edtJaE;
    ImageView imgLoadE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnUploadE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        btnTakeE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        btnSaveE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        btnCanelE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    private void cancel() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void insert() {
        String eng = edtEngE.getText().toString();
        String ja = edtJaE.getText().toString();
        byte[] img = getByteArrayFromImageView(imgLoadE);
            ContentValues contentValues = new ContentValues();
            contentValues.put("eng", eng);
            contentValues.put("ja", ja);
            contentValues.put("img", img);

            SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
            database.insert("dict", null, contentValues);
            Intent intent = new Intent(this, EditActivity.class);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == RESQUEST_CHOOSE_PHOTO){
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgLoadE.setImageBitmap(bitmap);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            } else  if (requestCode == RESQUEST_TAKE_PHOTO){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgLoadE.setImageBitmap(bitmap);
            }
        }
    }

    private void addControls() {
        btnTakeE = (Button) findViewById(R.id.btnTakeE);
        btnUploadE = (Button) findViewById(R.id.btnUploadE);
        btnSaveE = (Button) findViewById(R.id.btnSaveE);
        btnCanelE = (Button) findViewById(R.id.btnCanelE);
        edtEngE = (EditText) findViewById(R.id.edtEngE);
        edtJaE = (EditText) findViewById(R.id.edtJaE);
        imgLoadE = (ImageView) findViewById(R.id.imgLoadE);
    }
}
