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

public class JogadorCadastradoAdapter extends BaseAdapter {


        Context ctx;
        List<Jogador> listaDeJogadoresCadastrados;

        public JogadorCadastradoAdapter(Context ctx, List<Jogador> listaDeJogadoresCadastrados) {
            this.ctx = ctx;
            this.listaDeJogadoresCadastrados = listaDeJogadoresCadastrados;
        }

        @Override
        public int getCount() {
            return listaDeJogadoresCadastrados.size();
        }

        @Override
        public Object getItem(int position) {
            return listaDeJogadoresCadastrados.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Jogador jogador = listaDeJogadoresCadastrados.get(position);

            ViewHolder holder = null;

            if (convertView == null) {
                convertView = LayoutInflater.from(ctx).inflate(R.layout.linha_tabela_cadastro, null);
                holder = new ViewHolder();
                holder.nomeDoJogadorCadastrado = (TextView) convertView.findViewById(R.id.nomeDoJogadorCadastrado);
                holder.telefoneDoJogadorCadastrado = (TextView) convertView.findViewById(R.id.telefoneDoJogadorCadastrado);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.nomeDoJogadorCadastrado.setText(jogador.getNomeDoJogador());
            holder.telefoneDoJogadorCadastrado.setText(jogador.getTelefoneDoJogador());
            return convertView;
        }

        private class ViewHolder {
            TextView nomeDoJogadorCadastrado;
            TextView telefoneDoJogadorCadastrado;
        }
}
