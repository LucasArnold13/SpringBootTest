package com.lucas.testProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.lucas.testProject.dto.UserDTO;
import com.lucas.testProject.model.User;
import com.lucas.testProject.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private String path = "pages/user/";
    @Autowired
    UserService userService;

    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("fragment", String.format("%s/%s", path, "users"));
        return "layout";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("fragment", path + "user");
        return "layout";
    }

    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("fragment", path + "createUser");
        return "layout";
    }

    @PostMapping("/")
    public String addUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "Fehler: " + result.getAllErrors();
        }

        User user = new User(
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword());

        User newUser = userService.saveOrUpdateUser(user);

        return "redirect:/" + path + "users/" + newUser.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

}