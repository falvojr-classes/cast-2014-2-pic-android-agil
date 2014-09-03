package br.com.cast.treinamento.app.service;

import java.util.List;

import br.com.cast.treinamento.app.dao.ContatoDAO;
import br.com.cast.treinamento.app.entity.Contato;

public final class ContatoService {

	private static final ContatoService INSTANCIA = new ContatoService();
	
	private ContatoService() {
		super();
	}

	public static ContatoService getInstancia() {
		return INSTANCIA;
	}

	public List<Contato> listarTodos() {
		return ContatoDAO.getInstancia().listarTodos();	
	}
	
	public void salvar(Contato contato) {
		ContatoDAO.getInstancia().salvar(contato);
	}
}
