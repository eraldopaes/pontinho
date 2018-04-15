package br.com.odlare.ponto;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static br.com.odlare.ponto.TelaInicialActivity.bancoDados;

public class TelaDeCadastroActivity extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnClickListener {

    ListView listaDeJogadoresCadastrados;
    EditText edtNome, edtTelefoneDoJogador;
    static List<Jogador> listaComNomesDeCadastrados;
    JogadorCadastradoAdapter adapter;
    Button adicionarNovoJogador, editarJogador, excluirJogador, confirmarEdicaoJogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_cadastro);

        listaComNomesDeCadastrados = new ArrayList<>();

//        for (Jogador jogador : TelaInicialActivity.jogadoresCadastrados) {
//            //listaComNomesDeCadastrados.add(jogador);
//        }

        listar();

        listaDeJogadoresCadastrados = (ListView) findViewById(R.id.listaDeJogadoresCadastrados);

        adapter = new JogadorCadastradoAdapter(this, listaComNomesDeCadastrados);
        listaDeJogadoresCadastrados.setAdapter(adapter);

        adicionarNovoJogador = (Button) findViewById(R.id.adicionarNovoJogador);
        editarJogador = (Button) findViewById(R.id.editarJogador);
        excluirJogador = (Button) findViewById(R.id.excluirJogador);
        confirmarEdicaoJogador = (Button) findViewById(R.id.confirmarEdicaoJogador);
        adicionarNovoJogador.setOnClickListener(this);
        excluirJogador.setOnClickListener(this);
        editarJogador.setOnClickListener(this);
        confirmarEdicaoJogador.setOnClickListener(this);

        confirmarEdicaoJogador.setEnabled(false);

        edtNome = (EditText) findViewById(R.id.edtNomeDoJogador);
        edtTelefoneDoJogador = (EditText) findViewById(R.id.edtTelefoneDoJogador);
        edtTelefoneDoJogador.setOnEditorActionListener(this);

        edtNome.setEnabled(false);
        edtTelefoneDoJogador.setEnabled(false);

        edtNome.setFocusable(false);
        edtTelefoneDoJogador.setFocusable(false);

        listaDeJogadoresCadastrados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Jogador jogador = listaComNomesDeCadastrados.get(position);
                edtNome.setText(jogador.getNomeDoJogador());
                edtTelefoneDoJogador.setText(jogador.getTelefoneDoJogador());
            }
        });
    }

    public static void listar() {

        try {

            Cursor cursor = bancoDados.rawQuery("SELECT * FROM jogadores ORDER BY id DESC",
                    null);
            cursor.moveToFirst();

            listaComNomesDeCadastrados.clear();
            TelaInicialActivity.jogadoresCadastrados.clear();

            while (cursor != null) {

                Integer id = cursor.getInt(0);
                String nome = cursor.getString(1);
                String telefone = cursor.getString(2);
                Jogador jogador = new Jogador(id, nome, telefone);
                listaComNomesDeCadastrados.add(jogador);
                TelaInicialActivity.jogadoresCadastrados.add(jogador);
                cursor.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (v == edtTelefoneDoJogador && EditorInfo.IME_ACTION_DONE == actionId && !confirmarEdicaoJogador.isEnabled()) {

            String nome = edtNome.getText().toString().trim();
            String telefone = edtTelefoneDoJogador.getText().toString().trim();

            String nomeFinal = nome.replace(" ", "");

            if (telefone.length() == 9 && nomeFinal.length() != 0 && nomeFinal.length() <= 10) {

                Jogador novoJogador = new Jogador(nomeFinal, telefone);

                if (!listaComNomesDeCadastrados.contains(novoJogador)) {

                    bancoDados.execSQL("INSERT INTO jogadores (nome, telefone) VALUES ('"+nomeFinal+"', '"+telefone+"')");

                    TelaInicialActivity.jogadoresCadastrados.add(novoJogador);
                    listaComNomesDeCadastrados.add(new Jogador(nomeFinal, telefone));
                    adapter.notifyDataSetChanged();
                    edtNome.setText("");
                    edtTelefoneDoJogador.setText("");
                    edtNome.setEnabled(false);
                    edtTelefoneDoJogador.setEnabled(false);
                    edtNome.setFocusable(false);
                    edtTelefoneDoJogador.setFocusable(false);
                    editarJogador.setEnabled(true);
                    excluirJogador.setEnabled(true);
                    adicionarNovoJogador.setEnabled(true);
                    Toast.makeText(this, "Jogador cadastrado com sucesso.\nBoa sorte!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Jogador já está cadastrado.", Toast.LENGTH_SHORT).show();
                    edtNome.setEnabled(false);
                    edtTelefoneDoJogador.setEnabled(false);
                    edtNome.setFocusable(false);
                    edtTelefoneDoJogador.setFocusable(false);
                    edtNome.setText("");
                    edtTelefoneDoJogador.setText("");
                    editarJogador.setEnabled(true);
                    excluirJogador.setEnabled(true);
                    adicionarNovoJogador.setEnabled(true);
                }
            } else {
                Toast.makeText(this, "Verifique os campos.", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.adicionarNovoJogador:
                edtNome.setText("");
                edtTelefoneDoJogador.setText("");
                edtNome.setEnabled(true);
                edtTelefoneDoJogador.setEnabled(true);
                edtNome.setFocusable(true);
                edtTelefoneDoJogador.setFocusable(true);
                edtNome.setFocusableInTouchMode(true);
                edtTelefoneDoJogador.setFocusableInTouchMode(true);
                edtNome.requestFocus();
                editarJogador.setEnabled(false);
                excluirJogador.setEnabled(false);
                adicionarNovoJogador.setEnabled(false);
                break;
            case R.id.editarJogador:
                if (!edtNome.getText().toString().equalsIgnoreCase("") && !edtTelefoneDoJogador.getText().toString().equalsIgnoreCase("")) {
                    //edtNome.setEnabled(true);
                    edtTelefoneDoJogador.setEnabled(true);
                    //edtNome.setFocusable(true);
                    edtTelefoneDoJogador.setFocusable(true);
                    //edtNome.setFocusableInTouchMode(true);
                    edtTelefoneDoJogador.setFocusableInTouchMode(true);
                    editarJogador.setEnabled(false);
                    excluirJogador.setEnabled(false);
                    adicionarNovoJogador.setEnabled(false);
                    confirmarEdicaoJogador.setEnabled(true);
                } else {
                    Toast.makeText(this, "Nenhum jogador selecionado!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.excluirJogador:
                if (!edtNome.getText().toString().equalsIgnoreCase("") && !edtTelefoneDoJogador.getText().toString().equalsIgnoreCase("")) {
                    Jogador jogador = new Jogador(edtNome.getText().toString(), edtTelefoneDoJogador.getText().toString());
                    if (!TelaInicialActivity.jogadoresNaMesa.contains(jogador)) {
                        if (!TelaInicialActivity.listaDeJogadoresQueSairamDoJogo.contains(jogador)) {
                            if (TelaInicialActivity.jogadoresCadastrados.contains(jogador)) {
                                listaComNomesDeCadastrados.remove(jogador);
                                adapter.notifyDataSetChanged();
                                TelaInicialActivity.jogadoresCadastrados.remove(jogador);
                                edtNome.setText("");
                                edtTelefoneDoJogador.setText("");
                                edtNome.setEnabled(false);
                                edtTelefoneDoJogador.setEnabled(false);
                                edtNome.setFocusable(false);
                                edtTelefoneDoJogador.setFocusable(false);
                                Toast.makeText(this, "Jogador " + jogador.getNomeDoJogador() + " foi excluído!", Toast.LENGTH_SHORT).show();

                                bancoDados.execSQL("DELETE from jogadores where nome = '"+jogador.getNomeDoJogador()+"' and telefone = '"+jogador.getTelefoneDoJogador()+"'");

                            } else {
                                edtNome.setText("");
                                edtTelefoneDoJogador.setText("");
                                edtNome.setEnabled(false);
                                edtTelefoneDoJogador.setEnabled(false);
                                edtNome.setFocusable(false);
                                edtTelefoneDoJogador.setFocusable(false);
                                Toast.makeText(this, "Jogador não existe!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Esse jogador não pode ser excluído pois tem pendências!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Esse jogador não pode ser excluído pois está jogando!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Nenhum jogador selecionado!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.confirmarEdicaoJogador:

                String nome = edtNome.getText().toString().trim();
                String telefone = edtTelefoneDoJogador.getText().toString().trim();

                String nomeFinal = nome.replace(" ", "");

                if (telefone.length() == 9 && nomeFinal.length() != 0 && nomeFinal.length() <= 10) {

                    Jogador novoJogador = new Jogador(nomeFinal, telefone);
                    Jogador jogadorAExcluir = null;
                    for (Jogador j : TelaInicialActivity.jogadoresCadastrados) {
                        System.out.println(j);
                        if (j.getNomeDoJogador().equalsIgnoreCase(novoJogador.getNomeDoJogador())) {
                            jogadorAExcluir = j;
                        }
                    }
                    TelaInicialActivity.jogadoresCadastrados.remove(jogadorAExcluir);
                    listaComNomesDeCadastrados.remove(jogadorAExcluir);
                    adapter.notifyDataSetChanged();
                    TelaInicialActivity.jogadoresCadastrados.add(novoJogador);
                    listaComNomesDeCadastrados.add(novoJogador);
                    adapter.notifyDataSetChanged();
                    edtNome.setText("");
                    edtTelefoneDoJogador.setText("");
                    edtNome.setEnabled(false);
                    edtTelefoneDoJogador.setEnabled(false);
                    edtNome.setFocusable(false);
                    edtTelefoneDoJogador.setFocusable(false);
                    editarJogador.setEnabled(true);
                    excluirJogador.setEnabled(true);
                    adicionarNovoJogador.setEnabled(true);
                    confirmarEdicaoJogador.setEnabled(false);
                    Toast.makeText(this, "Dados editados com sucesso!", Toast.LENGTH_SHORT).show();

                    bancoDados.execSQL("UPDATE jogadores SET nome = '"+nomeFinal+"', telefone = '"+telefone+"' " +
                            "where nome = '"+jogadorAExcluir.getNomeDoJogador()+"' and telefone = '"+jogadorAExcluir.getTelefoneDoJogador()+"'");
                } else {
                    Toast.makeText(this, "Verifique os campos.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}