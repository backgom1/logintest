package com.ssw.fssw.repository;

import com.ssw.fssw.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserApiRepository extends JpaRepository<User, Long> {

    User findByEmailId(String emailId);

}
