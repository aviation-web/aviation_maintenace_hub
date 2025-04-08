package com.aeromaintenance.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:5173")
public class RoleMenuController {

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private MenuRepository menuRepository;
    
    @Autowired
    private RoleMenuMappingRepository roleMenuMappingRepository;

    // Get roles and their accessible menus
    @GetMapping("/role")
    public ResponseEntity<List<Role>> getRolesWithMenus() {
        List<Role> roles = roleRepository.findAll();  // Fetch all roles
        return ResponseEntity.ok(roles);
    }
    
    @PostMapping("/addRole")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> createRole(@RequestBody Role role, @AuthenticationPrincipal UserDetails userDetails) {
        Role createdRole = roleRepository.save(role);
        return ResponseEntity.ok(createdRole);
    }

    // Get root menus (menus with no parent) and their submenus
    @GetMapping("/menus")
    public ResponseEntity<List<Menu>> getMenus() {
        List<Menu> rootMenus = menuRepository.findByParentIsNull();  // Fetch root menus
        return ResponseEntity.ok(rootMenus);
    }

    // Save role-menu mapping (mapping roles to menus and submenus)
    @PostMapping("/saveMapping")
    public ResponseEntity<Void> saveRoleMenuMapping(@RequestBody List<RoleMenuMappingRequest> mappings) {
        for (RoleMenuMappingRequest mapping : mappings) {
            RoleMenuMapping roleMenuMapping = new RoleMenuMapping();
            Role role = roleRepository.findById(mapping.getRoleId());
            Menu menu = menuRepository.findById(mapping.getMenuId());
            roleMenuMapping.setRole(role);
            roleMenuMapping.setMenu(menu);
            roleMenuMapping.setAccessibleFlag(mapping.isAccessible());

            roleMenuMappingRepository.save(roleMenuMapping);
        }
        return ResponseEntity.ok().build();
    }
}