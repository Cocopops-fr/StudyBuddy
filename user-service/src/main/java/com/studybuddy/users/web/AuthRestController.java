package com.studybuddy.users.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.studybuddy.users.User;
import com.studybuddy.users.UserEntity;
import com.studybuddy.users.UserService;
import com.studybuddy.users.dto.EditProfileForm;
import com.studybuddy.users.dto.LoginForm;
import com.studybuddy.users.dto.RegisterForm;

@Controller
public class AuthRestController {

    // on stocke l'EMAIL dans la session
    public static final String SESSION_USER = "USER_EMAIL";

    private final UserService users;

    public AuthRestController(UserService users) {
        this.users = users;
    }

    // ACCUEIL : login + inscription
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        if (session.getAttribute(SESSION_USER) != null) {
            return "redirect:/profile";
        }

        if (!model.containsAttribute("loginForm")) {
            model.addAttribute("loginForm", new LoginForm("", ""));
        }

        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm(
                    "", "", "",
                    null, "", "", ""
            ));
        }

        return "index";
    }

    // INSCRIPTION
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
                           BindingResult bindingResult,
                           Model model,
                           HttpSession session) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("loginForm", new LoginForm("", ""));
            model.addAttribute("registerForm", registerForm);
            model.addAttribute("registerError", "Vérifie les champs du formulaire.");
            return "index";
        }

        try {
            UserEntity u = users.register(registerForm);
            session.setAttribute(SESSION_USER, u.getEmail());
            return "redirect:/profile";
        } catch (IllegalArgumentException e) {
            model.addAttribute("loginForm", new LoginForm("", ""));
            model.addAttribute("registerForm", registerForm);
            model.addAttribute("registerError", e.getMessage());
            return "index";
        }
    }

    // CONNEXION (EMAIL + MOT DE PASSE)
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult bindingResult,
                        Model model,
                        HttpSession session) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("loginForm", loginForm);
            model.addAttribute("registerForm", new RegisterForm(
                    "", "", "",
                    null, "", "", ""
            ));
            model.addAttribute("loginError", "Vérifie les champs du formulaire.");
            return "index";
        }

        return users.authenticate(loginForm)
                .map(user -> {
                    session.setAttribute(SESSION_USER, user.getEmail());
                    return "redirect:/profile";
                })
                .orElseGet(() -> {
                    model.addAttribute("loginForm", loginForm);
                    model.addAttribute("registerForm", new RegisterForm(
                            "", "", "",
                            null, "", "", ""
                    ));
                    model.addAttribute("loginError", "Identifiants invalides");
                    return "index";
                });
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // PROFIL
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        Object emailObj = session.getAttribute(SESSION_USER);
        if (emailObj == null) {
            return "redirect:/";
        }

        String email = emailObj.toString();

        users.findByEmail(email).ifPresent(userEntity -> {
            User dto = new User(
                    userEntity.getId(),
                    userEntity.getFirstName(),
                    userEntity.getLastName(),
                    userEntity.getBirthDate(),
                    userEntity.getUniversity(),
                    userEntity.getEmail(),
                    userEntity.getGender(),
                    userEntity.getCreatedAt()
            );
            model.addAttribute("user", dto);

            // On passe l'ID au modèle
            model.addAttribute("currentStudentId", userEntity.getId());

            // URL dynamique vers le microservice interactions
            String matchesUrl = "http://localhost:8082/profile?userId=" + userEntity.getId();
            model.addAttribute("matchesUrl", matchesUrl);
        });


        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, HttpSession session) {
        Object emailObj = session.getAttribute(SESSION_USER);
        if (emailObj == null) {
            return "redirect:/";
        }
        String email = emailObj.toString();

        return users.findByEmail(email)
                .map(user -> {
                    if (!model.containsAttribute("editForm")) {
                        EditProfileForm form = new EditProfileForm(
                                user.getFirstName(),
                                user.getLastName(),
                                user.getBirthDate(),
                                user.getUniversity(),
                                user.getEmail(),
                                user.getGender()
                        );
                        model.addAttribute("editForm", form);
                    }
                    return "profile-edit";
                })
                .orElse("redirect:/");
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@Valid @ModelAttribute("editForm") EditProfileForm editForm,
                                BindingResult bindingResult,
                                Model model,
                                HttpSession session) {

        Object emailObj = session.getAttribute(SESSION_USER);
        if (emailObj == null) {
            return "redirect:/";
        }
        String currentEmail = emailObj.toString();

        if (bindingResult.hasErrors()) {
            model.addAttribute("editError", "Vérifie les champs du formulaire.");
            return "profile-edit";
        }

        try {
            UserEntity updated = users.updateProfile(currentEmail, editForm);
            session.setAttribute(SESSION_USER, updated.getEmail());
            return "redirect:/profile";
        } catch (IllegalArgumentException e) {
            model.addAttribute("editError", e.getMessage());
            return "profile-edit";
        }
    }

    @PostMapping("/profile/delete")
    public String deleteAccount(HttpSession session) {
        Object emailObj = session.getAttribute(SESSION_USER);
        if (emailObj != null) {
            String email = emailObj.toString();
            users.deleteAccount(email);
        }
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping({"/login", "/register"})
    public String redirectToIndex() {
        return "redirect:/";
    }
}
