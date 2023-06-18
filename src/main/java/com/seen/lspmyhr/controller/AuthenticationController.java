package com.seen.lspmyhr.controller;

import com.seen.lspmyhr.model.UserApp;
import com.seen.lspmyhr.repository.UserAppRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping()
public class AuthenticationController {

    private final UserAppRepository userAppRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/authentication")
    public String loginView() {
        return "authentication";
    }

    @GetMapping("/registration")
    public String registrationView(Model model){
        UserApp userApp = new UserApp();
        model.addAttribute("userApp", userApp);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute UserApp userApp){
        try{
            userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));
            userAppRepository.save(userApp);
            return "redirect:/authentication";
        }catch (Exception e){
            return "redirect:/registration?error=true";
        }
    }
}
