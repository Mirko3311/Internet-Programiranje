package net.etf.ip.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.etf.ip.service.GenericService;

public class LoginBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private boolean isLoggedIn = false;
	private UserResponse currentUser;
	
	private GenericService<UserResponse> authService = new GenericService<>();
	
	public LoginBean() {}
	
	public boolean logIn()
	{
		try
		{
		UserRequest request = new  UserRequest(username, password);
		
		this.currentUser = authService.post("/auth/login", request, UserResponse.class);
		
		if(this.currentUser !=null)
		{
			System.out.println("[DEBUG] Login uspješan!");
            System.out.println("[DEBUG] Dobijeni ID: " + currentUser.getId());
            System.out.println("[DEBUG] Uloge korisnika: " + currentUser.getRoles());
            boolean isCompany =  currentUser.getRoles().stream().anyMatch(role -> role.equals("COMPANY") || role.equals("ROLE_COMPANY"));           
			//this.isLoggedIn = true;
			//return true;
            if (!isCompany) {
                System.err.println("[SECURITY] Korisnik nije kompanija! Pristup odbijen.");
                this.currentUser = null; // Poništavamo usera jer mu ne damo ulaz ovdje
                this.isLoggedIn = false;
                return false; 
            }

            System.out.println("[DEBUG] Login uspješan za kompaniju!");
            this.isLoggedIn = true;
            return true;
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	
	public boolean changePassword(String oldPassword, String newPassword)
	{
		System.out.print("Poziv changePassword metode");
		try
		{
			Map<String,String> data = new HashMap<>();
			data.put("oldPassword", oldPassword);
			data.put("newPassword",newPassword);
			return authService.post("/auth/change-password",data,Boolean.class)!=null;
		}catch(Exception e)
		{
		  e.printStackTrace();	
		  return false;
		}
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public UserResponse getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserResponse currentUser) {
		this.currentUser = currentUser;
	}

	public GenericService<UserResponse> getAuthService() {
		return authService;
	}

	public void setAuthService(GenericService<UserResponse> authService) {
		this.authService = authService;
	}

}
