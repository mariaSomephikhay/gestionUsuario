package com.unla.gestionUsuario.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.gestionUsuario.entities.User;
import com.unla.gestionUsuario.entities.UserLog;
import com.unla.gestionUsuario.exceptions.UserException;
import com.unla.gestionUsuario.repository.UserRepository;
import com.unla.gestionUsuario.repository.UserLogRepository;
import com.unla.gestionUsuario.service.IUserService;

@Service
public class UserService implements IUserService{
	
	@Autowired
	private UserRepository userRepositorio;
	
	@Autowired
	private UserLogRepository UserLogRepository;
	
	@Override
	public User getUserById(String id) throws UserException {
		return userRepositorio.findById(id).orElseThrow(() -> UserException.of(UserException.Type.USER_NOT_FOUND));
	}
	
	@Override
	public Boolean loginUser(User user) throws Exception{
		Boolean login = false;
		User userLogin = this.getUserById(user.getId());
		
		if(userLogin.getPassword().equals(user.getPassword())) {
			UserLog userLog = new UserLog();
			userLog.setUser(userLogin);
			userLog.setEvent("0");
			userLog.setDescription("Usuario logeado");
			UserLogRepository.save(userLog);
		}else {
			throw UserException.of(UserException.Type.INVALID_PASSWORD);
			//TODO: sumar contador de contraseña invalida
		}
		
		return login;
	}

	@Override
	public User createUser(User user) throws Exception {
		
		user.setBlockAmount(0);
		user.setState(true);
		
		userRepositorio.save(user);
		
		return user;
	}
}
