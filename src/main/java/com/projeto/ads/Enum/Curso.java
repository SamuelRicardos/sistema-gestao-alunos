package com.projeto.ads.Enum;

public enum Curso {
	
	ADMINISTRACAO("Administração"),
	ADS ("Análise e Desenvolvimento de Sistemas"),
	CONTABILIDADE("Contabilidade"),
	ENFERMAGEM("Enfermagem"),
	MARKETING("Marketing");
	
	private String descricao;
	
	private Curso(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
