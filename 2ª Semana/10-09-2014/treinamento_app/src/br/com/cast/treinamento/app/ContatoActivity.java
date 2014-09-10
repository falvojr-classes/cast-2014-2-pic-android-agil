package br.com.cast.treinamento.app;

import java.io.File;
import java.util.Map.Entry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.domain.exception.BusinessException;
import br.com.cast.treinamento.app.service.ContatoService;

/**
 * Activity responsável pela inclusão e alteração de um {@link Contato}.
 * 
 * @author venilton.junior
 */
public class ContatoActivity extends LifeCicleActivity {

	private static final int REQUEST_CODE_CAMERA = 12345;

	public static final int RESULT_CODE_NOVO = 1;
	public static final int RESULT_CODE_EDITAR = 2;
	
	public static final String CHAVE_CONTATO = "CHAVE_CONTATO";
	
	private EditText txtNome, txtEndereco, txtSite, txtTelefone;
	private RatingBar ratingBarRelevancia;
	private ImageView imgFoto;
	
	private Button btnSalvar;
	private Contato contato;
	private String caminhoReservadoFoto;

	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato);

		bindingElementosLayout();
		
		int recursoSubtitulo;
		if ((contato = (Contato) getIntent().getSerializableExtra(CHAVE_CONTATO)) == null) {
			contato = new Contato();
			recursoSubtitulo = R.string.subtitle_contato_activity_incluir;
		} else {
			carregarElementosLayout();
			recursoSubtitulo = R.string.subtitle_contato_activity_editar;
		}
		super.getSupportActionBar().setSubtitle(recursoSubtitulo);
		
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				carregarContato();
				try {
					ContatoActivity.this.setResult(contato.getId() == null ? RESULT_CODE_NOVO : RESULT_CODE_EDITAR);
					ContatoService.getInstancia(ContatoActivity.this).salvar(contato);
					ContatoActivity.this.finish();
				} catch (BusinessException excecao) {
					for (Entry<Integer, Integer> erro : excecao.getMapaErros().entrySet()) {
						EditText campoErro = (EditText) findViewById(erro.getKey());
						Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
						drawable.setBounds(0, 0, 50, 50);
						campoErro.setError(getString(erro.getValue()), drawable);
					}
				}
			}
		});
		
		imgFoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				caminhoReservadoFoto = Environment.getExternalStorageDirectory().toString() + File.separator + System.currentTimeMillis() + ".PNG";
				File arquivoFoto = new File(caminhoReservadoFoto);
				
				Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
				startActivityForResult(intentCamera, REQUEST_CODE_CAMERA);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_CAMERA) {
			if (resultCode == RESULT_OK) {
				contato.setFoto(caminhoReservadoFoto);
				carregarFoto();
			} else {
				//Deleta o arquivo salvo temporáriamente ExternalStorageDirectory do Android:
				File arquivoFoto = new File(caminhoReservadoFoto);
				if (arquivoFoto.exists()) {
					arquivoFoto.delete();
				}
				Toast.makeText(this, R.string.toast_foto_cancelada, Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void bindingElementosLayout() {
		txtNome = (EditText) findViewById(R.id.txtNome);
		txtEndereco = (EditText) findViewById(R.id.txtEndereco);
		txtSite = (EditText) findViewById(R.id.txtSite);
		txtTelefone = (EditText) findViewById(R.id.txtTelefone);
		ratingBarRelevancia = (RatingBar) findViewById(R.id.ratingBarRelevancia);
		btnSalvar = (Button) findViewById(R.id.btnSalvar);
		imgFoto = (ImageView) findViewById(R.id.imgFoto);
	}
	
	private void carregarElementosLayout() {
		txtNome.setText(contato.getNome());
		txtEndereco.setText(contato.getEndereco());
		txtSite.setText(contato.getSite());
		txtTelefone.setText(contato.getTelefone());
		if (contato.getRelevancia() != null) {
			ratingBarRelevancia.setRating(contato.getRelevancia());
		}
		if (contato.getFoto() != null) {
			carregarFoto();
		}
	}

	private void carregarFoto() {
		Bitmap bitmap = BitmapFactory.decodeFile(contato.getFoto());
		Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 204, 204, true);
		imgFoto.setImageBitmap(bitmapReduzido);
	}
	
	
	private void carregarContato() {
		contato.setNome(txtNome.getText().toString());
		contato.setEndereco(txtEndereco.getText().toString());
		contato.setSite(txtSite.getText().toString());
		contato.setTelefone(txtTelefone.getText().toString());
		contato.setRelevancia(ratingBarRelevancia.getRating());
	}

}
