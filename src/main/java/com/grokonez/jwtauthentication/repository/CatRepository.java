package com.grokonez.jwtauthentication.repository;

import com.grokonez.jwtauthentication.model.Cat;
import com.grokonez.jwtauthentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    public List<Cat> getCatByUser(User user);
}
