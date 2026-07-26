package com.aeromaintenance.login;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuDTO {
	 private Long id;
	    private MenuDTO menu;
	    private boolean accessibleFlag;
}
