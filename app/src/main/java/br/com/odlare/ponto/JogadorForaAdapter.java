package br.com.odlare.ponto;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class JogadorForaAdapter extends BaseAdapter{

    Context ctx;
    List<Jogador> listaDeJogadoresFora;

    public JogadorForaAdapter(Context ctx, List<Jogador> listaDeJogadoresFora) {
        this.ctx = ctx;
        this.listaDeJogadoresFora = listaDeJogadoresFora;
    }

    @Override
    public int getCount() {
        return listaDeJogadoresFora.size();
    }

    @Override
    public Object getItem(int position) {
        return listaDeJogadoresFora.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Jogador jogador = listaDeJogadoresFora.get(position);

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.linha_tabela_jogadores_fora, null);
            holder = new ViewHolder();
            holder.nomeDoJogadorFora = (TextView) convertView.findViewById(R.id.nomeDoJogadorFora);
            holder.valorGlobalJogadorFora = (TextView) convertView.findViewById(R.id.valorGlobalJogadorFora);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nomeDoJogadorFora.setText(jogador.getNomeDoJogador());
        holder.valorGlobalJogadorFora.setText(String.valueOf(jogador.getValorGlobalDoJogador()));
        if (jogador.getValorGlobalDoJogador() >= 0.0){
            holder.valorGlobalJogadorFora.setTextColor(Color.argb(255,66,165,245));
            holder.nomeDoJogadorFora.setTextColor(Color.argb(255,66,165,245));
        } else {
            holder.valorGlobalJogadorFora.setTextColor(Color.argb(255,239,83,88));
            holder.nomeDoJogadorFora.setTextColor(Color.argb(255,239,83,88));
        }
        return convertView;
    }

    private class ViewHolder {
        TextView nomeDoJogadorFora;
        TextView valorGlobalJogadorFora;
    }
}
