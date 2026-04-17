package net.etf.ip.beans;

import java.io.Serializable;
import java.util.List;

public class UserResponse implements Serializable
{
	private Integer id;
	private String username;
	private List<String> roles;
	
	public UserResponse() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	
}
