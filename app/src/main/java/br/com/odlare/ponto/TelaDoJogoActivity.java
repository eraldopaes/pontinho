package br.com.odlare.ponto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TelaDoJogoActivity extends AppCompatActivity implements View.OnClickListener {

    JogadorAdapter jogadorAdapter;
    ListView lista_dos_jogadores_na_mesa;
    Spinner spnOpcoesJogadores;
    List<String> listaComNomesDeJogadoresNasMesas;
    ArrayAdapter<String> listaDeOpcoesDeJogadores;
    Button botaoAdicionarJogador, botaoRemoverJogador, pontuarRodada, btnValorJogo;
    EditText edtValorDoJogo;
    RadioButton bateSimples, bateDeDez, acumula, naoAcumula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_do_jogo);

        listaComNomesDeJogadoresNasMesas = new ArrayList<>();
        lista_dos_jogadores_na_mesa = (ListView) findViewById(R.id.lista_dos_jogadores_na_mesa);
        jogadorAdapter = new JogadorAdapter(this, TelaInicialActivity.jogadoresNaMesa);
        lista_dos_jogadores_na_mesa.setAdapter(jogadorAdapter);
        lista_dos_jogadores_na_mesa.setEmptyView(findViewById(R.id.listaVazia));

        for (Jogador jogador : TelaInicialActivity.jogadoresCadastrados) {
            listaComNomesDeJogadoresNasMesas.add(jogador.getNomeDoJogador());
        }

        spnOpcoesJogadores = (Spinner) findViewById(R.id.spnOpcoesJogadores);
        listaDeOpcoesDeJogadores = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaComNomesDeJogadoresNasMesas);
        listaDeOpcoesDeJogadores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOpcoesJogadores.setAdapter(listaDeOpcoesDeJogadores);

        edtValorDoJogo = (EditText) findViewById(R.id.edtValorDoJogo);
        if (TelaInicialActivity.VALOR_DO_JOGO != 0) {
            edtValorDoJogo.setText(String.valueOf((int)TelaInicialActivity.VALOR_DO_JOGO));
        }

        bateDeDez = (RadioButton) findViewById(R.id.bateDeDez);
        bateSimples = (RadioButton) findViewById(R.id.bateSimples);
        acumula = (RadioButton) findViewById(R.id.acumula);
        naoAcumula = (RadioButton) findViewById(R.id.naoAcumula);

        botaoAdicionarJogador = (Button) findViewById(R.id.botaoAdicionarJogador);
        botaoRemoverJogador = (Button) findViewById(R.id.botaoRemoverJogador);
        pontuarRodada = (Button) findViewById(R.id.pontuarRodada);
        btnValorJogo = (Button) findViewById(R.id.btnValorJogo);
        botaoAdicionarJogador.setOnClickListener(this);
        botaoRemoverJogador.setOnClickListener(this);
        pontuarRodada.setOnClickListener(this);
        btnValorJogo.setOnClickListener(this);

        if (savedInstanceState != null) {
            if (TelaInicialActivity.VALOR_DO_JOGO != 0) {
                edtValorDoJogo.setText(String.valueOf((int)TelaInicialActivity.VALOR_DO_JOGO));
            }
            pontuarRodada.setClickable(savedInstanceState.getBoolean("botao_pontuar"));
            btnValorJogo.setClickable(savedInstanceState.getBoolean("botao_valor"));
            //edtValorDoJogo.setFocusable(savedInstanceState.getBoolean("editar_valor"));
            edtValorDoJogo.setEnabled(savedInstanceState.getBoolean("editar_valor"));
        } else {

            if (TelaInicialActivity.VALOR_DO_JOGO != 0) {
                edtValorDoJogo.setText(String.valueOf((int)TelaInicialActivity.VALOR_DO_JOGO));
            }
            pontuarRodada.setClickable(TelaInicialActivity.BOTAO_PONTUAR);
            btnValorJogo.setClickable(TelaInicialActivity.BOTAO_VALOR);
            //edtValorDoJogo.setFocusable(TelaInicialActivity.EDITAR_VALOR);
            edtValorDoJogo.setEnabled(TelaInicialActivity.EDITAR_VALOR);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("botao_pontuar", TelaInicialActivity.BOTAO_PONTUAR);
        outState.putBoolean("botao_valor", TelaInicialActivity.BOTAO_VALOR);
        outState.putBoolean("editar_valor", TelaInicialActivity.EDITAR_VALOR);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.botaoAdicionarJogador:
                if (spnOpcoesJogadores.getCount() != 0) {
                    String jogador_adicionar = spnOpcoesJogadores.getSelectedItem().toString();
                    Jogador jog = new Jogador(jogador_adicionar, 0.0);
                    if (!TelaInicialActivity.jogadoresNaMesa.contains(jog)) {
                        int pontos = 0;
                        int numeroDeVoltas = 0;
                        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {
                            if (j.getPontosDoJogador() >= pontos)
                                pontos = j.getPontosDoJogador();
                            if (j.getNumeroDeVoltasDoJogador() >= numeroDeVoltas)
                                numeroDeVoltas = j.getNumeroDeVoltasDoJogador();
                        }
                        Jogador jogador = new Jogador(jogador_adicionar, pontos, numeroDeVoltas, numeroDeVoltas == 0 ? 0 : - TelaInicialActivity.VALOR_DO_JOGO * TelaInicialActivity.VALOR_DAS_VOLTAS[numeroDeVoltas], 0, 0.0, 0.0, 0.0, 0.0);
                        if (!TelaInicialActivity.jogadoresNaMesa.contains(jogador)) {
                            TelaInicialActivity.jogadoresNaMesa.add(jogador);
                            jogadorAdapter.notifyDataSetChanged();
                        }
                        Toast.makeText(this, jogador.getNomeDoJogador() + " foi adicionado!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Jogador já está na mesa.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Nenhum jogador cadastrado.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.botaoRemoverJogador:
                if (spnOpcoesJogadores.getCount() != 0) {
                    String jogador_remover = spnOpcoesJogadores.getSelectedItem().toString();
                    Jogador j = new Jogador(jogador_remover, 0.0);
                    if (TelaInicialActivity.jogadoresNaMesa.contains(j)) {
                        removerJogadorDaMesa(jogador_remover);
                    } else {
                        Toast.makeText(this, "Jogador não está  na mesa.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Nenhum jogador cadastrado.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.pontuarRodada:
                Intent it = new Intent(this, TelaDeSelecaoDeBateActivity.class);
                it.putExtra("bateu", TelaInicialActivity.JOGADOR_QUE_BATEU);
                startActivityForResult(it, TelaInicialActivity.REQUEST_BATE);
                break;

            case R.id.btnValorJogo:
                try {
                    if (Double.parseDouble(edtValorDoJogo.getText().toString()) > 0) {
                        TelaInicialActivity.VALOR_DO_JOGO = Double.parseDouble(edtValorDoJogo.getText().toString());
                        zerarParametros();

                        TelaInicialActivity.JOGO_EM_ANDAMENTO = true;

                        TelaInicialActivity.BOTAO_PONTUAR = true;
                        TelaInicialActivity.BOTAO_VALOR = false;
                        TelaInicialActivity.EDITAR_VALOR = false;

                        pontuarRodada.setClickable(TelaInicialActivity.BOTAO_PONTUAR);
                        btnValorJogo.setClickable(TelaInicialActivity.BOTAO_VALOR);
                        //edtValorDoJogo.setFocusable(TelaInicialActivity.EDITAR_VALOR);
                        edtValorDoJogo.setEnabled(TelaInicialActivity.EDITAR_VALOR);

                        StringBuilder msg = new StringBuilder();
                        msg.append("Valor do bate: " + edtValorDoJogo.getText().toString()).append("\n")
                                .append("Valor do pocinho: " + Double.toString(Double.parseDouble(edtValorDoJogo.getText().toString())*5)).append("\n")
                                .append("Valor do poção: " + Double.toString(Double.parseDouble(edtValorDoJogo.getText().toString())*5));
                        Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Valor inválido.", Toast.LENGTH_SHORT).show();
                    }
                }catch (NumberFormatException e) {
                    Toast.makeText(this, "Valor tem que ser preenchido!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void removerJogadorDaMesa(String jogador_remover) {

        Iterator<Jogador> jogadorIterator = TelaInicialActivity.jogadoresNaMesa.iterator();

        while (jogadorIterator.hasNext()) {

            Jogador jogadorRecuperado = jogadorIterator.next();

            if (jogadorRecuperado.getNomeDoJogador().equals(jogador_remover)) {

                jogadorIterator.remove();

                if (TelaInicialActivity.listaDeJogadoresQueSairamDoJogo.contains(jogadorRecuperado)) {
                    double valorContabilizado = 0.0;
                    int posicao = 0;

                    for (Jogador j : TelaInicialActivity.listaDeJogadoresQueSairamDoJogo) {
                        if (j.equals(jogadorRecuperado)) {
                            valorContabilizado = j.getValorGlobalDoJogador();
                            posicao = TelaInicialActivity.listaDeJogadoresQueSairamDoJogo.indexOf(j);
                        }
                    }

                    TelaInicialActivity.listaDeJogadoresQueSairamDoJogo.get(posicao).setValorGlobalDoJogador(jogadorRecuperado.getValorGlobalDoJogador() + valorContabilizado);
                    Toast.makeText(this, jogadorRecuperado.getNomeDoJogador() + " foi removido!", Toast.LENGTH_SHORT).show();
                    jogadorAdapter.notifyDataSetChanged();

                } else {
                    TelaInicialActivity.listaDeJogadoresQueSairamDoJogo.add(new Jogador(jogadorRecuperado.getNomeDoJogador(), jogadorRecuperado.getValorGlobalDoJogador()));
                    Toast.makeText(this, jogadorRecuperado.getNomeDoJogador() + " foi removido!", Toast.LENGTH_SHORT).show();
                    jogadorAdapter.notifyDataSetChanged();
                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == TelaInicialActivity.REQUEST_BATE) {

            TelaInicialActivity.JOGADOR_QUE_BATEU = data.getStringExtra(TelaDeSelecaoDeBateActivity.RESULTADO_BATE);

            if (TelaInicialActivity.JOGADOR_QUE_BATEU != null) {

                Intent it = new Intent(this, PontuarJogadoresActivity.class);
                startActivityForResult(it, TelaInicialActivity.REQUEST_PONTOS);
            }
        }

        if (resultCode == RESULT_OK && requestCode == TelaInicialActivity.REQUEST_PONTOS) {

            for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {
                for (Jogador j1 : TelaInicialActivity.listaComOsPOntosDosJogadoresNaPartidaAtual) {
                    if (j1.equals(j)) {
                        j.setPontosDoJogador(j.getPontosDoJogador() + j1.getPontosDoJogador());
                    }
                }
            }
            jogadorAdapter.notifyDataSetChanged();

            if (verificarSeOJogoTerminou()) {
                calcularBateFinal();
                if (!TelaInicialActivity.POCINHO_TEM_VENCEDOR) {
                    verificarPocinhoTemVencedorOuAcumula();
                }
                distribuirPocao();
                fazerCalculoPartidaAtual();
                fazerCalculoPartidaGlobal();

                TelaInicialActivity.JOGO_EM_ANDAMENTO = false;

                TelaInicialActivity.BOTAO_PONTUAR = false;
                TelaInicialActivity.BOTAO_VALOR = true;
                TelaInicialActivity.EDITAR_VALOR = true;

                pontuarRodada.setClickable(TelaInicialActivity.BOTAO_PONTUAR);
                btnValorJogo.setClickable(TelaInicialActivity.BOTAO_VALOR);
                //edtValorDoJogo.setFocusable(true);
                edtValorDoJogo.setEnabled(TelaInicialActivity.EDITAR_VALOR);

            } else {
                calcularBates();
                verificarVoltas();
                if (!TelaInicialActivity.POCINHO_TEM_VENCEDOR) {
                    verificarPocinho();
                }
                ajustarPontuacao();
            }
        }
    }

    private void zerarParametros() {

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {
            j.setPontosDoJogador(0);
            j.setNumeroDeVoltasDoJogador(0);
            j.setValorDasVoltasDoJogador(0);
            j.setBatesDoJogador(0);
            j.setPocinhoDoJogador(0);
            j.setPocaoDoJogador(0);
            j.setValorPartidaDoJogador(0);
        }
        jogadorAdapter.notifyDataSetChanged();

        TelaInicialActivity.POCINHO_TEM_VENCEDOR = false;
        TelaInicialActivity.MAIOR_PONTUACAO_ATUAL = 0;
        TelaInicialActivity.JOGADOR_QUE_BATEU = null;
        TelaInicialActivity.listaComOsPOntosDosJogadoresNaPartidaAtual.clear();
    }

    private void fazerCalculoPartidaGlobal() {

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {
            j.setValorGlobalDoJogador(j.getValorGlobalDoJogador() + j.getValorPartidaDoJogador());
        }
    }

    private void fazerCalculoPartidaAtual() {

        double valorDasVoltasSomadas = 0;

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {
            valorDasVoltasSomadas = valorDasVoltasSomadas + j.getValorDasVoltasDoJogador();
        }

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {

            if (j.getNomeDoJogador().equals(TelaInicialActivity.JOGADOR_QUE_BATEU)) {
                j.setValorPartidaDoJogador(j.getBatesDoJogador() + j.getPocinhoDoJogador() + j.getPocaoDoJogador() - valorDasVoltasSomadas + j.getValorDasVoltasDoJogador());
            } else {
                j.setValorPartidaDoJogador(j.getBatesDoJogador() + j.getPocinhoDoJogador() + j.getPocaoDoJogador() + j.getValorDasVoltasDoJogador());
            }
        }
        jogadorAdapter.notifyDataSetChanged();
    }

    private void verificarPocinhoTemVencedorOuAcumula() {

        boolean acumula = true;

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {

            if (j.getPontosDoJogador() < 100 && j.getNumeroDeVoltasDoJogador() == 0) {
                distribuirPocinho();
                TelaInicialActivity.NUMERO_DE_POCINHOS_ACUMULADOS = 1;
                acumula = false;
            }
        }

        if (acumula) {

            if(this.acumula.isChecked()) {
                TelaInicialActivity.NUMERO_DE_POCINHOS_ACUMULADOS++;
            }

            if (this.naoAcumula.isChecked()) {

                int numeroDeJogadoresQueEstouraram = 0;

                for (Jogador jogador : TelaInicialActivity.jogadoresNaMesa) {
                    if (jogador.getNumeroDeVoltasDoJogador() != 0) {
                        numeroDeJogadoresQueEstouraram++;
                    }
                }

                int numeroDeJogadoresQueNaoEstouraram = TelaInicialActivity.jogadoresNaMesa.size() - numeroDeJogadoresQueEstouraram;
                double valorASerDividido = (5 * TelaInicialActivity.VALOR_DO_JOGO * numeroDeJogadoresQueEstouraram * TelaInicialActivity.NUMERO_DE_POCINHOS_ACUMULADOS) / numeroDeJogadoresQueNaoEstouraram;

                for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {
                    if (j.getNumeroDeVoltasDoJogador() == 0) {
                        j.setPocinhoDoJogador(valorASerDividido);
                    } else {
                        j.setPocinhoDoJogador(-5 * TelaInicialActivity.VALOR_DO_JOGO * TelaInicialActivity.NUMERO_DE_POCINHOS_ACUMULADOS);
                    }
                }
                TelaInicialActivity.NUMERO_DE_POCINHOS_ACUMULADOS = 1;
            }
        }
    }

    private void distribuirPocao() {

        boolean bateZerado = false;

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {

            if (j.getNomeDoJogador().equals(TelaInicialActivity.JOGADOR_QUE_BATEU)) {
                j.setPocaoDoJogador((TelaInicialActivity.jogadoresNaMesa.size()-1) * 5 * TelaInicialActivity.VALOR_DO_JOGO);
                if (j.getPontosDoJogador() == 0) {
                    bateZerado = true;
                }
            } else {
                j.setPocaoDoJogador(-5 * TelaInicialActivity.VALOR_DO_JOGO);
            }
        }

        if (bateZerado) {
            for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {
                j.setPocaoDoJogador(j.getPocaoDoJogador()*2);
            }
        }

        jogadorAdapter.notifyDataSetChanged();
    }

    private void ajustarPontuacao() {

        int maiorAtual = 0;

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {
            if (j.getPontosDoJogador() < 100 && j.getPontosDoJogador() >= maiorAtual) {
                maiorAtual = j.getPontosDoJogador();
            }
        }

        TelaInicialActivity.MAIOR_PONTUACAO_ATUAL = maiorAtual;

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {
            if (j.getPontosDoJogador() >= 100) {
                j.setPontosDoJogador(TelaInicialActivity.MAIOR_PONTUACAO_ATUAL);
            }
        }

        jogadorAdapter.notifyDataSetChanged();
    }

    private boolean verificarSeOJogoTerminou() {

        int quantidaDeJogadoresAbaixoDe100Pontos = 0;

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {

            if (j.getPontosDoJogador() < 100) {
                quantidaDeJogadoresAbaixoDe100Pontos++;
            }
        }
        if (quantidaDeJogadoresAbaixoDe100Pontos == 1) {
            return true;
        }
        return false;
    }

    private void calcularBates() {

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {

            if (verificarBateDeDez()) {
                if (j.getNomeDoJogador().equals(TelaInicialActivity.JOGADOR_QUE_BATEU)) {
                    j.setBatesDoJogador(j.getBatesDoJogador() + 2 * (TelaInicialActivity.jogadoresNaMesa.size() - 1) * TelaInicialActivity.VALOR_DO_JOGO);
                } else {
                    j.setBatesDoJogador(j.getBatesDoJogador() - 2 * TelaInicialActivity.VALOR_DO_JOGO);
                }
            } else {
                if (j.getNomeDoJogador().equals(TelaInicialActivity.JOGADOR_QUE_BATEU)) {
                    j.setBatesDoJogador(j.getBatesDoJogador() + (TelaInicialActivity.jogadoresNaMesa.size() - 1) * TelaInicialActivity.VALOR_DO_JOGO);
                } else {
                    j.setBatesDoJogador(j.getBatesDoJogador() - TelaInicialActivity.VALOR_DO_JOGO);
                }
            }
        }
        jogadorAdapter.notifyDataSetChanged();
    }

    private boolean verificarBateDeDez() {

        if (bateSimples.isChecked()) {
            return false;
        }

        return true;
    }

    private void verificarVoltas() {

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {

            if (j.getPontosDoJogador() >= 100) {
                j.setNumeroDeVoltasDoJogador(j.getNumeroDeVoltasDoJogador() + 1);
                j.setValorDasVoltasDoJogador(-TelaInicialActivity.VALOR_DAS_VOLTAS[j.getNumeroDeVoltasDoJogador()] * TelaInicialActivity.VALOR_DO_JOGO);
            }
        }
        jogadorAdapter.notifyDataSetChanged();
    }

    private void verificarPocinho() {

        int numeroDeJogadoresQueNaoEstouraram = 0;

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {

            if (j.getNumeroDeVoltasDoJogador() == 0) {
                numeroDeJogadoresQueNaoEstouraram++;
            }
        }

        if (numeroDeJogadoresQueNaoEstouraram == 1) {
            TelaInicialActivity.POCINHO_TEM_VENCEDOR = true;
            distribuirPocinho();
            TelaInicialActivity.NUMERO_DE_POCINHOS_ACUMULADOS = 1;
        }
    }

    private void distribuirPocinho() {

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {

            if (j.getNumeroDeVoltasDoJogador() == 0 && j.getPontosDoJogador() <100) {
                j.setPocinhoDoJogador(5 * TelaInicialActivity.VALOR_DO_JOGO * TelaInicialActivity.NUMERO_DE_POCINHOS_ACUMULADOS * (TelaInicialActivity.jogadoresNaMesa.size() - 1));
            } else {
                j.setPocinhoDoJogador(-5 * TelaInicialActivity.VALOR_DO_JOGO * TelaInicialActivity.NUMERO_DE_POCINHOS_ACUMULADOS);
            }
        }

        jogadorAdapter.notifyDataSetChanged();
    }

    private void calcularBateFinal() {

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {

            if (verificarBateDeDez()) {
                if (j.getNomeDoJogador().equals(TelaInicialActivity.JOGADOR_QUE_BATEU)) {
                    j.setBatesDoJogador(j.getBatesDoJogador() + (TelaInicialActivity.jogadoresNaMesa.size() - 1) * TelaInicialActivity.VALOR_DO_JOGO);
                } else {
                    j.setBatesDoJogador(j.getBatesDoJogador() - TelaInicialActivity.VALOR_DO_JOGO);
                }
            }
        }
        jogadorAdapter.notifyDataSetChanged();
    }
}
