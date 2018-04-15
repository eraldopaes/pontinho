package br.com.odlare.ponto;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class TelaDeEnvioDeResultadoActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nenhumResultado;
    ListView listaResultado;
    Button enviarResultado;
    ArrayAdapter<String> resultadoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_envio_de_resultado);

        nenhumResultado = (TextView) findViewById(R.id.nenhumResultado);
        listaResultado = (ListView) findViewById(R.id.listaResultado);
        enviarResultado = (Button) findViewById(R.id.enviarResultado);
        enviarResultado.setOnClickListener(this);

        resultadoAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, TelaInicialActivity.listaDeResultadosParaEnvio);
        listaResultado.setAdapter(resultadoAdapter);
        listaResultado.setEmptyView(nenhumResultado);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.enviarResultado:
                prepararResultadoParaEnvio();
                TelaInicialActivity.listaDeResultadosParaEnvio.clear();
                resultadoAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void prepararResultadoParaEnvio() {

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        DateFormat df = DateFormat.getDateTimeInstance();
        String data = df.format(date);

        StringBuilder resultado = new StringBuilder();

        resultado.append("Resultado data: ").append(data).append("\n\n");

        for (String resultadoEnviados : TelaInicialActivity.listaDeResultadosParaEnvio) {
            resultado.append(resultadoEnviados).append("\n");
        }

        StringBuilder numeros = new StringBuilder();

        for (Jogador jogadorCadastrado : TelaInicialActivity.jogadoresCadastrados) {

            if(TelaInicialActivity.listaDeJogadoresQueSairamDoJogo.contains(jogadorCadastrado)) {
                numeros.append(jogadorCadastrado.getTelefoneDoJogador()).append(";");
            }
        }


        Uri uri = Uri.parse("smsto:" + numeros.toString());
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri)
                .putExtra("sms_body", resultado.toString());
        dispararIntent(intent);
    }

    private void dispararIntent(Intent intent) {

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Ação não suportada.", Toast.LENGTH_SHORT).show();
        }
    }
}
