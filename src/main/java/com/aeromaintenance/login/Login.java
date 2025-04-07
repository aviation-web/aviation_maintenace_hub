package com.aeromaintenance.login;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_login")

public class Login {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;	
 	
 	@Column(nullable = false, length = 50)
 	private String firstName;
 	 
 	@Column(nullable = false, length = 50)
    private String middleName;
 	 
    @Column(nullable = false, length = 255)
    private String lastName;
 	 
 	@Column(nullable = false, length = 50,unique = true)
    private String username;
 	 
	/*
	 * @ManyToMany(fetch = FetchType.EAGER)
	 * 
	 * @JoinTable( name = "user_roles", // the name of the join table joinColumns
	 * = @JoinColumn(name = "user_id"), // foreign key to user_login table
	 * inverseJoinColumns = @JoinColumn(name = "role_id") // foreign key to role
	 * table ) private Set<Role> roles;
	 */
 	 
 	@Column(name = "DateOfBirth", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date dob;
 	 
 	@Column(nullable = false, length = 12)
    private String mobileNumber;
 	 
 	@Column(nullable = false, length = 50)
    private String email;
 	 
 	@Column(nullable = false, length = 100)
    private String address;
 	 
 	@Column(nullable = false, length = 50)
    private String city;
 	 
 	@Column(nullable = false, length = 50)
    private String  state;
 	 
 	@Column(nullable = false, length = 50)
    private String country;

 	@Column(length = 100)
    private String password;
 	
 	@Column
 	private boolean passwordExpired;
 	
 	@Column(nullable = false, length = 50)
    private String role;

    // Getters and setters
}
