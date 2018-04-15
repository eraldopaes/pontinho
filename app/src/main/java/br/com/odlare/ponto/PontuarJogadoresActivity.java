package br.com.odlare.ponto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PontuarJogadoresActivity extends AppCompatActivity implements View.OnClickListener{

    ArrayList<Jogador> listDeJogadoresNaMesa;
    JogadorPontosAdapter jogadorPontosAdapter;
    ListView listaDeJogadoresAPontuar;
    Button contabilizar, estouro;
    EditText edtPontos;
    int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontuar_jogadores);

        listDeJogadoresNaMesa = new ArrayList<>();
        listaDeJogadoresAPontuar = (ListView) findViewById(R.id.listaDeJogadoresAPontuar);

        contabilizar = (Button) findViewById(R.id.contabilizar);
        contabilizar.setOnClickListener(this);

        estouro = (Button) findViewById(R.id.estouro);
        estouro.setOnClickListener(this);

        edtPontos = (EditText)findViewById(R.id.edtPontos);

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {

            if (!j.getNomeDoJogador().equals(TelaInicialActivity.JOGADOR_QUE_BATEU)) {
                listDeJogadoresNaMesa.add(j);
            }
        }

        jogadorPontosAdapter = new JogadorPontosAdapter(this, listDeJogadoresNaMesa);
        listaDeJogadoresAPontuar.setAdapter(jogadorPontosAdapter);

        listaDeJogadoresAPontuar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Jogador jogador = listDeJogadoresNaMesa.get(position);
                    String nome = jogador.getNomeDoJogador();
                    listDeJogadoresNaMesa.remove(jogador);
                    try {
                        Jogador j = new Jogador(nome, Integer.parseInt(edtPontos.getText().toString()));
                        listDeJogadoresNaMesa.add(j);
                        jogadorPontosAdapter.notifyDataSetChanged();
                        contador++;
                    } catch (Exception e) {
                        listDeJogadoresNaMesa.add(new Jogador(nome, 0));
                        jogadorPontosAdapter.notifyDataSetChanged();
                        contador++;
                    }
                    edtPontos.setText("");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.contabilizar:
                if (contador >= listDeJogadoresNaMesa.size()) {
                    listDeJogadoresNaMesa.add(new Jogador(TelaInicialActivity.JOGADOR_QUE_BATEU, 0));
                    TelaInicialActivity.listaComOsPOntosDosJogadoresNaPartidaAtual.clear();
                    TelaInicialActivity.listaComOsPOntosDosJogadoresNaPartidaAtual = listDeJogadoresNaMesa;
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(this, "Pontue todos os jogadores", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.estouro:
                Jogador jogador = listDeJogadoresNaMesa.get(0);
                String nome = jogador.getNomeDoJogador();
                listDeJogadoresNaMesa.remove(jogador);
                try {
                    Jogador j = new Jogador(nome, 100);
                    listDeJogadoresNaMesa.add(j);
                    jogadorPontosAdapter.notifyDataSetChanged();
                    contador++;
                } catch (Exception e) {
                    listDeJogadoresNaMesa.add(new Jogador(nome, 0));
                    jogadorPontosAdapter.notifyDataSetChanged();
                    contador++;
                }
                edtPontos.setText("");
                break;
        }
    }
}
