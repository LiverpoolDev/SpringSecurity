package com.grokonez.jwtauthentication.repository;

import com.grokonez.jwtauthentication.model.Dog;
import com.grokonez.jwtauthentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    public Dog getDogByUser(User user);
}
