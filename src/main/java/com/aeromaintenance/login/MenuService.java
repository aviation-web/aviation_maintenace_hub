package com.aeromaintenance.login;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;
    
    @Autowired
    private RoleMenuMappingRepository mappingRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<MenuDTO> getRootMenusWithSubMenus() {
    	List<Menu> rootMenus = menuRepository.findRootMenus();
        List<MenuDTO> rootMenuDTOs = new ArrayList<>();

        for (Menu menu : rootMenus) {
            List<Menu> subMenus = menuRepository.findSubMenusByParentId(menu.getId());
            List<MenuDTO> subMenuDTOs = subMenus.stream()
                .map(sub -> new MenuDTO(sub.getId(), sub.getName(),sub.getPath(), sub.getComponent(), new ArrayList<>()))
                .collect(Collectors.toList());

            MenuDTO menuDTO = new MenuDTO(menu.getId(), menu.getName(),menu.getPath(), menu.getComponent(), subMenuDTOs);
            rootMenuDTOs.add(menuDTO);
        }

        return rootMenuDTOs;
    }
    
        public void saveMappings(List<RoleMenuMappingRequest> mappings) {
            for (RoleMenuMappingRequest dto : mappings) {
            	if(dto.getRoleId() != null && dto.getMenuId() != null){
            	 Long roleId = Long.parseLong(dto.getRoleId());
                Optional<Role> roleOptional=roleRepository.findById(roleId);
                Role role = roleOptional.orElseThrow(() -> new RuntimeException("Roles not found"));

                Long menuId = Long.parseLong(dto.getMenuId());
                Optional<Menu> menuOptional = menuRepository.findById(menuId);
                Menu menu = menuOptional.orElseThrow(() -> new RuntimeException("Menu not found"));

                RoleMenuMapping mapping = mappingRepository.findByRoleAndMenu(role, menu)
                    .orElse(new RoleMenuMapping(role, menu));

                mapping.setAccessibleFlag(dto.isAccessible());
                mappingRepository.save(mapping);
            	}
            }
        }
        
        public List<MenuDTO> getMenuByRole(Long roleId) {
        	List<Object[]> results = menuRepository.findMenusByRoleId(roleId);
            
        	Map<Long, MenuDTO> menuMap = new LinkedHashMap<>();

        	for (Object[] result : results) {
        	    Long parentId = ((Number) result[0]).longValue();
        	    String parentName = (String) result[1];
        	    String parentPath = (String) result[2];
        	    String parentComponent = (String) result[3];

        	    Long submenuId = result[4] != null ? ((Number) result[4]).longValue() : null;
        	    String submenuName = (String) result[5];
        	    String submenuPath = (String) result[6];
        	    String submenuComponent = (String) result[7];

        	    MenuDTO parentMenu = menuMap.computeIfAbsent(
        	        parentId,
        	        id -> new MenuDTO(id, parentName, parentPath, parentComponent, new ArrayList<>())
        	    );

        	    if (submenuId != null && submenuName != null) {
        	        boolean alreadyExists = parentMenu.getSubMenus().stream()
        	            .anyMatch(sub -> sub.getId().equals(submenuId));
        	        if (!alreadyExists) {
        	            MenuDTO submenu = new MenuDTO(submenuId, submenuName, submenuPath, submenuComponent, new ArrayList<>());
        	            parentMenu.getSubMenus().add(submenu);
        	        }
        	    }
        	}
System.out.println(menuMap.values());
        	return new ArrayList<>(menuMap.values());
        }

		private Long getLong(Object obj) {
		        if (obj instanceof BigInteger) {
		            return ((BigInteger) obj).longValue();
		        } else if (obj instanceof Number) {
		            return ((Number) obj).longValue();
		        } else {
		            return null;
		        }
		}

        
}


