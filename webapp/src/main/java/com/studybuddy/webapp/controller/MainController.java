package com.studybuddy.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.ui.Model;



@Controller
public class MainController {

    private final String usersServiceUrl;
    private final String interactionsServiceUrl;

    public MainController(
            @Value("${users.service.url}") String usersServiceUrl,
            @Value("${interactions.service.url}") String interactionsServiceUrl
    ) {
        this.usersServiceUrl = usersServiceUrl;
        this.interactionsServiceUrl = interactionsServiceUrl;
    }

    
    
    
    
    /**
     * Affiche la page d'accueil, qui contient le menu principal.
     */
    @GetMapping({"/", "/home"})
    public String homePage(Model model) {

        model.addAttribute("interactionsServiceUrl", interactionsServiceUrl);
        model.addAttribute("usersServiceUrl", usersServiceUrl); // 👈 ajouter ça

        return "home";
    }


    /**
     * Affiche la page pour gérer les utilisateurs.
     * Cette page sera notre point d'entrée pour les fonctionnalités du microservice Utilisateur.
     */
    @GetMapping("/users/manage")
    public String manageUsersPage(Model model) {
    	model.addAttribute("usersServiceUrl", usersServiceUrl);
        return "manage_users";
    }
}