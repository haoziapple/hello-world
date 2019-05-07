package com.github.haozi.repository;

import com.github.haozi.domain.Auth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Auth entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthRepository extends JpaRepository<Auth, Long>, JpaSpecificationExecutor<Auth> {

    @Query(value = "select distinct auth from Auth auth left join fetch auth.roles left join fetch auth.templates",
        countQuery = "select count(distinct auth) from Auth auth")
    Page<Auth> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct auth from Auth auth left join fetch auth.roles left join fetch auth.templates")
    List<Auth> findAllWithEagerRelationships();

    @Query("select auth from Auth auth left join fetch auth.roles left join fetch auth.templates where auth.id =:id")
    Optional<Auth> findOneWithEagerRelationships(@Param("id") Long id);

}
