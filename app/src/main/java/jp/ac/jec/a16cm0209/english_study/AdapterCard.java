package jp.ac.jec.a16cm0209.english_study;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by nguyenhiep on 1/30/2017 AD.
 */

public class AdapterCard extends BaseAdapter {
    Activity context;
    ArrayList<Card> list;
    final String DATABASE_NAME = "DictEJ.sqlite";

    public AdapterCard(Activity context, ArrayList<Card> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        //Positon getView
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview_row, null);
        ImageView btnEditList = (ImageView) row.findViewById(R.id.btnEditList);
        ImageView btnDeleteList = (ImageView) row.findViewById(R.id.btnDeleteList);
        TextView txtData = (TextView) row.findViewById(R.id.txtData);

        //Set value
        final Card card = list.get(position);
        txtData.setText(card.eng + " - " + card.ja);

        //Set Image Button Edit
        btnEditList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);

                intent.putExtra("key", card.eng);
                context.startActivity(intent);
            }
        });
        btnDeleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("確認");
                builder.setMessage("削除します？");
                builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(card.eng);
                    }
                });
                builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return row;
    }

    private void delete(String idEng) {
        SQLiteDatabase database  = Database.initDatabase(context, DATABASE_NAME);
        database.delete("dict", "eng = ?", new String[]{idEng});
        Cursor cursor = database.rawQuery("SELECT * FROM dict ORDER BY eng", null);
        list.clear();

        while (cursor.moveToNext()) {
            String eng = cursor.getString(0);
            String ja = cursor.getString(1);
            byte[] img = cursor.getBlob(2);

            list.add(new Card(eng, ja, img));
        }
        notifyDataSetChanged();
    }
}
