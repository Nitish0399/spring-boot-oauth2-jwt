package com.example.oauth2.service;

import java.util.List;

import com.example.oauth2.model.UserInfo;
import com.example.oauth2.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserInfoService {

	@Autowired
	private UserDetailsRepository userDatailsRepository;

	public UserInfo getUserInfoByUserName(String userName) {
		return userDatailsRepository.findByUserName(userName);
	}

	public List<UserInfo> findAllUsers() {
		return userDatailsRepository.findAll();
	}

	public UserInfo getUserInfoById(Integer id) {
		return userDatailsRepository.findById(id);
	}

	public String addUser(UserInfo userInfo) {
		if(userDatailsRepository.findByUserName(userInfo.getUserName()) !=null){
			return "Username Already Exists";
		}
		userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
		userDatailsRepository.save(userInfo);

		return "User Created";
	}

	public UserInfo updateUser(Integer id, UserInfo userRecord) {
		UserInfo userInfo = userDatailsRepository.findById(id);
		userInfo.setUserName(userRecord.getUserName());
		userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
		userInfo.setRole(userRecord.getRole());
		return userDatailsRepository.save(userInfo);
	}

	public void deleteUser(Integer id) {
		userDatailsRepository.deleteById(id);
	}

	public UserInfo updatePassword(Integer id, UserInfo userRecord) {
		UserInfo userInfo = userDatailsRepository.findById(id);
		userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
		return userDatailsRepository.save(userInfo);
	}

	public UserInfo updateRole(Integer id, UserInfo userRecord) {
		UserInfo userInfo = userDatailsRepository.findById(id);
		userInfo.setRole(userRecord.getRole());
		return userDatailsRepository.save(userInfo);
	}
}