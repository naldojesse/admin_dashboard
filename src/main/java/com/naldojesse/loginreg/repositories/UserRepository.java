package com.naldojesse.loginreg.repositories;

import com.naldojesse.loginreg.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    User findByUsername(String username);
}
