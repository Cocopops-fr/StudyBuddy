package com.studybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    /**
     * Affiche la page d'accueil, qui contient le menu principal.
     */
    @GetMapping({"/", "/home"})
    public String homePage() {
        // Renvoie au template 'home.html'
        return "home";
    }

    /**
     * Affiche la page pour gérer les utilisateurs.
     * Cette page sera notre point d'entrée pour les fonctionnalités du microservice Utilisateur.
     */
    @GetMapping("/users/manage")
    public String manageUsersPage() {
        // Renvoie au template 'manage_users.html'
        return "manage_users"; 
    }
}