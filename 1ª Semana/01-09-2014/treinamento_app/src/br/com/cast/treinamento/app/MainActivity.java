package br.com.cast.treinamento.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Minha primeira Activity, usada para exemplificar os conceitos básicos de comportamento de uma tela.
 * 
 * @author venilton.falvo
 */
public class MainActivity extends ActionBarActivity {

	private Button btnExemplo;
	private EditText txtExemplo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);

		txtExemplo = (EditText) super.findViewById(R.id.txtExemplo);

		btnExemplo = (Button) super.findViewById(R.id.btnExemplo);
		btnExemplo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(view.getContext(), MainActivity.super.getString(R.string.toast_voce_digitou, txtExemplo.getText().toString()), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
