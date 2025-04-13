package com.aeromaintenance.login;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Entity
@Table(name = "role_menu_mapping")
public class RoleMenuMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "roleId")
	 * 
	 * @JsonBackReference private Role role;
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "menuId")
	 * 
	 * @JsonBackReference private Menu menu;
	 */
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    //@JsonBackReference(value = "role-roleMenu")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId")
    //@JsonBackReference(value = "menu-roleMenu")
    private Menu menu;
    
    private boolean accessibleFlag;
    
    public RoleMenuMapping(Role role, Menu menu) {
        this.role = role;
        this.menu = menu;
    }

    // Getters and Setters
}