package com.projeto.ads.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projeto.ads.model.Usuario;
import com.projeto.ads.repository.RoleRepository;
import com.projeto.ads.repository.UserRepository;
import com.projeto.ads.service.ServiceEmail;
import com.projeto.ads.util.Util;

@Controller

public class UsuarioController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ServiceEmail serviceEmail;
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		mv.setViewName("Login/login");
		return  mv;
	}
	
	@GetMapping("/dashboard")
	public ModelAndView dashBoard(Authentication authentication) {
		ModelAndView mv = new ModelAndView();
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		String username = userDetails.getUsername();
		String papel = userDetails.getAuthorities().iterator().next().getAuthority();
		int atIndex = username.indexOf("@");// retorna a posição do @ ou -1 caso não encontre o @
		String nomeUser = atIndex != -1 ? username.substring(0, atIndex) : username;
		mv.addObject("usuario", nomeUser);
		mv.addObject("papel", papel);
		mv.setViewName("Login/index");
		return mv;
	}//fim método
	
	@GetMapping("/usuario/inserir")
	public ModelAndView cadastro() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("roles", roleRepository.findAll());
		mv.addObject("usuario", new Usuario());
		mv.setViewName("Login/cadastro");
		return mv;
	}//fim cadastro
	
	@PostMapping("/usuario/inserir")
	public ModelAndView salvarUser(
			@ModelAttribute Usuario usuario, 
			@RequestParam("confirmPassword") String confirmPass
			, @RequestParam("dataNasc") String dataNascimento, 
			@RequestParam("roleUser") String roller){
		
		ModelAndView mv = new ModelAndView();
		if(!usuario.getPassword().equals(confirmPass)) {
			mv.setViewName("Login/cadastro");
			mv.addObject("error", "As senhas não correspondem!");
			mv.addObject("roles", roleRepository.findAll());
			
			return mv;
		} //fim if
		
		if(usuario.getPassword().length() < 8) {
			mv.setViewName("Login/cadastro");
			mv.addObject("error", "A senha precisa ter no mínimo 8 caracteres");
			mv.addObject("roles", roleRepository.findAll());
			
			return mv;
		} //fim if
		
		Date dataFormatada = null;
		
		try {
		SimpleDateFormat formataEntrada = new SimpleDateFormat("yyyy-MM-dd");
		dataFormatada = formataEntrada.parse(dataNascimento);
		} catch(ParseException ps) {
			ps.printStackTrace();
		}// fim catch
		
		usuario.setDataNascimento(dataFormatada);
		usuario.setUsername(usuario.getEmail());
		Usuario aux = userRepository.findByUsername(usuario.getUsername());
		if(aux == null) {
			usuario.setRole(roleRepository.findByNome(roller));
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			
			userRepository.save(usuario);
			mv.setViewName("redirect:/login");
			
			return mv;
		} else {
			mv.setViewName("Login/cadastro");
			mv.addObject("error", "Usuário á cadastrado!");
			mv.addObject("roles", roleRepository.findAll());
			return mv;
		}//fim else

	}// fim metodo
	
	@GetMapping("/usuario/recuperarSenha")
	public ModelAndView recuperarSenha() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		mv.setViewName("Login/recuperar");
		return mv;
	}
	
	@PostMapping("/usuario/recuperarSenha")
	public ModelAndView recuperarSenhaPost(@ModelAttribute Usuario usuario, RedirectAttributes redirect) {
		ModelAndView mv = new ModelAndView();
		Usuario aux = userRepository.findByEmail(usuario.getEmail());
		
		if(aux == null) {
			mv.addObject("usuario", new Usuario());
			mv.setViewName("Login/recuperar");
			mv.addObject("error", "Email não encontrado");
			return mv;
		} // fim if null
		
		aux.setToken(Util.generateToken());
		userRepository.save(aux);
		String mensagem = "Use esse token para recuperar sua senha: " + aux.getToken();
		serviceEmail.sendEmail("senaclpoo@gmail.com", aux.getEmail(), mensagem, "Senac informa: recuperar senha plataforma gestão de aluno");
		usuario.setToken("");
		mv.addObject("usuario", aux);
		redirect.addFlashAttribute("usuario", usuario);
		mv.setViewName("redirect:/usuario/atualizarUsuario");
		return mv;
	}// fim recuperar
	
	@GetMapping("/usuario/atualizarUsuario")
	public ModelAndView updatePass(Usuario usuario) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Login/atualizar");
		return mv;
	}
	
	
	@PostMapping("/usuario/atualizarUsuario")
	public ModelAndView updatePassPost(@ModelAttribute Usuario usuario, @RequestParam("confirmPassword") String confirmPass, 
			@RequestParam("email") String email) {
		ModelAndView mv = new ModelAndView();
		Usuario aux = userRepository.findByEmail(email);
		if(!aux.getToken().equals(usuario.getToken())) {
			mv.setViewName("Login/atualizar");
			mv.addObject("error", "Token não confere - 1");
			mv.addObject("usuario", usuario);
			return  mv;
		}
		
		if(!usuario.getPassword().equals(confirmPass) || confirmPass.length() < 8) {
			String erro = confirmPass.length() < 8 ? "Senha precisa ter no mínimo tamanho 8" : "Senhas não conferem";
			mv.setViewName("Login/atualizar");
			mv.addObject("error", "Token não confere - 2");
			mv.addObject("usuario", usuario);
			return  mv;
		}
		
		aux.setToken("");
		aux.setPassword(passwordEncoder.encode(confirmPass));
		userRepository.save(aux);
		mv.addObject("usuario", new Usuario());
		mv.setViewName("redirect:/login");
		
		return mv;
		
	}
	
	
}// fim class
