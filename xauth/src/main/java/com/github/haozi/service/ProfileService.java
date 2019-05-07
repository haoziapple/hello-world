package com.github.haozi.service;

import com.github.haozi.domain.Profile;
import com.github.haozi.repository.ProfileRepository;
import com.github.haozi.service.dto.ProfileDTO;
import com.github.haozi.service.mapper.ProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Profile.
 */
@Service
@Transactional
public class ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    /**
     * Save a profile.
     *
     * @param profileDTO the entity to save
     * @return the persisted entity
     */
    public ProfileDTO save(ProfileDTO profileDTO) {
        log.debug("Request to save Profile : {}", profileDTO);
        Profile profile = profileMapper.toEntity(profileDTO);
        profile = profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    /**
     * Get all the profiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profiles");
        return profileRepository.findAll(pageable)
            .map(profileMapper::toDto);
    }

    /**
     * Get all the Profile with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ProfileDTO> findAllWithEagerRelationships(Pageable pageable) {
        return profileRepository.findAllWithEagerRelationships(pageable).map(profileMapper::toDto);
    }
    

    /**
     * Get one profile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProfileDTO> findOne(Long id) {
        log.debug("Request to get Profile : {}", id);
        return profileRepository.findOneWithEagerRelationships(id)
            .map(profileMapper::toDto);
    }

    /**
     * Delete the profile by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.deleteById(id);
    }
}
