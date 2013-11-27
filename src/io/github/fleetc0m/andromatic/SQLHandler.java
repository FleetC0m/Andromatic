package io.github.fleetc0m.andromatic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class SQLHandler extends SQLiteOpenHelper{
	public static final String LOG_TAG = "SQLHandler";
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Andromatic.db";
	public static final String TABLE_NAME = "triggers";
	public static final String TEXT_TYPE = " TEXT";
	public static final String BOOLEAN_TYPE = " BOOLEAN";
	
	public static final String TRIGGER_NAME = "triggername";
	public static final String TRIGGER_ID = "_id";
	public static final String CLASS_NAME = "classname";
	public static final String TRIGGER_RULE = "triggerrule";
	public static final String ACTION_NAME = "actionname";
	public static final String ACTION_RULE = "actionrule";
	public static final String INTENT_TYPE = "intenttype";
	
	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
		+ " (" 
		+ TRIGGER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
		+ TRIGGER_NAME + TEXT_TYPE + ","
		+ CLASS_NAME + TEXT_TYPE + ","
		+ TRIGGER_RULE + TEXT_TYPE + ","
		+ ACTION_NAME + TEXT_TYPE + ","
		+ ACTION_RULE + TEXT_TYPE + ","
		+ INTENT_TYPE + BOOLEAN_TYPE
		+ " )";
	
	public SQLHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
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
		values.put(INTENT_TYPE, bundle.getBoolean(INTENT_TYPE));
		long rowid = db.insert(TABLE_NAME, null, values);
		db.close();
		return rowid;
	}
	
	public Bundle getTriggerById(long rowid){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_NAME, new String[] { TRIGGER_ID, TRIGGER_NAME, CLASS_NAME, TRIGGER_RULE, ACTION_NAME, ACTION_RULE, INTENT_TYPE}, TRIGGER_ID + "=?", new String[]{String.valueOf(rowid)}, null, null, null);
		
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
	

}
