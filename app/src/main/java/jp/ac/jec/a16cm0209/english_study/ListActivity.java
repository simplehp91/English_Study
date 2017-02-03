package jp.ac.jec.a16cm0209.english_study;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    final String DATABASE_NAME = "DictEJ.sqlite";
    SQLiteDatabase database;

    ListView listView;
    ArrayList<Card> list;
    AdapterCard adapter;
    Button btnBackL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        addControls();
        readData();
    }

    private void addControls() {
        btnBackL = (Button) findViewById(R.id.btnBackL);
        btnBackL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterCard(this, list);
        listView.setAdapter(adapter);
    }

    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM dict ORDER BY eng", null);
        list.clear(); //Tránh trùng dữ liệu
        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String english =  cursor.getString(0);
            String japan = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            list.add(new Card(english, japan, image));
        }
        adapter.notifyDataSetChanged(); //Vẽ lại giao diện
    }
}
