package com.aeromaintenance.login;

import java.util.ArrayList;
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
public class ParentMenuDTO {
	 private Long id;
	    private String name;
	    private String path;
	    private String component;
	    private List<SubMenuDTO> subMenus = new ArrayList<>();

}
