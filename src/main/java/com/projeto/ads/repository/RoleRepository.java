package com.projeto.ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.ads.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

public Role findByNome(String papel);

}// fim interface
