package com.projeto.ads.model;

import javax.persistence.Entity;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.projeto.ads.model.Usuario;
@Entity // Criar a tabela
@Table(name="roles") // Criar uma tabela com um nome que eu quero
public class Role {
	
@Id // Para dizer que essa é a chave primária do nosso banco
@GeneratedValue(strategy = GenerationType.IDENTITY) //Utiliza a estratégia de identidade para geração automática de valores
private Long id;

@Column(unique = true) // indica que o campo deve ser único na tabela
private String nome;

@OneToMany(mappedBy = "role")
private List<Usuario> usuarios;// fazer um relacionamento com a chave estrangeira na table usuarios

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

public List<Usuario> getUsuarios() {
	return usuarios;
}

public void setUsuarios(List<Usuario> usuarios) {
	this.usuarios = usuarios;
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
	Role other = (Role) obj;
	return Objects.equals(id, other.id);
}

}// fim class
