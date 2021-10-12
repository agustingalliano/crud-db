package com.example.crud_bbdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnInsert,btnUpdate,btnDelete,btnSearch;
    private EditText textId,textName,textSurname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.put_button);
        btnDelete = findViewById(R.id.delete_button);
        btnUpdate = findViewById(R.id.update_button);
        btnSearch = findViewById(R.id.search_button);

        textId = findViewById(R.id.id);
        textName = findViewById(R.id.name);
        textSurname = findViewById(R.id.idSurname);

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gets the data repository in write mode
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(DbStructure.FeedEntry.COLUMN_NAME, textId.getText().toString());
                values.put(DbStructure.FeedEntry.COLUMN_NAME2, textName.getText().toString());
                values.put(DbStructure.FeedEntry.COLUMN_NAME3, textSurname.getText().toString());

                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(DbStructure.FeedEntry.TABLE_NAME, null, values);
                Toast.makeText(getApplicationContext(),"Register saved successfully with identifier: "+newRowId, Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // New value for one column
                ContentValues values = new ContentValues();
                values.put(DbStructure.FeedEntry.COLUMN_NAME2, textName.getText().toString());
                values.put(DbStructure.FeedEntry.COLUMN_NAME3, textSurname.getText().toString());

                // Which row to update, based on the title
                String selection = DbStructure.FeedEntry.COLUMN_NAME + " LIKE ?";
                String[] selectionArgs = { textId.getText().toString() };

                int count = db.update(
                        DbStructure.FeedEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);

                Toast.makeText(getApplicationContext(),"Register updated successfully", Toast.LENGTH_SHORT).show();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Define 'where' part of query.
                String selection = DbStructure.FeedEntry.COLUMN_NAME + " LIKE ?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = { textId.getText().toString() };
                // Issue SQL statement.
                int deletedRows = db.delete(DbStructure.FeedEntry.TABLE_NAME, selection, selectionArgs);
                Toast.makeText(getApplicationContext(),"Register deleted successfully with identifier: "+textId.getText().toString(), Toast.LENGTH_SHORT).show();
                textId.setText("");
                textName.setText("");
                textSurname.setText("");

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                // Define a projection that specifies which columns from the database
                // you will actually use after this query.
                String[] projection = {
                        DbStructure.FeedEntry.COLUMN_NAME2,
                        DbStructure.FeedEntry.COLUMN_NAME3
                };

                // Filter results WHERE "id" = 'textId.getText().toString()'
                String selection = DbStructure.FeedEntry.COLUMN_NAME + " = ?";
                String[] selectionArgs = { textId.getText().toString() };

                // How you want the results sorted in the resulting Cursor
//                String sortOrder =
//                        DbStructure.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

                Cursor cursor = db.query(
                        DbStructure.FeedEntry.TABLE_NAME,   // The table to query
                        projection,             // The array of columns to return (pass null to get all)
                        selection,              // The columns for the WHERE clause
                        selectionArgs,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        null               // The sort order
                );

                cursor.moveToFirst();

                if (cursor.getCount()>0) {
                    textName.setText(cursor.getString(0));
                    textSurname.setText(cursor.getString(1));
                } else {
                    Toast.makeText(getApplicationContext(),"Register with id: "+textId.getText().toString()+" does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}