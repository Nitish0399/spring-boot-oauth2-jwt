package com.example.oauth2.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.oauth2.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface UserDetailsRepository extends CrudRepository<UserInfo, String> {
	public UserInfo findByUserName(String userName);

	public List<UserInfo> findAll();

	public UserInfo findById(Integer id);

	public UserInfo save(UserInfo userInfo);

	public void deleteById(Integer id);
}
