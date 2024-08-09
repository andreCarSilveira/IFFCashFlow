package modelo;

public enum DespesaReceita {

	DESPESA_OR_RECEITA("Selecione o tipo..."), RECEITA("Receita"), DESPESA("Despesa");

	private String descricao;

	public String getDescricao() {
		return this.descricao;
	}

	private DespesaReceita(String descricao){
	          this.descricao = descricao;
	}

}
