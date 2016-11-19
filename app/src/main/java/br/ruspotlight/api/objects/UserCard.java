package br.ruspotlight.api.objects;

public class UserCard {
    private int janta;
    private int almoco;
    private String nomeUsuario;
    private int codigoCartao;
    private String tipoVinculo;
    private int totalRefeicoes;

    public int getDinnerBalance() {
        return this.janta;
    }

    public int getLunchBalance() {
        return this.almoco;
    }

    public String getUsername() {
        return this.nomeUsuario;
    }

    public int getCode() {
        return this.codigoCartao;
    }

    public String getLinkType() {
        return this.tipoVinculo;
    }

    public int getTotalMeals() {
        return this.totalRefeicoes;
    }
}
