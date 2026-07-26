package com.aeromaintenance.login;

import java.util.Date;
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
public class MenuDTO {
	 	private Long id;
	    private String name;
	    private String path;
	    private String component;
	    private String icon;
	    private List<MenuDTO> subMenus;
	}
	
	/*
	 * private Long id; private String name; private List<MenuDTO> subMenus; //
	 * private List<SubMenuDTO> subMenus;
	 */
