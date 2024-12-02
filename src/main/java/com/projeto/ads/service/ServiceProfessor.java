package com.projeto.ads.service;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.projeto.ads.model.Aluno;
import com.projeto.ads.model.Professor;
import com.projeto.ads.repository.ProfessorRepository;

@Service
public class ServiceProfessor {

	@Autowired
	ProfessorRepository professorRepository;
	
	public String validaProfessor(String email, String cpf) {
		
		String out=null;
		Professor aux = professorRepository.findByCpf(cpf);
		Professor aux2 = professorRepository.findByEmail(email);
		if(aux!=null && aux.getCpf().equals(cpf)) {
			out="Já existe um professor com esse cpf!";
		}
		
		if(aux != null && aux.getEmail().equals(email)) {
			if(out != null) {
				out = out + "também com esse email!!";
			} else {
				out="Já existe um professor com esse cpf!";
			}
			
			return out;
		}
		
		if(aux!= null && aux2.getEmail().equals(email)) {
			out = "Já existe professor com esse email!!";
		}
		
		return out;
	}// fim valida
	
	public String alterarProfessor(Professor professor) {
	   Professor aux = professorRepository.findByCpf(professor.getCpf());
	   if(aux != null && aux.getId() == professor.getId() || aux == null) {
		   
		   Professor aux2 = professorRepository.findByEmail(professor.getEmail());
		   if(aux2 != null && aux2.getId() == professor.getId() || aux2 == null) {
			   return null;
		   } else {
			   return "Esse email já está cadastrado na base de dados!";
		   }
	   }//fim if do cpf
	   else {
		   return "Esse cpf já está cadastrado na base de dados!";
	   }
	}// fim alterar
	
	public String gerarMat() {
		Date data = new Date();
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);
		int ano = calendario.get(Calendar.YEAR);
		Professor professor = professorRepository.findLastInsertedProfessor();

		if (professor == null) {
			return ano + "1";
		} else {
			String out = ano + "";
			return out + (professor.getId() + 1);
		}

	} // fim cadastrarAluno
}
