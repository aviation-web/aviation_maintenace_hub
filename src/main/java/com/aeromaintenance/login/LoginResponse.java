package com.aeromaintenance.login;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

	private String token;
	private boolean passwordExpired;
	private String username;
	private String role;
}
