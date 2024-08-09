package modelo;

public enum Lucro {
	TIPO_LUCRO("Selecione o tipo..."), BRUTO("Bruto"), OPERACIONAL("Operacional"), LIQUIDO("Líquido");

	private String descricao;

	public String getDescricao() {
		return this.descricao;
	}

	private Lucro(String descricao){
	          this.descricao = descricao;
	}
}
