package com.unla.gestionUsuario.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.gestionUsuario.entities.User;
import com.unla.gestionUsuario.exceptions.UsernameOrIdNotFound;
import com.unla.gestionUsuario.repository.UserRepository;
import com.unla.gestionUsuario.service.IUserService;

@Service
public class UserService implements IUserService{
	
	@Autowired
	private UserRepository userRepositorio;
	
	@Override
	public User getUserById(String i) throws UsernameOrIdNotFound {
		return userRepositorio.findById(i).orElseThrow(() -> new UsernameOrIdNotFound("El Id del usuario no existe."));
	}

	@Override
	public User createUser(User user) throws Exception {
		
		user.setBlockAmount(0);
		user.setState(true);
		
		userRepositorio.save(user);
		
		return user;
	}
}
