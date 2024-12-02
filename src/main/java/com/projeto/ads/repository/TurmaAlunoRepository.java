package com.projeto.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.ads.model.Turma;
import com.projeto.ads.model.TurmaAluno;

@Repository
public interface TurmaAlunoRepository extends JpaRepository<TurmaAluno, Long> {

	// remove todas as relações de uma turma
	public void deleteByTurma(Turma turma);
	
}
