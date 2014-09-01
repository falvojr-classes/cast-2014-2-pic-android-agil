package com.example.treinamento.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Nossa primeira Activity!
 * 
 * @author venilton.junior
 */
public class MainActivity extends ActionBarActivity {

    private EditText txtExemplo;
	private Button btnExemplo;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Relaciona um layout a nossa Activity:
        setContentView(R.layout.activity_main_linear_vertical);
        
        txtExemplo = (EditText) findViewById(R.id.txtExemplo);    
        btnExemplo = (Button) findViewById(R.id.btnExemplo);
        
        // Relaciona um listener através de uma classe anônima:
        btnExemplo.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View view) {
				// Apresenta uma mensagem do tipo Toast na interface visual:
				Toast.makeText(MainActivity.this, "Você digitou: " + txtExemplo.getText().toString(), Toast.LENGTH_LONG).show();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
