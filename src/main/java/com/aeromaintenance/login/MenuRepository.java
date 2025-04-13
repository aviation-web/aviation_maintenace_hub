package com.aeromaintenance.login;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
	  // Root menus using derived query
    List<Menu> findByParentIsNull();  

    // Submenus of a parent using derived query
   // List<Menu> findByParent(Menu parent);
    Optional<Menu> findById(Long id);

    // Root menus with submenus eagerly fetched
    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.subMenus WHERE m.parent IS NULL")
    List<Menu> findRootMenusWithSubMenus();

    // Corrected queries below
    @Query("SELECT m FROM Menu m WHERE m.parent IS NULL")
    List<Menu> findRootMenus();

    @Query("SELECT m FROM Menu m WHERE m.parent.id = :parentId")
    List<Menu> findSubMenusByParentId(@Param("parentId") Long parentId);
    
    @Query(
    	    value = "SELECT DISTINCT " +
    	            "parent.id AS parent_id, " +
    	            "parent.name AS parent_name, " +
    	            "parent.path AS parent_path, " +
    	            "parent.component AS parent_component, " +
    	            "child.id AS submenu_id, " +
    	            "child.name AS submenu_name, " +
    	            "child.path AS submenu_path, " +
    	            "child.component AS submenu_component " +
    	            "FROM menu parent " +
    	            "LEFT JOIN menu child ON child.parent_id = parent.id " +
    	            "LEFT JOIN role_menu_mapping rm_parent ON rm_parent.menu_id = parent.id " +
    	            "    AND rm_parent.role_id = :roleId AND rm_parent.accessible_flag = 1 " +
    	            "LEFT JOIN role_menu_mapping rm_child ON rm_child.menu_id = child.id " +
    	            "    AND rm_child.role_id = :roleId AND rm_child.accessible_flag = 1 " +
    	            "WHERE parent.parent_id IS NULL " +                         
    	            "AND rm_parent.id IS NOT NULL " +                          
    	            "AND (child.id IS NULL OR rm_child.id IS NOT NULL) " +     
    	            "ORDER BY parent.id, child.id",
    	    nativeQuery = true)
    	List<Object[]> findMenusByRoleId(@Param("roleId") Long roleId);

    
   // @Query("SELECT m FROM RoleMenuMapping rmm JOIN rmm.menu m WHERE rmm.role.id = :roleId")
   // List<Menu> findMenusByRoleId(@Param("roleId") Long roleId);
	/*
	 * @Query( value = "SELECT DISTINCT " + "parent.id AS parent_id, " +
	 * "parent.name AS parent_name, " + "parent.path AS parent_path, " +
	 * "parent.component AS parent_component, " + "child.id AS submenu_id, " +
	 * "child.name AS submenu_name, " + "child.path AS submenu_path, " +
	 * "child.component AS submenu_component " + "FROM menu parent " +
	 * "LEFT JOIN menu child ON child.parent_id = parent.id " +
	 * "LEFT JOIN role_menu_mapping rm_parent ON rm_parent.menu_id = parent.id " +
	 * "    AND rm_parent.role_id = :roleId AND rm_parent.accessible_flag = 1 " +
	 * "LEFT JOIN role_menu_mapping rm_child ON rm_child.menu_id = child.id " +
	 * "    AND rm_child.role_id = :roleId AND rm_child.accessible_flag = 1 " +
	 * "WHERE (rm_parent.id IS NOT NULL OR rm_child.id IS NOT NULL) " +
	 * "  AND parent.parent_id IS NULL " + "ORDER BY parent.id, child.id",
	 * nativeQuery = true ) List<Object[]> findMenusByRoleId(@Param("roleId") Long
	 * roleId);
	 */
    
    @Query("SELECT DISTINCT m FROM RoleMenuMapping rm JOIN rm.menu m WHERE m.parent IS NULL AND rm.role.id = :roleId")
    List<Menu> findRootMenusByRole(@Param("roleId") Long roleId);
}

