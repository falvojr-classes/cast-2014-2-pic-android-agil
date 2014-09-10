package br.com.cast.treinamento.app.service;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import br.com.cast.treinamento.app.R;
import br.com.cast.treinamento.app.dao.ContatoDAO;
import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.domain.exception.BusinessException;

/**
 * Classe que respresenta a camada de serviço da entidade {@link Contato}.
 * 
 * @author venilton.junior
 */
public final class ContatoService {

	private static ContatoService INSTANCIA;
	
	private Context contexto;
	
	private ContatoService(Context contexto) {
		super();
		this.contexto = contexto;
	}

	public static ContatoService getInstancia(Context contexto) {
		if (INSTANCIA == null) {
			INSTANCIA = new ContatoService(contexto);	
		}
		return INSTANCIA;
	}

	public List<Contato> listarTodos() {
		return ContatoDAO.getInstancia(contexto).listarTodos();	
	}
	
	public void salvar(Contato contato) throws BusinessException {
		BusinessException excecao = new BusinessException();
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
		
		ContatoDAO.getInstancia(contexto).salvar(contato);
	}

	public void excluir(Contato contato) {
		ContatoDAO.getInstancia(contexto).excluir(contato);
	}

	public List<Contato> listarPorFiltro(Contato filtro) {
		return ContatoDAO.getInstancia(contexto).listarPorFiltro(filtro);
	}
	
	public long contarPorFiltro(Contato filtro) {
		return ContatoDAO.getInstancia(contexto).contarPorFiltro(filtro);
	}
}
