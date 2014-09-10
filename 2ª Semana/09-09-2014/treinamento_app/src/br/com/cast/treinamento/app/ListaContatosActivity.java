package br.com.cast.treinamento.app;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.service.ContatoService;
import br.com.cast.treinamento.app.widget.ContatoAdapter;
import br.com.cast.treinamento.app.widget.ContatoAdapterClickListener;

/**
 * Activity responsável pela listagem e disponibilização das funcionalidades da entidade {@link Contato}.
 * 
 * @author venilton.junior
 */
public class ListaContatosActivity extends LifeCicleActivity {
	
	protected static final String CHAVE_FILTRO_CONTATO = "CHAVE_FILTRO_CONTATO";

	private ListView listViewContatos;

	private Contato contatoSelecionado;
	
	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_lista_contatos);
		super.getSupportActionBar().setSubtitle(R.string.subtitle_lista_contatos_activity);
		
		listViewContatos = (ListView) super.findViewById(R.id.listViewContatos);
		
		super.registerForContextMenu(listViewContatos);
		
		ContatoAdapterClickListener listener = new ContatoAdapterClickListener(this);
		listViewContatos.setOnItemClickListener(listener);
		listViewContatos.setOnItemLongClickListener(listener);
	}
	
	@Override
	protected void onResume() {
		super.onResume();	
		carregarListView();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_contatos, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		//Opção 1: findItem + setVisible
//		menu.findItem(R.id.action_editar).setVisible(contatoSelecionado != null);
//		menu.findItem(R.id.action_excluir).setVisible(contatoSelecionado != null);
		//Opção 2: setGroupVisible
		menu.setGroupVisible(R.id.group_selecao_obrigatoria, contatoSelecionado != null);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		executarAcaoMenuSelecionado(item.getItemId());
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.lista_contatos, menu);
		menu.findItem(R.id.action_novo).setVisible(false);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		executarAcaoMenuSelecionado(item.getItemId());
		return super.onContextItemSelected(item);
	}

	public void recuperarContatoSelecionado(AdapterView<?> adapter, int posicao) {
		contatoSelecionado = (Contato) adapter.getItemAtPosition(posicao);
	}
	
	private void carregarListView() {
		List<Contato> contatos;
		
		Contato filtro = (Contato) super.getIntent().getSerializableExtra(CHAVE_FILTRO_CONTATO);
		if (filtro == null) {
			contatos = ContatoService.getInstancia(this).listarTodos();
		} else {
			contatos = ContatoService.getInstancia(this).listarPorFiltro(filtro);
		}
		ContatoAdapter adapter = new ContatoAdapter(this, contatos);		
		listViewContatos.setAdapter(adapter);
		
		contatoSelecionado = null;
	}
	
	private void executarAcaoMenuSelecionado(int id) {
		switch (id) {
		case R.id.action_novo:
			Intent intentIncluir = new Intent(this, ContatoActivity.class);
			super.startActivity(intentIncluir);
			break;
		case R.id.action_editar:
			Intent intentEditar = new Intent(this, ContatoActivity.class);
			intentEditar.putExtra(ContatoActivity.CHAVE_CONTATO, contatoSelecionado);
			super.startActivity(intentEditar);
			break;
		case R.id.action_excluir:
			new AlertDialog.Builder(this)
				.setTitle(R.string.dialog_confirmacao)
				.setMessage(getString(R.string.dialog_mensagem, contatoSelecionado.getNome()))
				.setPositiveButton(R.string.dialog_sim, new OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ContatoService.getInstancia(ListaContatosActivity.this).excluir(contatoSelecionado);
						carregarListView();
					}
				})
				.setNeutralButton(R.string.dialog_nao, null)
				.create().show();
			break;
		default:
			break;
		}
	}
}
