package br.com.odlare.ponto;

import java.io.Serializable;

public class Jogador implements Serializable {

    private Integer id;
    private String nomeDoJogador;
    private int pontosDoJogador;
    private int numeroDeVoltasDoJogador;
    private double valorDasVoltasDoJogador;
    private double batesDoJogador;
    private double pocinhoDoJogador;
    private double pocaoDoJogador;
    private double valorPartidaDoJogador;
    private double valorGlobalDoJogador;
    private String telefoneDoJogador;

    public Jogador(Integer id, String nomeDoJogador, String telefoneDoJogador) {
        this.id = id;
        this.nomeDoJogador = nomeDoJogador;
        this.telefoneDoJogador = telefoneDoJogador;
    }

    public Jogador(String nomeDoJogador, String telefoneDoJogador) {
        this.nomeDoJogador = nomeDoJogador;
        this.telefoneDoJogador = telefoneDoJogador;
    }

    public Jogador(String nomeDoJogador, int pontosDoJogador) {
        this.nomeDoJogador = nomeDoJogador;
        this.pontosDoJogador = pontosDoJogador;
    }

    public Jogador(String nomeDoJogador, double valorGlobalDoJogador) {
        this.nomeDoJogador = nomeDoJogador;
        this.valorGlobalDoJogador = valorGlobalDoJogador;
    }

    public Jogador(String nomeDoJogador, int pontosDoJogador, int numeroDeVoltasDoJogador,
                   double valorDasVoltasDoJogador, double batesDoJogador, double pocinhoDoJogador,
                   double pocaoDoJogador, double valorPartidaDoJogador, double valorGlobalDoJogador) {
        this.nomeDoJogador = nomeDoJogador;
        this.pontosDoJogador = pontosDoJogador;
        this.numeroDeVoltasDoJogador = numeroDeVoltasDoJogador;
        this.valorDasVoltasDoJogador = valorDasVoltasDoJogador;
        this.batesDoJogador = batesDoJogador;
        this.pocinhoDoJogador = pocinhoDoJogador;
        this.pocaoDoJogador = pocaoDoJogador;
        this.valorPartidaDoJogador = valorPartidaDoJogador;
        this.valorGlobalDoJogador = valorGlobalDoJogador;
    }

    public String getNomeDoJogador() {
        return nomeDoJogador;
    }

    public void setNomeDoJogador(String nomeDoJogador) {
        this.nomeDoJogador = nomeDoJogador;
    }

    public int getPontosDoJogador() {
        return pontosDoJogador;
    }

    public void setPontosDoJogador(int pontosDoJogador) {
        this.pontosDoJogador = pontosDoJogador;
    }

    public int getNumeroDeVoltasDoJogador() {
        return numeroDeVoltasDoJogador;
    }

    public void setNumeroDeVoltasDoJogador(int numeroDeVoltasDoJogador) {
        this.numeroDeVoltasDoJogador = numeroDeVoltasDoJogador;
    }

    public double getValorDasVoltasDoJogador() {
        return valorDasVoltasDoJogador;
    }

    public void setValorDasVoltasDoJogador(double valorDasVoltasDoJogador) {
        this.valorDasVoltasDoJogador = valorDasVoltasDoJogador;
    }

    public double getBatesDoJogador() {
        return batesDoJogador;
    }

    public void setBatesDoJogador(double batesDoJogador) {
        this.batesDoJogador = batesDoJogador;
    }

    public double getPocinhoDoJogador() {
        return pocinhoDoJogador;
    }

    public void setPocinhoDoJogador(double pocinhoDoJogador) {
        this.pocinhoDoJogador = pocinhoDoJogador;
    }

    public double getPocaoDoJogador() {
        return pocaoDoJogador;
    }

    public void setPocaoDoJogador(double pocaoDoJogador) {
        this.pocaoDoJogador = pocaoDoJogador;
    }

    public double getValorPartidaDoJogador() {
        return valorPartidaDoJogador;
    }

    public void setValorPartidaDoJogador(double valorPartidaDoJogador) {
        this.valorPartidaDoJogador = valorPartidaDoJogador;
    }

    public double getValorGlobalDoJogador() {
        return valorGlobalDoJogador;
    }

    public void setValorGlobalDoJogador(double valorGlobalDoJogador) {
        this.valorGlobalDoJogador = valorGlobalDoJogador;
    }

    public String getTelefoneDoJogador() {
        return telefoneDoJogador;
    }

    public void setTelefoneDoJogador(String telefoneDoJogador) {
        this.telefoneDoJogador = telefoneDoJogador;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Jogador jogador = (Jogador) o;

        return nomeDoJogador.equalsIgnoreCase(jogador.nomeDoJogador);

    }

    @Override
    public int hashCode() {
        return nomeDoJogador.hashCode();
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "id=" + id +
                ", nomeDoJogador='" + nomeDoJogador + '\'' +
                ", pontosDoJogador=" + pontosDoJogador +
                ", numeroDeVoltasDoJogador=" + numeroDeVoltasDoJogador +
                ", valorDasVoltasDoJogador=" + valorDasVoltasDoJogador +
                ", batesDoJogador=" + batesDoJogador +
                ", pocinhoDoJogador=" + pocinhoDoJogador +
                ", pocaoDoJogador=" + pocaoDoJogador +
                ", valorPartidaDoJogador=" + valorPartidaDoJogador +
                ", valorGlobalDoJogador=" + valorGlobalDoJogador +
                ", telefoneDoJogador='" + telefoneDoJogador + '\'' +
                '}';
    }
}
