package br.com.odlare.ponto;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TelaJogadoresForaActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listaJogadoresFora;
    JogadorForaAdapter jogadorForaAdapter;
    ArrayAdapter<String> sairamGanhandoAdapter;
    ArrayAdapter<String> sairamPerdendoAdapter;
    TextView nenhumJogador;
    Spinner spnJogadorQuePaga, spnJogadorQueRecebe;
    EditText edtValorASerPago;
    ArrayList<String> nomesDosQueSairamGanhando;
    ArrayList<String> nomesDosQueSairamPerdendo;
    Button concluirTransacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogadores_fora);

        nomesDosQueSairamGanhando = new ArrayList<>();
        nomesDosQueSairamPerdendo = new ArrayList<>();

        listaJogadoresFora = (ListView) findViewById(R.id.listaJogadoresFora);
        nenhumJogador = (TextView) findViewById(R.id.nenhumJogador);
        spnJogadorQuePaga = (Spinner) findViewById(R.id.spnJogadorQuePaga);
        spnJogadorQueRecebe = (Spinner) findViewById(R.id.spnJogadorQueRecebe);
        edtValorASerPago = (EditText) findViewById(R.id.edtValorASerPago);
        concluirTransacao = (Button) findViewById(R.id.concluirTransacao);

        concluirTransacao.setOnClickListener(this);

        jogadorForaAdapter = new JogadorForaAdapter(this, TelaInicialActivity.listaDeJogadoresQueSairamDoJogo);

        listaJogadoresFora.setAdapter(jogadorForaAdapter);
        listaJogadoresFora.setEmptyView(nenhumJogador);

        for (Jogador j : TelaInicialActivity.listaDeJogadoresQueSairamDoJogo) {
            if (j.getValorGlobalDoJogador() > 0.0) {
                nomesDosQueSairamGanhando.add(j.getNomeDoJogador());
            }

            if (j.getValorGlobalDoJogador() < 0.0) {
                nomesDosQueSairamPerdendo.add(j.getNomeDoJogador());
            }
        }

        sairamGanhandoAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nomesDosQueSairamGanhando);
        sairamGanhandoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sairamPerdendoAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nomesDosQueSairamPerdendo);
        sairamPerdendoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnJogadorQuePaga.setAdapter(sairamPerdendoAdapter);
        spnJogadorQueRecebe.setAdapter(sairamGanhandoAdapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.concluirTransacao:
                realizarTransacao();
                break;
        }
    }

    private void realizarTransacao() {

        try {
            String paga = spnJogadorQuePaga.getSelectedItem().toString();
            String recebe = spnJogadorQueRecebe.getSelectedItem().toString();
            double valor = Double.parseDouble(edtValorASerPago.getText().toString());

            boolean podePagar = false;
            boolean podeReceber = false;

            for (Jogador testeJogador : TelaInicialActivity.listaDeJogadoresQueSairamDoJogo) {
                if (testeJogador.getNomeDoJogador().equalsIgnoreCase(paga) && -testeJogador.getValorGlobalDoJogador() >= valor) {
                    podePagar = true;
                }
                if (testeJogador.getNomeDoJogador().equalsIgnoreCase(recebe) && testeJogador.getValorGlobalDoJogador() >= valor) {
                    podeReceber = true;
                }
            }

            if (podePagar) {
                if (podeReceber) {
                    for (Jogador jogador : TelaInicialActivity.listaDeJogadoresQueSairamDoJogo) {

                        if (jogador.getNomeDoJogador().equals(paga)) {
                            jogador.setValorGlobalDoJogador(jogador.getValorGlobalDoJogador() + valor);
                            if (jogador.getValorGlobalDoJogador() == 0) {
                                nomesDosQueSairamPerdendo.remove(jogador.getNomeDoJogador());
                            }
                        }
                        if (jogador.getNomeDoJogador().equals(recebe)) {
                            jogador.setValorGlobalDoJogador(jogador.getValorGlobalDoJogador() - valor);
                            if (jogador.getValorGlobalDoJogador() == 0) {
                                nomesDosQueSairamGanhando.remove(jogador.getNomeDoJogador());
                            }
                        }
                    }
                    jogadorForaAdapter.notifyDataSetChanged();
                    sairamGanhandoAdapter.notifyDataSetChanged();
                    sairamPerdendoAdapter.notifyDataSetChanged();
                    String msg = paga + " " + valor + " " + recebe;
                    TelaInicialActivity.listaDeResultadosParaEnvio.add(msg);
                    Toast.makeText(this, "Transação realizada com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Transação não realizada.\nValor superior ao que o jogador tem a receber.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Transação não realizada.\nValor superior ao que o jogador tem que pagar.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException ex) {
            Log.i("Erro de valor", String.valueOf(ex));
            Toast.makeText(this, "Erro na transação!\nConfira os dados e tente novamente.", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException ex) {
            Toast.makeText(this, "Verificar campos.", Toast.LENGTH_SHORT).show();
        }
        edtValorASerPago.setText("");
    }
}
