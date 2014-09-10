package br.com.cast.treinamento.app.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.cast.treinamento.app.dao.mapping.ContatoEntity;

public abstract class BaseDAO extends SQLiteOpenHelper {

	protected static final String TAG = "DAO";
	
	private static final String DB_NAME = "TreinamentoAndroid.db";
	private static final int DB_VERSION = 1;

	private Context contexto;
	
	public BaseDAO(Context contexto) {
		super(contexto, DB_NAME, null, DB_VERSION);
		this.contexto = contexto;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			executarScriptAsset(db, "creates.sql");
		} catch (Exception e) {
			db.execSQL(ContatoEntity.CREATE);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			executarScriptAsset(db, "drops.sql");
		} catch (Exception e) {
			db.execSQL(ContatoEntity.DROP);
		}
		this.onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.onUpgrade(db, oldVersion, newVersion);
	}
	
	private void executarScriptAsset(SQLiteDatabase db, String createSQL) throws IOException, SQLException {
		db.beginTransaction();
		try {
			InputStream inputStream = contexto.getAssets().open(createSQL);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String linha;
			while ((linha = reader.readLine()) != null) {
				db.compileStatement(linha).execute();
			}
			db.setTransactionSuccessful();
		} catch (IOException e) {
			Log.e(TAG, "I/O: " + e.getMessage());
			throw e;
		} catch (SQLException e) {
			Log.e(TAG, "SQL: " + e.getMessage());
			throw e;
		} finally {
			db.endTransaction();
		}
	}
}
