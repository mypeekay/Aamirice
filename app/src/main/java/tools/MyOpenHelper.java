package tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.peekay.aamirice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String DBName = "MOVIE.DB";
    private static final String CREATE_TABLE = "CREATE TABLE MOVIE(ID INTEGER PRIMARY KEY AUTOINCREMENT,MOVIENAME TEXT,YEAR TEXT,LINK TEXT,INTRO TEXT)";
    private static final String CREATE_MUSICTABLE = "CREATE TABLE MUSIC(ID INTEGER PRIMARY KEY AUTOINCREMENT,MUSICNAME TEXT,LINK TEXT)";
    Context context;
    List<String> music = new ArrayList<>();
    List<String> musiclink = new ArrayList<>();

    public MyOpenHelper(Context context) {
        super(context, DBName, null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_MUSICTABLE);
        insertAllData(db);
        insertAllMusicData(db);
    }

    //加入音乐数据
    private void insertAllMusicData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        for (int i = 0; i < context.getResources().getStringArray(R.array.music).length; i++) {
            values.put("MUSICNAME", context.getResources().getStringArray(R.array.music)[i]);
            values.put("LINK", context.getResources().getStringArray(R.array.musiclink)[i]);
            db.insert("MUSIC", null, values);
            values.clear();
        }
    }

    public Cursor getAllMusic() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM MUSIC", null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MOVIE");
        db.execSQL("DROP TABLE IF EXISTS MUSIC");
        onCreate(db);
    }

    //获取所有数据
    public Cursor getALLMovie() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM MOVIE", null);
        return cursor;
    }

    //加入默认电影数据
    public void insertAllData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        for (int i = 0; i < 43; i++) {
            values.put("MOVIENAME", context.getResources().getStringArray(R.array.movie)[i]);
            values.put("YEAR", context.getResources().getStringArray(R.array.year)[i]);
            values.put("LINK", context.getResources().getStringArray(R.array.link)[i]);
            values.put("INTRO", context.getResources().getStringArray(R.array.intro)[i]);
            db.insert("MOVIE", null, values);
            values.clear();
        }
    }

    //根据电影名查找
    public Cursor getMovie(String moviename) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM MOVIE WHERE MOVIENAME = ?", new String[]{moviename});
        return cursor;
    }

    //相似查找
    public Cursor likeInfo(String s) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("MOVIE", null, "MOVIENAME LIKE ? OR YEAR LIKE ?", new String[]{"%" + s + "%", "%" + s + "%"}, null, null, null);
        //        Cursor cursor=sqLiteDatabase.query("MOVIE",null,"MOVIENAME LIKE ? OR YEAR LIKE ? OR INTRO LIKE ?",new String[]{"%"+s+"%","%"+s+"%","%"+s+"%"},null,null,null);
        return cursor;
    }
}