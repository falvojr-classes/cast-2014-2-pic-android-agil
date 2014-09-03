package br.com.cast.treinamento.app;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * {@link OnClickListener} criado para introduzir o conceito de listeners concretos.
 * 
 * @author venilton.junior
 */
public class MeuOnClickListener implements OnClickListener {

	@Override
	public void onClick(View view) {
		Log.d("MEU DEBUG", "Elemento clicado!");
	}

}
