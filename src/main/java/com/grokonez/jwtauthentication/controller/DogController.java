package com.grokonez.jwtauthentication.controller;


import com.grokonez.jwtauthentication.model.Dog;
import com.grokonez.jwtauthentication.model.User;
import com.grokonez.jwtauthentication.repository.DogRepository;
import com.grokonez.jwtauthentication.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/dog")
@AllArgsConstructor
public class DogController {

    private DogRepository dogRepository;
    private UserRepository userRepository;

    @PostMapping
    public Dog saveDog(@RequestBody Dog dog, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        dog.setUser(user);
        return dogRepository.save(dog);
    }

    @GetMapping
    public Dog getDogByUser(@RequestBody Dog dog, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        return dogRepository.getDogByUser(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('PM')")
    public String deleteCat(@PathVariable Long id) {
        dogRepository.deleteById(id);
        return "Dog with id " + id + " delete";

    }

    @PutMapping("/{iddog}/{iduser}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public Dog updateDog(@PathVariable Long iddog, @RequestBody Dog dog, @PathVariable Long iduser) {
        Dog olddog = dogRepository.findById(iddog).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        User user = userRepository.findById(iduser).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        olddog.setUser(dog.getUser());
        olddog.setName(dog.getName());
        olddog.setAge(dog.getAge());
        olddog.setUser(user);
        return dogRepository.save(olddog);
    }
}
