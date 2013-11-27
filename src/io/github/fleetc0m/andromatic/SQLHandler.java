package io.github.fleetc0m.andromatic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHandler extends SQLiteOpenHelper{
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Andromatic.db";
	public static final String TABLE_NAME = "triggers";
	
	public static final String TRIGGER_NAME = "triggername";
	public static final String TRIGGER_ID = "_id";
	public static final String CLASS_NAME = "classname";
	public static final String TRIGGER_RULE = "triggerrule";
	public static final String ACTION_NAME = "actionname";
	public static final String ACTION_RULE = "actionrule";
	
	public static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME
		+ "(" 
		+ TRIGGER_ID + " INTEGER PRIMARY KEY AUTOINCREAMENT,"
		+ TRIGGER_NAME + " TEXT,"
		+ CLASS_NAME + " TEXT,"
		+ TRIGGER_RULE + " TEXT,"
		+ ACTION_NAME + " TEXT,"
		+ ACTION_RULE + " TEXT"
		+ ");";
	
	public SQLHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		// TODO Auto-generated method stub
		
	}

}
