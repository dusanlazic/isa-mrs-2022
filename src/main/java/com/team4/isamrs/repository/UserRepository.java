package com.team4.isamrs.repository;

import com.team4.isamrs.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
