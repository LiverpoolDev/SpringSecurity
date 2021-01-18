package com.grokonez.jwtauthentication.controller;


import com.grokonez.jwtauthentication.model.Dog;
import com.grokonez.jwtauthentication.model.Fish;
import com.grokonez.jwtauthentication.model.User;
import com.grokonez.jwtauthentication.repository.FishRepository;
import com.grokonez.jwtauthentication.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequestMapping("/api/fish")
@RestController
@AllArgsConstructor
public class FishController {

    private FishRepository fishRepository;
    private UserRepository userRepository;

    @PostMapping
    public Fish saveFish(@RequestBody Fish fish, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        fish.setUser(user);
        return fishRepository.save(fish);
    }

    @GetMapping
    public Dog getFishByUser(@RequestBody Fish fish, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        return fishRepository.getFishByUser(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('PM')")
    public String deleteFish(@PathVariable Long id) {
        fishRepository.deleteById(id);
        return "Fish with id " + id + " delete";

    }

    @PutMapping("/{idfish}/{iduser}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public Fish updateFish(@PathVariable Long idfish, @RequestBody Fish fish, @PathVariable Long iduser) {
        Fish oldfish = fishRepository.findById(idfish).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        User user = userRepository.findById(iduser).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        oldfish.setUser(fish.getUser());
        oldfish.setName(fish.getName());
        oldfish.setAge(fish.getAge());
        oldfish.setUser(user);
        return fishRepository.save(oldfish);
    }
}
