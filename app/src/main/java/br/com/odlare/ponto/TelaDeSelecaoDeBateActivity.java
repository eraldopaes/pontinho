package br.com.odlare.ponto;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TelaDeSelecaoDeBateActivity extends ListActivity {

    public static final String RESULTADO_BATE = "Result";

    ArrayList<String> listaComJogadoresNasMesas;

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String jogadorQueBateu = l.getItemAtPosition(position).toString();
        Intent it = new Intent();
        it.putExtra(TelaDeSelecaoDeBateActivity.RESULTADO_BATE, jogadorQueBateu);
        setResult(RESULT_OK, it);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        listaComJogadoresNasMesas = new ArrayList<>();

        for (Jogador j : TelaInicialActivity.jogadoresNaMesa) {
            listaComJogadoresNasMesas.add(j.getNomeDoJogador());
        }
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, listaComJogadoresNasMesas));
        String jogador = getIntent().getStringExtra("bateu");

        if (jogador != null) {

            int position = listaComJogadoresNasMesas.indexOf(jogador);
            getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            getListView().setItemChecked(position, true);
        }
    }
}
