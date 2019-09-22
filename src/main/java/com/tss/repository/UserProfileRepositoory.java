package com.tss.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tss.model.UserProfile;

@Repository
public interface UserProfileRepositoory extends CrudRepository<UserProfile, Integer> {

}
