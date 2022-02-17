package com.example.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.pets.data.PetContract.PetEntry;

/**
 * {@link ContentProvider} for Pets app.
 */
public class PetProvider extends ContentProvider {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = PetProvider.class.getSimpleName();
    //Database helper object
    private PetDbHelper mDbHelper;
    /**
     * URI matcher code for the content URI for the pets table
     */
    private static final int PETS = 100;

    /**
     * URI matcher code for the content URI for a single pet in the pets table
     */
    private static final int PET_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // TODO: Add 2 content URIs to URI matcher
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS, PETS);
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS + "/#", PET_ID);
    }

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        /*
         TODO: Create and initialize a PetDbHelper object to gain access to the pets database.
         Make sure the variable is a global variable, so it can be referenced from other
         ContentProvider methods.
        */
        mDbHelper = new PetDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                /* TODO: ACT ON PETS TABLE */
                cursor = database.query(PetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PET_ID:
                /* TODO: ACT ON SINGLE PET */
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(PetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                /* TODO: NO MATCH FOUND */
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);

        }
        //set notification uri to cursor
        //so we know  what content uri he cursor as created for
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                /* TODO: ACT ON PETS TABLE */
                return insertPet(uri, contentValues);
            default:
                /* TODO: NO MATCH FOUND */
                throw new IllegalArgumentException("Insertion is not supported for " + uri);

        }

    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case PET_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case PETS:

                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
               // If 1 or more rows were deleted, then notify all listeners that the data at the
                // given URI has changed
                if (rowsDeleted != 0)
                    getContext().getContentResolver().notifyChange(uri, null);
                return rowsDeleted;
            case PET_ID:
                // Delete a single row given by the ID in the URI
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
                // If 1 or more rows were deleted, then notify all listeners that the data at the
                // given URI has changed
                if (rowsDeleted != 0)
                    getContext().getContentResolver().notifyChange(uri, null);
                return rowsDeleted;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return PetEntry.CONTENT_LIST_TYPE;
            case PET_ID:
                return PetEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // TODO: Update the selected pets in the pets database table with the given ContentValues
        if (values.containsKey(PetEntry.COLUMN_PET_NAME)) {
            String name = values.getAsString(PetEntry.COLUMN_PET_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }

        // If the {@link PetEntry#COLUMN_PET_GENDER} key is present,
        // check that the gender value is valid.
        if (values.containsKey(PetEntry.COLUMN_PET_GENDER)) {
            Integer gender = values.getAsInteger(PetEntry.COLUMN_PET_GENDER);
            if (gender == null || !PetEntry.isValidGender(gender)) {
                throw new IllegalArgumentException("Pet requires valid gender");
            }
        }

        // If the {@link PetEntry#COLUMN_PET_WEIGHT} key is present,
        // check that the weight value is valid.
        if (values.containsKey(PetEntry.COLUMN_PET_WEIGHT)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer weight = values.getAsInteger(PetEntry.COLUMN_PET_WEIGHT);
            if (weight != null && weight < 0) {
                throw new IllegalArgumentException("Pet requires valid weight");
            }
        }
        if (values.size() == 0) {
            return 0;
        }
        // TODO: Return the number of rows that were affected
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(PetEntry.TABLE_NAME, values, selection, selectionArgs);


        if (rowsUpdated != 0) {
            //notify all listeners that data change for pet content uri
            getContext().getContentResolver().notifyChange(uri, null);
        }


//        getContext().getContentResolver().notifyChange(uri,null);
        // Returns the number of database rows affected by the update statement
        return rowsUpdated;
    }

    private Uri insertPet(Uri uri, ContentValues values) {
        //TODO: Insert a New pet into pets Database table with given content values
        //Once we know the of the new row in the table return the new URI;


        Integer gender = values.getAsInteger(PetEntry.COLUMN_PET_GENDER);
        String name = values.getAsString(PetEntry.COLUMN_PET_NAME);
        Integer weight = values.getAsInteger(PetEntry.COLUMN_PET_WEIGHT);
        if (name == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }
        if (gender == null || !PetEntry.isValidGender(gender)) {
            throw new IllegalArgumentException("Pet requires valid gender");
        }
        if (weight != null && weight < 0) {
            throw new IllegalArgumentException("Pet requires valid weight");
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(PetEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        //notify all listeners that data change for pet content uri
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }
}