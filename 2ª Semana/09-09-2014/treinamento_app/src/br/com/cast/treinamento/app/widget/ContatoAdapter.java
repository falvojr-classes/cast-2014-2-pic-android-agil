package br.com.cast.treinamento.app.widget;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.cast.treinamento.app.R;
import br.com.cast.treinamento.app.domain.Contato;

/**
 * Adapter customizado para a entidade {@link Contato}.
 * 
 * @author venilton.junior
 */
public class ContatoAdapter extends BaseAdapter {

	private Activity contexto;
	private List<Contato> itens;
	
	public ContatoAdapter(Activity contexto, List<Contato> itens) {
		super();
		this.itens = itens;
		this.contexto = contexto;
	}

	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public Contato getItem(int posicao) {
		return itens.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		Contato item = getItem(posicao);
		return item.getId();
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int posicao, View view, ViewGroup viewPai) {
		Contato item = getItem(posicao);
		
		LayoutInflater layoutInflater = contexto.getLayoutInflater();
		View layoutItem = layoutInflater.inflate(R.layout.item_list_view_contato, null);
			
		TextView lblNome = (TextView) layoutItem.findViewById(R.id.lblNome);	
		lblNome.setText(item.getNome());
		
		TextView lblTelefone = (TextView) layoutItem.findViewById(R.id.lblTelefone);
		lblTelefone.setText(item.getTelefone());
		
		return layoutItem;
	}

}
