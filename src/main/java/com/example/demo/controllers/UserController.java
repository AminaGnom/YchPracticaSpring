package com.example.demo.controllers;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/a")
    public String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "user-main";
    }


    @GetMapping("/{id}")
    public String detailView(@PathVariable Long id, Model model)
    {
        model.addAttribute("user",userRepository.findById(id).orElseThrow());
        return "user-main";
    }

    @GetMapping("/admins")
    public String userView(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }
    @GetMapping("/{id}/update")
    public String updView(@PathVariable Long id, Model model)
    {
        model.addAttribute("user",userRepository.findById(id).orElseThrow());
        model.addAttribute("role", Role.values());
        return "user-edit";
    }


    @PostMapping("/{id}/update")
    public String update_user(@RequestParam String username,
                              @RequestParam(name="roles[]", required = false) String[] roles,
                              @PathVariable Long id)
    {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(username);
        user.getRoles().clear();
        if(roles != null)
        {
            for(String role: roles)
            {
                user.getRoles().add(Role.valueOf(role));
            }
        }

        userRepository.save(user);
        return "redirect:/admin/admins";
    }
}