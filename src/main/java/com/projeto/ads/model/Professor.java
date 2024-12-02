package com.projeto.ads.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.projeto.ads.Enum.Titulacao;

@Entity
@SequenceGenerator(name="seq_professor", sequenceName = "seq_professor", allocationSize = 1, initialValue = 1)
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="seq_professor")
	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private String matricula;
	@Enumerated(EnumType.STRING)
	private Titulacao titulacao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public Titulacao getTitulacao() {
		return titulacao;
	}
	public void setTitulacao(Titulacao titulacao) {
		this.titulacao = titulacao;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
}
