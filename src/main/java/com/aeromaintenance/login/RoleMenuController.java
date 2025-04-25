package com.aeromaintenance.login;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
//@CrossOrigin(origins = "http://localhost:5173")
public class RoleMenuController {
	
	@Autowired
    private MenuService menuService;
	
	@Autowired
    private LoginService loginService;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private MenuRepository menuRepository;
    
    @Autowired
    private RoleMenuMappingRepository roleMenuMappingRepository;

    @GetMapping("/viewUser")
    public List<Login> getAllUser() {
        return loginService.getAllUser();
    }
	
	  //Register user 
	 @PostMapping("/addUser") 
	 public ResponseEntity<String>   addUser(@RequestBody Login user) { 
		 System.out.println("Creating user: " + user);
		 loginService.addUser(user); 
	      return ResponseEntity.ok("User Registered Successfully!"); 
	  }
    
    @PostMapping("/addRole")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> createRole(@RequestBody Role role, @AuthenticationPrincipal UserDetails userDetails) {
        Role createdRole = roleRepository.save(role);
        return ResponseEntity.ok(createdRole);
    }
    
    @GetMapping("/menus")
    public ResponseEntity<List<MenuDTO>> getRootMenusWithSubMenus() {
        return ResponseEntity.ok(menuService.getRootMenusWithSubMenus());
    }

    @GetMapping("/role")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<RoleDTO> roleDTOs = roles.stream()
                .map(role -> new RoleDTO(role.getId(), role.getRoleName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(roleDTOs);
    }
    
    @PostMapping("/saveMapping")
    public ResponseEntity<String> saveRoleMenuMapping(@RequestBody List<RoleMenuMappingRequest> mappings) {
    	menuService.saveMappings(mappings);
        return ResponseEntity.ok("Mappings saved successfully.");
    }
    
    @GetMapping("/roleMenus/{roleId}")
    public ResponseEntity<List<MenuDTO>> getMenusByRole(@PathVariable Long roleId) {
    	List<MenuDTO> menus = menuService.getMenuByRole(roleId); // This will return parent + submenus
        return ResponseEntity.ok(menus);
    }
    
    @GetMapping("/byname/{role}")
    public ResponseEntity<RoleDTO> getRoleByName(@PathVariable String role) {
    	Optional<Role> roleOptional = roleRepository.findByRoleName(role);
        Role roles = roleOptional.orElseThrow(() -> new RuntimeException("Roles not found"));

        return ResponseEntity.ok(new RoleDTO(roles.getId(), roles.getRoleName()));
    }
}