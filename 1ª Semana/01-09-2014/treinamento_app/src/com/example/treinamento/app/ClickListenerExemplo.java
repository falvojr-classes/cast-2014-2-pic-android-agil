package com.example.treinamento.app;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Exemplo de uma classe que implementa o contrato {@link OnClickListener}.
 * 
 * @author venilton.junior
 */
public class ClickListenerExemplo implements OnClickListener {

	@Override
	public void onClick(View view) {
		Log.d("MEU DEBUG", "Botão clicado!");
	}

}
