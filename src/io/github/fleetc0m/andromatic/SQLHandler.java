package io.github.fleetc0m.andromatic;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class SQLHandler extends SQLiteOpenHelper{
	private static final String LOG_TAG = "SQLHandler";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Andromatic.db";
	private static final String TABLE_NAME = "triggers";
	private static final String TEXT_TYPE = " TEXT";
	
	public static final String TRIGGER_NAME = "triggername";
	public static final String TRIGGER_ID = "_id";
	public static final String CLASS_NAME = "classname";
	public static final String TRIGGER_RULE = "triggerrule";
	public static final String ACTION_NAME = "actionname";
	public static final String ACTION_RULE = "actionrule";
	public static final String INTENT_TYPE = "intenttype";
	
	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
		+ " (" 
		+ TRIGGER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
		+ TRIGGER_NAME + TEXT_TYPE + ","
		+ CLASS_NAME + TEXT_TYPE + ","
		+ TRIGGER_RULE + TEXT_TYPE + ","
		+ ACTION_NAME + TEXT_TYPE + ","
		+ ACTION_RULE + TEXT_TYPE + ","
		+ INTENT_TYPE + TEXT_TYPE
		+ " )";
	
	public SQLHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		Log.d(LOG_TAG, "Created a trigger table in the database");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		Log.d(LOG_TAG, "Upgrade from version " + oldversion + " to " + newversion + ", Previous database will be deleted.");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
	}

	/**
	 *  Add a trigger information to the database
	 * @param bundle
	 * @return trigger id
	 */
	public long addTrigger(Bundle bundle){
		if(bundle == null) 
			return -1;
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(TRIGGER_NAME, bundle.getString(TRIGGER_NAME));
		values.put(CLASS_NAME, bundle.getString(CLASS_NAME));
		values.put(TRIGGER_RULE, bundle.getString(TRIGGER_RULE));
		values.put(ACTION_NAME, bundle.getString(ACTION_NAME));
		values.put(ACTION_RULE, bundle.getString(ACTION_RULE));
		values.put(INTENT_TYPE, bundle.getString(INTENT_TYPE));
		long rowid = db.insert(TABLE_NAME, null, values);
		Log.d(LOG_TAG, "A trigger added to the database: " + bundle.getString(TRIGGER_NAME));
		db.close();
		return rowid;
	}
	
	/**
	 * Get a trigger bundle by row id
	 * @param rowid
	 * @return Trigger bundle or null
	 */
	public Bundle getTriggerById(long rowid){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_NAME, new String[] { TRIGGER_ID, TRIGGER_NAME, CLASS_NAME, TRIGGER_RULE, ACTION_NAME, ACTION_RULE, INTENT_TYPE}, TRIGGER_ID + " = ?", new String[]{String.valueOf(rowid)}, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}else{
			return null;
		}
		
		Bundle bundle = new Bundle();
		
		bundle.putLong(TRIGGER_ID, cursor.getLong(0));
		bundle.putString(TRIGGER_NAME, cursor.getString(1));
		bundle.putString(CLASS_NAME, cursor.getString(2));
		bundle.putString(TRIGGER_RULE, cursor.getString(3));
		bundle.putString(ACTION_NAME, cursor.getString(4));
		bundle.putString(ACTION_RULE, cursor.getString(5));
		bundle.putString(INTENT_TYPE, cursor.getString(6));
		db.close();
		Log.d(LOG_TAG, "Trigger Id: " + rowid + "Trigger Name: " + cursor.getString(1));
		return bundle;
	}
	
	/**
	 * Return a trigger by trigger name
	 * @param triggerName
	 * @return bundle
	 */
	public Bundle getTriggerByName(String triggerName){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_NAME, new String[] { TRIGGER_ID, TRIGGER_NAME, CLASS_NAME, TRIGGER_RULE, ACTION_NAME, ACTION_RULE, INTENT_TYPE}, TRIGGER_NAME + "=?", new String[]{triggerName}, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		
		Bundle bundle = new Bundle();
		
		bundle.putLong(TRIGGER_ID, cursor.getLong(0));
		bundle.putString(TRIGGER_NAME, cursor.getString(1));
		bundle.putString(CLASS_NAME, cursor.getString(2));
		bundle.putString(TRIGGER_RULE, cursor.getString(3));
		bundle.putString(ACTION_NAME, cursor.getString(4));
		bundle.putString(ACTION_RULE, cursor.getString(5));
		bundle.putString(INTENT_TYPE, cursor.getString(6));
		db.close();
		return bundle;
	}
	
	/**
	 * Get a list of Bundle triggers
	 * @return Bundle List
	 */
	public List<Bundle> getAllTriggers(){
		List<Bundle> triggerList = new ArrayList<Bundle>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
            	Bundle bundle = new Bundle();
            	
        		bundle.putLong(TRIGGER_ID, cursor.getLong(0));
        		bundle.putString(TRIGGER_NAME, cursor.getString(1));
        		bundle.putString(CLASS_NAME, cursor.getString(2));
        		bundle.putString(TRIGGER_RULE, cursor.getString(3));
        		bundle.putString(ACTION_NAME, cursor.getString(4));
        		bundle.putString(ACTION_RULE, cursor.getString(5));
        		bundle.putString(INTENT_TYPE, cursor.getString(6));
        		
        		triggerList.add(bundle);
            } while (cursor.moveToNext());
        }
        db.close();
        return triggerList;
		
	}
	
	/**
	 * Update a trigger by row id
	 * @param bundle
	 * @return row id
	 */
	public long updateTriggerById(Bundle bundle){
		if(bundle == null){
			return -1;
		}
        SQLiteDatabase db = this.getWritableDatabase();
        
		ContentValues values = new ContentValues();
		
		if(bundle.containsKey(TRIGGER_NAME)){
			values.put(TRIGGER_NAME, bundle.getString(TRIGGER_NAME));
		}
		if(bundle.containsKey(CLASS_NAME)){
			values.put(CLASS_NAME, bundle.getString(CLASS_NAME));
		}
		if(bundle.containsKey(TRIGGER_RULE)){
			values.put(TRIGGER_RULE, bundle.getString(TRIGGER_RULE));
		}
		if(bundle.containsKey(ACTION_NAME)){
			values.put(ACTION_NAME, bundle.getString(ACTION_NAME));
		}
		if(bundle.containsKey(ACTION_RULE)){
			values.put(ACTION_RULE, bundle.getString(ACTION_RULE));
		}
		if(bundle.containsKey(INTENT_TYPE)){
			values.put(INTENT_TYPE, bundle.getString(INTENT_TYPE));
		}
		
		long rowid = db.update(TABLE_NAME, values, TRIGGER_ID + " = ?",new String[] { String.valueOf(bundle.getLong(TRIGGER_ID)) });
		Log.d(LOG_TAG, "Updated a trigger in the database: " + bundle.getString(TRIGGER_NAME));
		db.close();
        return rowid;
        
	}
	
	/**
	 * Get a count of triggers in the database
	 * @return count of triggers
	 */
    public int getTriggersCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        Log.d(LOG_TAG, "Get trigger count: " + count);
        db.close();
        return count;
    }
    
    /**
     * Delete a trigger in the database
     * @param rowid
     */
    public void deleteTriggerById(long rowid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, TRIGGER_ID + " = ?", new String[] {String.valueOf(rowid)});
        db.close();
    }
    
    public void testDatabase(){
    	
    	Bundle bundle = new Bundle();
		
		bundle.putString(TRIGGER_NAME, "TEST_TRIGGER");
		bundle.putString(CLASS_NAME, "TEST_CLASS");
		bundle.putString(TRIGGER_RULE, "TRIGGER_RULE");
		bundle.putString(ACTION_NAME, "ACTION_NAME");
		bundle.putString(ACTION_RULE, "ACTION_RULE");
		bundle.putString(INTENT_TYPE, "INTENT_TYPE");
    	long rowid = this.addTrigger(bundle);
    	
    	Bundle returnBundle = this.getTriggerById(rowid);
    	int triggerCount = this.getTriggersCount();
    	Log.d(LOG_TAG,"Test Database: " + returnBundle.getString(ACTION_RULE) + "Count: " + triggerCount);
    	
    	List<Bundle> arrayList = this.getAllTriggers();
    	
    	for(int i = 0; i < arrayList.size(); i++){
    		
        	Log.d(LOG_TAG,"Test Database: " + arrayList.get(i).getString(ACTION_RULE));
    	}
    }
	

}
