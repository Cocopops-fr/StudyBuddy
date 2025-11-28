package com.studybuddy.interactions.config;

import com.studybuddy.interactions.model.Profile;
import com.studybuddy.interactions.repo.ProfileRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner initProfiles(ProfileRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                Profile p1 = new Profile();
                p1.setStudentId("student-1");
                p1.setFullname("Alice Dupont");
                p1.setSkills("Java, Data, Spring");
                p1.setCampus("Nanterre");

                Profile p2 = new Profile();
                p2.setStudentId("student-2");
                p2.setFullname("Mehdi Karim");
                p2.setSkills("Python, IA");
                p2.setCampus("Cergy");

                Profile p3 = new Profile();
                p3.setStudentId("student-3");
                p3.setFullname("Lina Martin");
                p3.setSkills("Sécurité, Réseau");
                p3.setCampus("Reims");
                
                
                Profile p4 = new Profile();
                p4.setStudentId("student-4");
                p4.setFullname("Arnaud Perrin");
                p4.setSkills("humble, humilité, aller aux soiréées, philosophie politique");
                p4.setCampus("Reims");
                
                
                Profile p5 = new Profile();
                p5.setStudentId("student-5");
                p5.setFullname("Emilie Roger ");
                p5.setSkills("plus écouter Léo, se reconcilier avec victor , java :( ");
                p5.setCampus("Cy Tech Campus du Parc");
                
                Profile p6 = new Profile();
                p6.setStudentId("student-6");
                p6.setFullname("Lucie Whitelock ");
                p6.setSkills("arrêter de fumer, répondre au Y , java :( ");
                p6.setCampus("Cy Tech Campus du Parc");

                repo.save(p1);
                repo.save(p2);
                repo.save(p3);
                repo.save(p4);
                repo.save(p5);
                repo.save(p6);
            }
        };
    }
}
