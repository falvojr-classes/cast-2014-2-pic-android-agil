package br.com.cast.treinamento.app.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.cast.treinamento.app.dao.mapping.ContatoEntity;

public abstract class BaseDAO extends SQLiteOpenHelper {

	private static final String DB_NAME = "TreinamentoAndroid.db";
	private static final int DB_VERSION = 1;

	public BaseDAO(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ContatoEntity.CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(ContatoEntity.DROP);
		this.onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.onUpgrade(db, oldVersion, newVersion);
	}
}
