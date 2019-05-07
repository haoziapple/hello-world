package com.github.haozi.repository;

import com.github.haozi.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Menu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

    @Query(value = "select distinct menu from Menu menu left join fetch menu.roles left join fetch menu.templates left join fetch menu.auths",
        countQuery = "select count(distinct menu) from Menu menu")
    Page<Menu> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct menu from Menu menu left join fetch menu.roles left join fetch menu.templates left join fetch menu.auths")
    List<Menu> findAllWithEagerRelationships();

    @Query("select menu from Menu menu left join fetch menu.roles left join fetch menu.templates left join fetch menu.auths where menu.id =:id")
    Optional<Menu> findOneWithEagerRelationships(@Param("id") Long id);

}
