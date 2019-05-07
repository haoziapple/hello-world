package com.github.haozi.repository;

import com.github.haozi.domain.Site;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Site entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteRepository extends JpaRepository<Site, Long>, JpaSpecificationExecutor<Site> {

}
