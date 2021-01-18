package com.grokonez.jwtauthentication.repository;

import com.grokonez.jwtauthentication.model.Dog;
import com.grokonez.jwtauthentication.model.Fish;
import com.grokonez.jwtauthentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishRepository extends JpaRepository<Fish, Long> {
    public Dog getFishByUser(User user);
}
