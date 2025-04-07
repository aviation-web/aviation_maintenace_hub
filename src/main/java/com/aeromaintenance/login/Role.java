package com.aeromaintenance.login;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50,unique = true)
    private String roleName;
    
    private String roleDescription;
    
    @Column(nullable = false, length = 50,unique = true)
    private String roleCode;
    
    @OneToMany(mappedBy = "role")
    private List<RoleMenuMapping> roleMenuMappings;

    // Getters and Setters
}
