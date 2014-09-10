package br.com.cast.treinamento.app.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.cast.treinamento.app.dao.mapping.ContatoEntity;
import br.com.cast.treinamento.app.domain.Contato;

/**
 * Classe responsável pelo acesso ao dados da entidade {@link Contato}.
 * 
 * @author venilton.junior
 */
public final class ContatoDAO extends BaseDAO {
	
	private static ContatoDAO INSTANCIA;
	
	private ContatoDAO(Context contexto) {
		super(contexto);
	}

	public static ContatoDAO getInstancia(Context contexto) {
		if (INSTANCIA == null) {
			INSTANCIA = new ContatoDAO(contexto);
		}
		return INSTANCIA;
	}

	public List<Contato> listarTodos() throws SQLException {
		SQLiteDatabase db = super.getReadableDatabase();
		try {
			// Opação 1: método db.query:
			String orderBy = String.format("UPPER(%s)", ContatoEntity.COLUNA_NOME);
			Cursor cursorQuery = db.query(ContatoEntity.TABELA, ContatoEntity.COLUNAS , null, null, null, null, orderBy);
			
			// Opação 2: método db.rawQuery (Inplicitamente utilizado pelo db.query):
//			String sqlTodos = String.format("SELECT * FROM %s ORDER BY UPPER(%s) DESC", TbContato.TABELA, TbContato.COLUNA_NOME);
//			Cursor cursorRawQuery = db.rawQuery(sqlTodos, null);
			
			return ContatoEntity.bindContatos(cursorQuery);
		} catch (SQLException excecao) {
			Log.e(TAG, excecao.getMessage());
			throw excecao;
		} finally {
			super.close();
		}
	}
	
	public void salvar(Contato contato) throws SQLException {
		SQLiteDatabase db = super.getWritableDatabase();
		try {
			db.beginTransaction();
			ContentValues values = new ContentValues();
			values.put(ContatoEntity.COLUNA_NOME, contato.getNome());
			values.put(ContatoEntity.COLUNA_ENDERECO, contato.getEndereco());
			values.put(ContatoEntity.COLUNA_SITE, contato.getSite());
			values.put(ContatoEntity.COLUNA_TELEFONE, contato.getTelefone());
			if (contato.getRelevancia().floatValue() == 0) {
				values.putNull(ContatoEntity.COLUNA_RELEVANCIA);
			} else {
				values.put(ContatoEntity.COLUNA_RELEVANCIA, contato.getRelevancia());
			}
			
			if (contato.getId() == null) {
				db.insert(ContatoEntity.TABELA, null, values);
			} else {
				String clausulaWhere = ContatoEntity.COLUNA_ID + " = ?";
				String[] argumentosWhere = new String[] { contato.getId().toString() };	
				db.update(ContatoEntity.TABELA, values, clausulaWhere, argumentosWhere);
			}
			db.setTransactionSuccessful();
		} catch (SQLException excecao) {
			Log.e(TAG, excecao.getMessage());
			throw excecao;
		} finally {
			db.endTransaction();
			super.close();
		}
	}

	public void excluir(Contato contato) {
		SQLiteDatabase db = super.getWritableDatabase();
		try {
			db.beginTransaction();
			
			// Opação 1: método db.delete
			String clausulaWhere = ContatoEntity.COLUNA_ID + " = ?";
			String[] argumentosWhere = new String[] { contato.getId().toString() };
			db.delete(ContatoEntity.TABELA, clausulaWhere, argumentosWhere);
			
			// Opação 2: método db.compileStatement
//			String sqlDelete = String.format("DELETE FROM %s WHERE %s = ?", TbContato.TABELA, TbContato.COLUNA_ID);
//			SQLiteStatement statement = db.compileStatement(sqlDelete);
//			statement.bindLong(1, contato.getId());
//			statement.execute();
//			statement.close();
			
			db.setTransactionSuccessful();
		} catch (SQLException excecao) {
			Log.e(TAG, excecao.getMessage());
			throw excecao;
		} finally {
			db.endTransaction();
			super.close();
		}
	}

	public List<Contato> listarPorFiltro(Contato filtro) {
		SQLiteDatabase db = super.getReadableDatabase();
		
		List<String> argumentosWhere = new ArrayList<>();
		String clausulasWhere = prepararWhere(filtro, argumentosWhere);
		
		String orderBy = String.format("UPPER(%s)", ContatoEntity.COLUNA_NOME);	
		Cursor cursorQuery = db.query(ContatoEntity.TABELA, ContatoEntity.COLUNAS , clausulasWhere, argumentosWhere.toArray(new String[0]), null, null, orderBy);
		return ContatoEntity.bindContatos(cursorQuery);
	}
	
	public long contarPorFiltro(Contato filtro) {
		SQLiteDatabase db = super.getReadableDatabase();
		
		List<String> argumentosWhere = new ArrayList<>();
		String clausulasWhere = prepararWhere(filtro, argumentosWhere);

		String sqlCount = String.format("SELECT COUNT(*) FROM %s %s", ContatoEntity.TABELA, "".equals(clausulasWhere) ? "" : ("WHERE " + clausulasWhere));
		Cursor cursorQuery = db.rawQuery(sqlCount, argumentosWhere.toArray(new String[0]));
		cursorQuery.moveToFirst();
		return cursorQuery.getLong(0);
	}

	private String prepararWhere(Contato filtro, List<String> argumentosWhere) {
		String clausulasWhere = "";
		if (!"".equals(filtro.getNome())) {
			clausulasWhere += "nome LIKE ?";
			argumentosWhere.add("%" + filtro.getNome() + "%");
		}
		if (!"".equals(filtro.getTelefone())) {
			clausulasWhere += "".equals(clausulasWhere) ? "telefone LIKE ?" : " AND telefone LIKE ?";
			argumentosWhere.add("%" + filtro.getTelefone() + "%");
		}
		return clausulasWhere;
	}
}
