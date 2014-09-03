package br.com.cast.treinamento.app.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.treinamento.app.entity.Contato;

public final class ContatoDAO {

	private static long SEQUENCE = 1;
	private static final List<Contato> REPOSITORIO = new ArrayList<>();
	
	private static final ContatoDAO INSTANCIA = new ContatoDAO();
	
	private ContatoDAO() {
		super();
	}

	public static ContatoDAO getInstancia() {
		return INSTANCIA;
	}

	public List<Contato> listarTodos() {
		return REPOSITORIO;	
	}
	
	public void salvar(Contato contato) {
		if (contato.getId() == null) {
			contato.setId(SEQUENCE++);
			REPOSITORIO.add(contato);
		} else {
			REPOSITORIO.set(REPOSITORIO.indexOf(contato), contato);
		}
	}
}
