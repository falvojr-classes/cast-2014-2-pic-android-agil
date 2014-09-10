package br.com.cast.treinamento.app.widget;

import br.com.cast.treinamento.app.ListaContatosActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * Lestener communs aos eventos onItemClick e onItemLongClick.
 * 
 * @author venilton.junior
 */
public class ContatoAdapterClickListener implements OnItemClickListener, OnItemLongClickListener {

	private ListaContatosActivity contexto;

	public ContatoAdapterClickListener(ListaContatosActivity contexto) {
		this.contexto = contexto;
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
		contexto.recuperarContatoSelecionado(adapter, posicao);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
		contexto.recuperarContatoSelecionado(adapter, posicao);
		return false;
	}

}
