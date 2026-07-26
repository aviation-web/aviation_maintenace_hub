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
public class RoleDTO {
	private Long id;
    private String roleName;
    private String roleCode;
    private List<MenuDTO> menus;

    public RoleDTO(Long id, String name) {
        this.id = id;
        this.roleName = name;

    }
}
