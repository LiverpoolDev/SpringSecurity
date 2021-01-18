package com.grokonez.jwtauthentication.controller;

import com.grokonez.jwtauthentication.model.Cat;
import com.grokonez.jwtauthentication.model.User;
import com.grokonez.jwtauthentication.repository.CatRepository;
import com.grokonez.jwtauthentication.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cat")
@AllArgsConstructor

public class CatController {
    private CatRepository catRepository;
    private UserRepository userRepository;

    @PostMapping
    public Cat saveCat(@RequestBody Cat cat, Principal principal){
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(()-> new RuntimeException("Fail! -> Cause: User Role not find."));
        cat.setUser(user);
       return catRepository.save(cat);
    }
    @GetMapping
    public List<Cat> getCat(Principal principal){
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(()-> new RuntimeException("Fail! -> Cause: User Role not find."));
//        return catRepository.getCatByUser(user);

    }
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('PM')")
    public String deleteCat(@PathVariable Long id){
        catRepository.deleteById(id);
        return "Cat with id " + id + " delete";
    }
    @PutMapping("/{idcat}/{iduser}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public Cat updateCat(@PathVariable Long idcat, @RequestBody Cat cat, @PathVariable Long iduser){
        Cat oldcat = catRepository.findById(idcat).orElseThrow(()-> new RuntimeException("Fail! -> Cause: User Role not find."));
        User user = userRepository.findById(iduser).orElseThrow(()-> new RuntimeException("Fail! -> Cause: User Role not find."));
        oldcat.setUser(cat.getUser());
        oldcat.setName(cat.getName());
        oldcat.setAge(cat.getAge());
        oldcat.setUser(user);
        return catRepository.save(oldcat);
    }
}
