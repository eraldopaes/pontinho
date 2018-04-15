package br.com.odlare.ponto;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class JogadorPontosAdapter extends BaseAdapter{

    Context ctx;
    List<Jogador> jogadoresLista;

    public JogadorPontosAdapter(Context ctx, List<Jogador> jogadoresLista) {
        this.ctx = ctx;
        this.jogadoresLista = jogadoresLista;
    }

    @Override
    public int getCount() {
        return jogadoresLista.size();
    }

    @Override
    public Object getItem(int position) {
        return jogadoresLista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Jogador jogador = jogadoresLista.get(position);

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.linha_tabela_pontuar_jogadores, null);
            holder = new ViewHolder();
            holder.nomeDoJogador = (TextView) convertView.findViewById(R.id.nomeDoJogador);
            holder.edtPontosDoJogador = (TextView) convertView.findViewById(R.id.edtPontosDoJogador);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nomeDoJogador.setText(jogador.getNomeDoJogador());
        holder.edtPontosDoJogador.setText(String.valueOf(jogador.getPontosDoJogador()));

        return convertView;
    }

    private class ViewHolder {
        TextView nomeDoJogador;
        TextView edtPontosDoJogador;
    }
}
