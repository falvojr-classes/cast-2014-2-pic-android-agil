package br.com.cast.treinamento.app.service;

import java.util.List;

import android.text.TextUtils;
import br.com.cast.treinamento.app.R;
import br.com.cast.treinamento.app.dao.ContatoDAO;
import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.domain.exception.ExcecaoNegocio;

/**
 * Classe que respresenta a camada de serviço da entidade {@link Contato}.
 * 
 * @author venilton.junior
 */
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
	
	public void salvar(Contato contato) throws ExcecaoNegocio {
		ExcecaoNegocio excecao = new ExcecaoNegocio();
		if (TextUtils.isEmpty(contato.getNome())) {
			excecao.getMapaErros().put(R.id.txtNome, R.string.erro_preenchimento_obrigatorio);
		}
		if (TextUtils.isEmpty(contato.getEndereco())) {
			excecao.getMapaErros().put(R.id.txtEndereco, R.string.erro_preenchimento_obrigatorio);
		}
		if (TextUtils.isEmpty(contato.getSite())) {
			excecao.getMapaErros().put(R.id.txtSite, R.string.erro_preenchimento_obrigatorio);
		}
		if (TextUtils.isEmpty(contato.getTelefone())) {
			excecao.getMapaErros().put(R.id.txtTelefone, R.string.erro_preenchimento_obrigatorio);
		}
		
		if (!excecao.getMapaErros().isEmpty()) {
			throw excecao;
		}
		
		ContatoDAO.getInstancia().salvar(contato);
	}

	public void excluir(Contato contato) {
		ContatoDAO.getInstancia().excluir(contato);
	}
}
