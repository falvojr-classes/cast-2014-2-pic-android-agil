package br.com.cast.treinamento.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.cast.treinamento.app.domain.Contato;

/**
 * Activity responsável pela inclusão da funcionalidade de filtro na {@link ListaContatosActivity}.
 * 
 * @author venilton.junior
 */
public class FiltroContatosActivity extends LifeCicleActivity {

	private EditText txtNome, txtTelefone;
	private Button btnFiltrar;

	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filtro_contatos);
		
		super.getSupportActionBar().setSubtitle(R.string.subtitle_filtrar_contatos);
		
		this.bindingElementosLayout();

		this.btnFiltrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent intentLista = new Intent(FiltroContatosActivity.this, ListaContatosActivity.class);
				if (!TextUtils.isEmpty(txtNome.getText()) || !TextUtils.isEmpty(txtTelefone.getText())) {
					Contato filtroContato = new Contato();
					filtroContato .setNome(txtNome.getText().toString());
					filtroContato.setTelefone(txtTelefone.getText().toString());
					intentLista.putExtra(ListaContatosActivity.CHAVE_FILTRO_CONTATO, filtroContato);
				}
				FiltroContatosActivity.super.startActivity(intentLista);
			}
		});
	}
	
	private void bindingElementosLayout() {
		txtNome = (EditText) findViewById(R.id.txtNome);
		txtTelefone = (EditText) findViewById(R.id.txtTelefone);
		btnFiltrar = (Button) findViewById(R.id.btnFiltrar);
	}
}
