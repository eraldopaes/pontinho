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

public class JogadorAdapter extends BaseAdapter {

    Context ctx;
    List<Jogador> jogadoresLista;

    public JogadorAdapter(Context ctx, List<Jogador> jogadoresLista) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.linha_da_tabela_customizada, null);
            holder = new ViewHolder();
            holder.nome_jogador = (TextView) convertView.findViewById(R.id.nome_jogador);
            holder.pontos_jogador = (TextView) convertView.findViewById(R.id.pontos_jogador);
            holder.voltas_jogador = (TextView) convertView.findViewById(R.id.voltas_jogador);
            holder.valor_voltas_jogador = (TextView) convertView.findViewById(R.id.valor_voltas_jogador);
            holder.valor_bates_jogador = (TextView) convertView.findViewById(R.id.valor_bates_jogador);
            holder.pocinho_jogador = (TextView) convertView.findViewById(R.id.pocinho_jogador);
            holder.pocao_jogador = (TextView) convertView.findViewById(R.id.pocao_jogador);
            holder.valor_partida_jogador = (TextView) convertView.findViewById(R.id.valor_partida_jogador);
            holder.valor_global_jogador = (TextView) convertView.findViewById(R.id.valor_global_jogador);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nome_jogador.setText(jogador.getNomeDoJogador());
        holder.pontos_jogador.setText(Integer.toString(jogador.getPontosDoJogador()));
        holder.voltas_jogador.setText(Integer.toString(jogador.getNumeroDeVoltasDoJogador()));

        holder.valor_voltas_jogador.setText(String.valueOf(jogador.getValorDasVoltasDoJogador()));
        if (jogador.getValorDasVoltasDoJogador() == 0.0){
            holder.valor_voltas_jogador.setTextColor(Color.argb(255,66,165,245));
        } else {
            holder.valor_voltas_jogador.setTextColor(Color.argb(255,239,83,88));
        }

        holder.valor_bates_jogador.setText(String.valueOf(jogador.getBatesDoJogador()));
        if (jogador.getBatesDoJogador() >= 0) {
            holder.valor_bates_jogador.setTextColor(Color.argb(255,66,165,245));
        } else {
            holder.valor_bates_jogador.setTextColor(Color.argb(255,239,83,88));
        }

        holder.pocinho_jogador.setText(String.valueOf(jogador.getPocinhoDoJogador()));
        if (jogador.getPocinhoDoJogador() >= 0.0) {
            holder.pocinho_jogador.setTextColor(Color.argb(255,66,165,245));
        } else {
            holder.pocinho_jogador.setTextColor(Color.argb(255,239,83,88));
        }

        holder.pocao_jogador.setText(String.valueOf(jogador.getPocaoDoJogador()));
        if (jogador.getPocaoDoJogador() >= 0.0) {
            holder.pocao_jogador.setTextColor(Color.argb(255,66,165,245));
        } else {
            holder.pocao_jogador.setTextColor(Color.argb(255,239,83,88));
        }

        holder.valor_partida_jogador.setText(String.valueOf(jogador.getValorPartidaDoJogador()));
        if (jogador.getValorPartidaDoJogador() >= 0.0) {
            holder.valor_partida_jogador.setTextColor(Color.argb(255,66,165,245));
        } else {
            holder.valor_partida_jogador.setTextColor(Color.argb(255,239,83,88));
        }

        holder.valor_global_jogador.setText(String.valueOf(jogador.getValorGlobalDoJogador()));
        if (jogador.getValorGlobalDoJogador() >= 0.0) {
            holder.valor_global_jogador.setTextColor(Color.argb(255,66,165,245));
        } else {
            holder.valor_global_jogador.setTextColor(Color.argb(255,239,83,88));
        }
        return convertView;
    }

    private class ViewHolder {
        TextView nome_jogador;
        TextView pontos_jogador;
        TextView voltas_jogador;
        TextView valor_voltas_jogador;
        TextView valor_bates_jogador;
        TextView pocinho_jogador;
        TextView pocao_jogador;
        TextView valor_partida_jogador;
        TextView valor_global_jogador;
    }
}
