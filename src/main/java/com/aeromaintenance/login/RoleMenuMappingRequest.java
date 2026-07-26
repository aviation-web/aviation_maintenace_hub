package com.aeromaintenance.login;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuMappingRequest {
	private String roleId;
	private String menuId;
	private boolean accessible;
	

}
