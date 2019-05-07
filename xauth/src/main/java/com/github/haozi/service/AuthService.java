package com.github.haozi.service;

import com.github.haozi.domain.Auth;
import com.github.haozi.repository.AuthRepository;
import com.github.haozi.service.dto.AuthDTO;
import com.github.haozi.service.mapper.AuthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Auth.
 */
@Service
@Transactional
public class AuthService {

    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final AuthRepository authRepository;

    private final AuthMapper authMapper;

    public AuthService(AuthRepository authRepository, AuthMapper authMapper) {
        this.authRepository = authRepository;
        this.authMapper = authMapper;
    }

    /**
     * Save a auth.
     *
     * @param authDTO the entity to save
     * @return the persisted entity
     */
    public AuthDTO save(AuthDTO authDTO) {
        log.debug("Request to save Auth : {}", authDTO);
        Auth auth = authMapper.toEntity(authDTO);
        auth = authRepository.save(auth);
        return authMapper.toDto(auth);
    }

    /**
     * Get all the auths.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AuthDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Auths");
        return authRepository.findAll(pageable)
            .map(authMapper::toDto);
    }

    /**
     * Get all the Auth with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<AuthDTO> findAllWithEagerRelationships(Pageable pageable) {
        return authRepository.findAllWithEagerRelationships(pageable).map(authMapper::toDto);
    }
    

    /**
     * Get one auth by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AuthDTO> findOne(Long id) {
        log.debug("Request to get Auth : {}", id);
        return authRepository.findOneWithEagerRelationships(id)
            .map(authMapper::toDto);
    }

    /**
     * Delete the auth by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Auth : {}", id);
        authRepository.deleteById(id);
    }
}
