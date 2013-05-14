package nl.mprog.apps.othello.persistence;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReplaysHandler extends SQLiteOpenHelper {
	
	private static final int VERSION = 1;
	private static final String NAME = "replays";

	public ReplaysHandler(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String create = "CREATE TABLE "+ NAME +
				" (id INTEGER PRIMARY KEY," +
				" name VARCHAR(255)," +
				" moves TEXT)";
		db.execSQL(create);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+ NAME);
		onCreate(db);
	}
	
	public void addReplay(String name, String moves) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("moves", moves);
		
		db.insert(NAME, null, values);
		db.close();
	}
	
	public Map<String, String> getReplays() {
		Map<String, String> map = new HashMap<String, String>();
		
		String query = "SELECT * FROM "+ NAME;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		if (cursor.moveToFirst()) {
			do {
				map.put(cursor.getString(1), cursor.getString(2));
			} while (cursor.moveToNext());
		}
		
		return map;
	}

}
