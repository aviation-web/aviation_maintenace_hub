package com.aeromaintenance.login;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByParentIsNull();  // Root menus (without parent)
    List<Menu> findByParent(Menu parent);  // Submenus for a given parent
	Menu findById(String menuId);
}
