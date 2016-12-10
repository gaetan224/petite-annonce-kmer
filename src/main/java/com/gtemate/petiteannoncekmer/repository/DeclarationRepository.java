package com.gtemate.petiteannoncekmer.repository;

import com.gtemate.petiteannoncekmer.domain.Declaration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Declaration entity.
 */
@SuppressWarnings("unused")
public interface DeclarationRepository extends JpaRepository<Declaration,Long>,JpaSpecificationExecutor<Declaration> {

    @Query("select declaration from Declaration declaration where declaration.owner.login = ?#{principal.username}")
    Page<Declaration> findByOwnerIsCurrentUser(Pageable pageable);

}
