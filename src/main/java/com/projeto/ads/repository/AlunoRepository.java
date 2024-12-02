package com.projeto.ads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projeto.ads.Enum.Curso;
import com.projeto.ads.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{

	public Aluno findByCpf(String cpf);
	
	@Query("SELECT a FROM Aluno a WHERE a.id= (SELECT Max(a2.id) FROM Aluno a2)")
	public Aluno findLastInsertedAluno();
	
	@Query("SELECT a FROM Aluno a ORDER BY a.id")
	public List<Aluno> findAllByOrderedById();

	public List<Aluno> findAllByOrderByNomeAsc();
	
	@Query("SELECT a FROM Aluno a WHERE a.curso=:curso AND a.turno=:turno")
	public List<Aluno> buscarPorCursoETurno(@Param("curso") Curso curso, @Param("turno") String turno);
}
