package br.com.odlare.ponto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TelaDeEntradaActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imagemDeEntrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_entrada);

        imagemDeEntrada = (ImageView) findViewById(R.id.imagemDeEntrada);
        imagemDeEntrada.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(this, TelaInicialActivity.class);
        startActivity(it);
    }
}
