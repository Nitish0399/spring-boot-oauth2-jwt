package com.example.oauth2.contoller;

import java.util.List;

import com.example.oauth2.model.UserInfo;
import com.example.oauth2.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserInfoService userService;

	@GetMapping("")
	public Object getAllUser(@RequestHeader HttpHeaders requestHeader) {
		List<UserInfo> userInfos = userService.findAllUsers();
		if (userInfos == null || userInfos.isEmpty()) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return userInfos;
	}

	@PostMapping("")
	public String addUser(@RequestBody UserInfo userRecord) {

		return userService.addUser(userRecord);
	}

	@PutMapping("/{id}")
	public UserInfo updateUser(@RequestBody UserInfo userRecord, @PathVariable Integer id) {
		return userService.updateUser(id,userRecord);
	}
	
	@PutMapping("/changePassword/{id}")
	public UserInfo updateUserPassword(@RequestBody UserInfo userRecord, @PathVariable Integer id) {
		return userService.updatePassword(id,userRecord);
	}
	
	@PutMapping("/changeRole/{id}")
	public UserInfo updateUserRole(@RequestBody UserInfo userRecord, @PathVariable Integer id) {
		return userService.updateRole(id,userRecord);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserInfo> getUserById(@PathVariable Integer id) {
		UserInfo userInfo = userService.getUserInfoById(id);
		if (userInfo == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userInfo, HttpStatus.OK);
	}
}
