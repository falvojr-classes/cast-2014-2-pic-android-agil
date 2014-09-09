package br.com.cast.treinamento.app.dao.mapping;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import br.com.cast.treinamento.app.domain.Contato;

/**
 * Classe responsável por disponibilizar informações úteis à camada de persistência relacionadas à entidade {@link Contato}.
 * 
 * @see <a href="http://developer.android.com/training/basics/data-storage/databases.html">Saving Data in SQL Databases</a>
 * 
 * @author venilton.junior
 */
public final class ContatoEntity {
	
	public static final String TABELA = "tb_contato";
	
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_ENDERECO = "endereco";
    public static final String COLUNA_SITE = "site";
    public static final String COLUNA_TELEFONE = "telefone";
    public static final String COLUNA_RELEVANCIA = "relevancia";
	
    public static final String CREATE = "CREATE TABLE " + TABELA + 
							    		"(" +
								    		COLUNA_ID + " INTEGER PRIMARY KEY," +
								    		COLUNA_NOME + " TEXT UNIQUE NOT NULL," +
								    		COLUNA_ENDERECO + " TEXT NOT NULL," +
								    		COLUNA_SITE + " TEXT NOT NULL," +
								    		COLUNA_TELEFONE + " TEXT NOT NULL," +
								    		COLUNA_RELEVANCIA + " REAL" +
							    		");";

    public static final String DROP = "DROP TABLE IF EXISTS " + TABELA + ";";
    
    public static final String[] COLUNAS = { COLUNA_ID, COLUNA_NOME, COLUNA_ENDERECO, COLUNA_SITE, COLUNA_TELEFONE, COLUNA_RELEVANCIA };
    
	private ContatoEntity() {
		super();
	}

	public static Contato bindContato(Cursor cursor) {
		if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
			Contato contato = new Contato();
			contato.setId(cursor.getLong(cursor.getColumnIndex(COLUNA_ID)));
			contato.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
			contato.setEndereco(cursor.getString(cursor.getColumnIndex(COLUNA_ENDERECO)));
			contato.setSite(cursor.getString(cursor.getColumnIndex(COLUNA_SITE)));
			contato.setTelefone(cursor.getString(cursor.getColumnIndex(COLUNA_TELEFONE)));
			if (!cursor.isNull(cursor.getColumnIndex(COLUNA_RELEVANCIA))) {
				contato.setRelevancia(cursor.getFloat(cursor.getColumnIndex(COLUNA_RELEVANCIA)));
			}
			return contato;
		}
		return null;
	}

	public static List<Contato> bindContatos(Cursor cursor) {
		List<Contato> contatos = new ArrayList<Contato>();
		while (cursor.moveToNext()) {
			Contato contato = bindContato(cursor);
			contatos.add(contato);
		}
		return contatos;
	}
}
