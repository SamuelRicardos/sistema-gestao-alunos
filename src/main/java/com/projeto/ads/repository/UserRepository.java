package com.projeto.ads.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.ads.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findByUsername(String username);
	
	public Usuario findByEmail(String email);
	
}
