package modelo;

public enum Lucro {
	TIPO_LUCRO("Selecione o tipo..."), BRUTO("Bruto"), OPERACIONAL("Operacional"), LIQUIDO("L�quido");

	private String descricao;

	public String getDescricao() {
		return this.descricao;
	}

	private Lucro(String descricao){
	          this.descricao = descricao;
	}
}
