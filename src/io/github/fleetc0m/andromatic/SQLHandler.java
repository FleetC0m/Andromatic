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
	private static final String INTEGER_TYPE = " INTEGER";
	
	public static final String RULE_NAME = "rulename";
	public static final String TRIGGER_ID = "_id";
	public static final String TRIGGER_CLASS_NAME = "classname";
	public static final String TRIGGER_RULE = "triggerrule";
	public static final String ACTION_CLASS_NAME = "actionname";
	public static final String ACTION_RULE = "actionrule";
	public static final String INTENT_TYPE = "intenttype";
	public static final String POLLING_TYPE = "pollingbool";
	public static final String RULE_TIMESTAMP = "ruletimestamp";
	
	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
		+ " (" 
		+ TRIGGER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
		+ RULE_NAME + TEXT_TYPE + ","
		+ TRIGGER_CLASS_NAME + TEXT_TYPE + ","
		+ TRIGGER_RULE + TEXT_TYPE + ","
		+ ACTION_CLASS_NAME + TEXT_TYPE + ","
		+ ACTION_RULE + TEXT_TYPE + ","
		+ INTENT_TYPE + TEXT_TYPE + ","
		+ POLLING_TYPE + INTEGER_TYPE + ","
		+ RULE_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
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
	public long addRule(Bundle bundle){
		if(bundle == null) 
			return -1;
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(RULE_NAME, bundle.getString(RULE_NAME));
		values.put(TRIGGER_CLASS_NAME, bundle.getString(TRIGGER_CLASS_NAME));
		values.put(TRIGGER_RULE, bundle.getString(TRIGGER_RULE));
		values.put(ACTION_CLASS_NAME, bundle.getString(ACTION_CLASS_NAME));
		values.put(ACTION_RULE, bundle.getString(ACTION_RULE));
		values.put(INTENT_TYPE, bundle.getString(INTENT_TYPE));
		if(bundle.getBoolean(POLLING_TYPE) == true){
			values.put(POLLING_TYPE, 1);
		} else{
			values.put(POLLING_TYPE, 0);
		}
		long rowid = db.insert(TABLE_NAME, null, values);
		Log.d(LOG_TAG, "A trigger added to the database: " + bundle.getString(RULE_NAME));

		return rowid;
	}
	
	/**
	 * Get a trigger bundle by row id
	 * @param rowid
	 * @return Trigger bundle or null
	 */
	public Bundle getRuleById(long rowid){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_NAME, new String[] { TRIGGER_ID, RULE_NAME, TRIGGER_CLASS_NAME, TRIGGER_RULE, ACTION_CLASS_NAME, ACTION_RULE, INTENT_TYPE, POLLING_TYPE, RULE_TIMESTAMP}, TRIGGER_ID + " = ?", new String[]{String.valueOf(rowid)}, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}else{
			return null;
		}
		
		Bundle bundle = new Bundle();
		
		bundle.putLong(TRIGGER_ID, cursor.getLong(0));
		bundle.putString(RULE_NAME, cursor.getString(1));
		bundle.putString(TRIGGER_CLASS_NAME, cursor.getString(2));
		bundle.putString(TRIGGER_RULE, cursor.getString(3));
		bundle.putString(ACTION_CLASS_NAME, cursor.getString(4));
		bundle.putString(ACTION_RULE, cursor.getString(5));
		bundle.putString(INTENT_TYPE, cursor.getString(6));
		
		if(cursor.getInt(7) == 1){
			bundle.putBoolean(POLLING_TYPE, true);
		}else{
			bundle.putBoolean(POLLING_TYPE, false);
		}
		
		bundle.putString(RULE_TIMESTAMP, cursor.getString(8));

		Log.d(LOG_TAG, "Trigger Id: " + rowid + "Trigger Name: " + cursor.getString(1));
		return bundle;
	}
	
	/**
	 * Return a trigger by trigger name
	 * @param triggerName
	 * @return bundle
	 */
	public Bundle getRuleByName(String ruleName){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_NAME, new String[] { TRIGGER_ID, RULE_NAME, TRIGGER_CLASS_NAME, TRIGGER_RULE, ACTION_CLASS_NAME, ACTION_RULE, INTENT_TYPE, POLLING_TYPE, RULE_TIMESTAMP}, RULE_NAME + "=?", new String[]{ruleName}, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		
		Bundle bundle = new Bundle();
		
		bundle.putLong(TRIGGER_ID, cursor.getLong(0));
		bundle.putString(RULE_NAME, cursor.getString(1));
		bundle.putString(TRIGGER_CLASS_NAME, cursor.getString(2));
		bundle.putString(TRIGGER_RULE, cursor.getString(3));
		bundle.putString(ACTION_CLASS_NAME, cursor.getString(4));
		bundle.putString(ACTION_RULE, cursor.getString(5));
		bundle.putString(INTENT_TYPE, cursor.getString(6));

		if(cursor.getInt(7) == 1){
			bundle.putBoolean(POLLING_TYPE, true);
		}else{
			bundle.putBoolean(POLLING_TYPE, false);
		}
		
		bundle.putString(RULE_TIMESTAMP, cursor.getString(8));
		
		return bundle;
	}
	
	/**
	 * Get a list of Bundle triggers
	 * @return Bundle List
	 */
	public List<Bundle> getAllRules(){
		List<Bundle> triggerList = new ArrayList<Bundle>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
            	Bundle bundle = new Bundle();
            	
        		bundle.putLong(TRIGGER_ID, cursor.getLong(0));
        		bundle.putString(RULE_NAME, cursor.getString(1));
        		bundle.putString(TRIGGER_CLASS_NAME, cursor.getString(2));
        		bundle.putString(TRIGGER_RULE, cursor.getString(3));
        		bundle.putString(ACTION_CLASS_NAME, cursor.getString(4));
        		bundle.putString(ACTION_RULE, cursor.getString(5));
        		bundle.putString(INTENT_TYPE, cursor.getString(6));
        		
        		if(cursor.getInt(7) == 1){
        			bundle.putBoolean(POLLING_TYPE, true);
        		}else{
        			bundle.putBoolean(POLLING_TYPE, false);
        		}
        		
        		bundle.putString(RULE_TIMESTAMP, cursor.getString(8));
        		
        		triggerList.add(bundle);
            } while (cursor.moveToNext());
        }

        return triggerList;
		
	}
	
	/**
	 * Update a trigger by row id
	 * @param bundle
	 * @return row id
	 */
	public long updateRuleById(Bundle bundle){
		if(bundle == null){
			return -1;
		}
        SQLiteDatabase db = this.getWritableDatabase();
        
		ContentValues values = new ContentValues();
		
		if(bundle.containsKey(RULE_NAME)){
			values.put(RULE_NAME, bundle.getString(RULE_NAME));
		}
		if(bundle.containsKey(TRIGGER_CLASS_NAME)){
			values.put(TRIGGER_CLASS_NAME, bundle.getString(TRIGGER_CLASS_NAME));
		}
		if(bundle.containsKey(TRIGGER_RULE)){
			values.put(TRIGGER_RULE, bundle.getString(TRIGGER_RULE));
		}
		if(bundle.containsKey(ACTION_CLASS_NAME)){
			values.put(ACTION_CLASS_NAME, bundle.getString(ACTION_CLASS_NAME));
		}
		if(bundle.containsKey(ACTION_RULE)){
			values.put(ACTION_RULE, bundle.getString(ACTION_RULE));
		}
		if(bundle.containsKey(INTENT_TYPE)){
			values.put(INTENT_TYPE, bundle.getString(INTENT_TYPE));
		}
		
		if(bundle.containsKey(POLLING_TYPE)){
			if(bundle.getBoolean(POLLING_TYPE) == true){
				values.put(POLLING_TYPE, 1);
			} else{
				values.put(POLLING_TYPE, 0);
			}
		}
		
		long rowid = db.update(TABLE_NAME, values, TRIGGER_ID + " = ?",new String[] { String.valueOf(bundle.getLong(TRIGGER_ID)) });
		Log.d(LOG_TAG, "Updated a trigger in the database: " + bundle.getString(RULE_NAME));

        return rowid;
        
	}
	
	/**
	 * Get a count of triggers in the database
	 * @return count of triggers
	 */
    public int getRuleCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        Log.d(LOG_TAG, "Get trigger count: " + count);

        return count;
    }
    
    /**
     * Delete a trigger in the database
     * @param rowid
     */
    public void deleteRuleById(long rowid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, TRIGGER_ID + " = ?", new String[] {String.valueOf(rowid)});

    }
    
    public void testDatabase(){
    	//this.onUpgrade(this.getWritableDatabase(), 1, 2);
    	Bundle bundle = new Bundle();
		
		bundle.putString(RULE_NAME, "TEST_TRIGGER");
		bundle.putString(TRIGGER_CLASS_NAME, "TEST_CLASS");
		bundle.putString(TRIGGER_RULE, "TRIGGER_RULE");
		bundle.putString(ACTION_CLASS_NAME, "ACTION_NAME");
		bundle.putString(ACTION_RULE, "ACTION_RULE");
		bundle.putString(INTENT_TYPE, "INTENT_TYPE");
		bundle.putBoolean(POLLING_TYPE, false);
    	long rowid = this.addRule(bundle);
    	
    	Bundle returnBundle = this.getRuleById(rowid);
    	int triggerCount = this.getRuleCount();
    	Log.d(LOG_TAG,"Test Database: " + returnBundle.getString(RULE_TIMESTAMP) + "Count: " + triggerCount);
    	Bundle bundle2 = new Bundle();
    	bundle2.putString(RULE_NAME, "NEW_RULE_NAME");
    	bundle2.putLong(TRIGGER_ID, 5);
    	this.updateRuleById(bundle2);
    	List<Bundle> arrayList = this.getAllRules();
    	
    	for(int i = 0; i < arrayList.size(); i++){
    		
        	Log.d(LOG_TAG,"Test Database: " + arrayList.get(i).getString(RULE_TIMESTAMP));
    	}
    }
	

}
