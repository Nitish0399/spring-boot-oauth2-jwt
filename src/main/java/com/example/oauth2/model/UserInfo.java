package com.example.oauth2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 25)
	private Integer id;

	@Column(name = "username", length = 50, unique=true)
	private String userName;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "password", length = 800)
	private String password;

	@Column(name = "role", length = 50)
	private String role;


	@Override
	public String toString() {
		return String.format("UserInfo [id=%s, userName=%s, password=%s, role=%s]", id, userName, password,
				role);
	}

}
