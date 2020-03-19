package com.shumile.springbootjpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Long> {

    List<User> findByNameOrderByCreateTimeDesc(String name);

}
