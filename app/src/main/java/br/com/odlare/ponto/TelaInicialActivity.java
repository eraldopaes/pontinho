package br.com.odlare.ponto;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TelaInicialActivity extends ListActivity {

    static ArrayList<Jogador> listaDeJogadoresQueSairamDoJogo;
    static ArrayList<Jogador> listaComOsPOntosDosJogadoresNaPartidaAtual;
    static ArrayList<Jogador> jogadoresCadastrados;
    static List<Jogador> jogadoresNaMesa;
    static List<String> listaDeResultadosParaEnvio;
    static String JOGADOR_QUE_BATEU;
    static int REQUEST_BATE = 1;
    static int REQUEST_PONTOS = 2;

    static double VALOR_DO_JOGO = 0;
    static boolean POCINHO_TEM_VENCEDOR = false;
    static int NUMERO_DE_POCINHOS_ACUMULADOS = 1;
    static int MAIOR_PONTUACAO_ATUAL = 0;
    static double VALOR_DAS_VOLTAS[] = {0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023};

    static boolean JOGO_EM_ANDAMENTO = false;
    static boolean BOTAO_PONTUAR = false;
    static boolean BOTAO_VALOR = true;
    static boolean EDITAR_VALOR = true;

    static SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jogadoresCadastrados = new ArrayList<>();
        jogadoresNaMesa = new ArrayList<>();
        listaComOsPOntosDosJogadoresNaPartidaAtual = new ArrayList<>();
        listaDeJogadoresQueSairamDoJogo = new ArrayList<>();
        listaDeResultadosParaEnvio = new ArrayList<>();

        ArrayAdapter<String> listaDeOpcoesDaTelaInicial =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                        getResources().getStringArray(R.array.lista_opcoes_tela_inicial));
        getListView().setAdapter(listaDeOpcoesDaTelaInicial);

        bancoDados = openOrCreateDatabase("pt", MODE_PRIVATE, null);
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS jogadores(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, telefone VARCHAR)");

        try {

            Cursor cursor = bancoDados.rawQuery("SELECT * FROM jogadores ORDER BY id DESC",
                    null);
            cursor.moveToFirst();

            TelaInicialActivity.jogadoresCadastrados.clear();

            while (cursor != null) {
                Integer id = cursor.getInt(0);
                String nome = cursor.getString(1);
                String telefone = cursor.getString(2);
                Jogador jogador = new Jogador(id, nome, telefone);
                TelaInicialActivity.jogadoresCadastrados.add(jogador);
                cursor.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        jogadoresCadastrados.add(new Jogador("Eraldo", "999360099"));
//        jogadoresCadastrados.add(new Jogador("Dudu", "999181838"));
//        jogadoresCadastrados.add(new Jogador("Cabral", "999001358"));
//        jogadoresCadastrados.add(new Jogador("Jorge", "996560099"));
//        jogadoresCadastrados.add(new Jogador("Icaro", "991923210"));
//        jogadoresCadastrados.add(new Jogador("Ricardinho", "999001377"));
//        jogadoresCadastrados.add(new Jogador("Chikabon", "999957777"));
//        jogadoresCadastrados.add(new Jogador("Diego", "981728982"));
//        jogadoresCadastrados.add(new Jogador("Marcio", "999946494"));
//        jogadoresCadastrados.add(new Jogador("Rodrigo", "991999599"));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        switch (position) {
            case 0:
                Intent iniciarPontinho = new Intent(this, TelaDoJogoActivity.class);
                startActivity(iniciarPontinho);
                break;
            case 1:
                Intent it = new Intent(this, TelaDeCadastroActivity.class);
                startActivity(it);
                break;
            case 2:
                Intent itt = new Intent(this, TelaDeEnvioDeResultadoActivity.class);
                startActivity(itt);
                break;
            case 3:
                Intent it_ = new Intent(this, TelaJogadoresForaActivity.class);
                startActivity(it_);
                break;
            case 4:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //Deixar aqui sem nada para que não seja possível retornar e perder os dados que estão nessa activity enquanto não tem banco.
    }
}
