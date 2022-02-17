package com.example.pets;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import com.example.pets.data.PetContract.PetEntry;

import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pets.data.PetContract;
import com.example.pets.data.PetDbHelper;
import com.example.pets.data.PetProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /** Database helper that will provide us access to the database */
    private PetDbHelper mDbHelper;
    private Cursor cursor=null;
    private ListView petListView=null;
    private PetCursorAdapter mPetCursorAdapter=null;
    private static final int PET_LOADER=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
            startActivity(intent);
        });
        petListView = (ListView) findViewById(R.id.list_view);
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);
        mPetCursorAdapter=new PetCursorAdapter(this,null);
        petListView.setAdapter(mPetCursorAdapter);
        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
//                Uri.withAppendedId();
//                int catlog=R.layout.activity_catalog;
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                intent.setData(ContentUris.withAppendedId(PetEntry.CONTENT_URI,id));
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
//        mDbHelper = new PetDbHelper(this);
        getLoaderManager().initLoader(PET_LOADER,null,this);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//       displayDatabaseInfo();
//    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
//    private void displayDatabaseInfo() {
//        // Create and/or open a database to read from it
//        SQLiteDatabase db = mDbHelper.getReadableDatabase();
//
//        // Define a projection that specifies which columns from the database
//        // you will actually use after this query.
//        String[] projection = {
//                PetContract.PetEntry._ID,
//                PetContract.PetEntry.COLUMN_PET_NAME,
//                PetContract.PetEntry.COLUMN_PET_BREED,
//                PetContract.PetEntry.COLUMN_PET_GENDER,
//                PetContract.PetEntry.COLUMN_PET_WEIGHT };
//        if(cursor != null) cursor.close();
//
//        // Perform a query on the pets table
//        Cursor cursor = PetProvider.query(PetEntry.CONTENT_URI,
//                PetContract.PetEntry.TABLE_NAME,   // The table to query
//                projection,            // The columns to return
//                null,                  // The columns for the WHERE clause
//                null,                  // The values for the WHERE clause
//                null,                  // Don't group the rows
//                null,                  // Don't filter by row groups
//                null);                   // The sort order
                // Perform a query on the pets table

//         cursor = getContentResolver().query(PetEntry.CONTENT_URI,
//                projection,            // The columns to return
//                null,                  // The columns for the WHERE clause
//                null,                  // The values for the WHERE clause
//                null);                   // The sort order
//        mPetCursorAdapter=new PetCursorAdapter(this,cursor);
//        petListView.setAdapter(mPetCursorAdapter);


//        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

//        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
//            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
//            displayView.append(PetEntry._ID + " - " +
//                    PetEntry.COLUMN_PET_NAME + " - " +
//                    PetEntry.COLUMN_PET_BREED + " - " +
//                   PetEntry.COLUMN_PET_GENDER + " - " +
//                   PetEntry.COLUMN_PET_WEIGHT + "\n");

            // Figure out the index of each column
//            int idColumnIndex = cursor.getColumnIndex(PetContract.PetEntry._ID);
//            int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME);
//            int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED);
//            int genderColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_GENDER);
//            int weightColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_WEIGHT);

//            // Iterate through all the returned rows in the cursor
//            while (cursor.moveToNext()) {
//                // Use that index to extract the String or Int value of the word
//                // at the current row the cursor is on.
//                int currentID = cursor.getInt(idColumnIndex);
//                String currentName = cursor.getString(nameColumnIndex);
//                String currentBreed = cursor.getString(breedColumnIndex);
//                int currentGender = cursor.getInt(genderColumnIndex);
//                int currentWeight = cursor.getInt(weightColumnIndex);
//                // Display the values from each column of the current row in the cursor in the TextView
//                displayView.append(("\n" + currentID + " - " +
//                        currentName + " - " +
//                        currentBreed + " - " +
//                        currentGender + " - " +
//                        currentWeight));
//            }
//        } finally {
//            // Always close the cursor when you're done reading from it. This releases all its
//            // resources and makes it invalid.
//            cursor.close();
//        }
//    }

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertPet() {


        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(PetContract.PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetContract.PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetContract.PetEntry.COLUMN_PET_GENDER, PetContract.PetEntry.GENDER_MALE);
        values.put(PetContract.PetEntry.COLUMN_PET_WEIGHT, 7);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
       Uri newUri = getContentResolver().insert(PetEntry.CONTENT_URI, values);
        // Show a toast message depending on whether or not the insertion was successful
        if (newUri== null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.

        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
//                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                PetContract.PetEntry._ID,
                PetContract.PetEntry.COLUMN_PET_NAME,
                PetContract.PetEntry.COLUMN_PET_BREED,
              };
        return new CursorLoader(this //parent activity context
                ,PetEntry.CONTENT_URI //provider content URI to query
                ,projection  //Column to include in a resulting cursor
                ,null //no selection clause
                ,null //no selection argumnet
                ,null); //default sort order

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mPetCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPetCursorAdapter.swapCursor(null);
    }
    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllPets() {
        int rowsDeleted = getContentResolver().delete(PetEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }
}
