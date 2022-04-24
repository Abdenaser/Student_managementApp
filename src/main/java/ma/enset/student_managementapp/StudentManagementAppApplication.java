package ma.enset.student_managementapp;

import ma.enset.student_managementapp.entities.Etudiant;
import ma.enset.student_managementapp.entities.Gender;
import ma.enset.student_managementapp.repositories.EtudiantRepository;
import ma.enset.student_managementapp.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class StudentManagementAppApplication {
    public static void main(String[] args) {        SpringApplication.run(StudentManagementAppApplication.class, args);
    }
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //@Bean
    CommandLineRunner commandLineRunner(EtudiantRepository etudiantRepository) {
        return args -> {
            etudiantRepository.save(new Etudiant(null, "Aroukhsiss", "Abdenasser", "admin@admin.ma", new Date(), Gender.M, true));
            etudiantRepository.save(new Etudiant(null, "Amine", "idkdidk", "Aminea@gmail.com", new Date(), Gender.M, true));
            etudiantRepository.save(new Etudiant(null, "Said", "said", "said@gmail.com", new Date(), Gender.M, true));
            etudiantRepository.save(new Etudiant(null, "med", "med", "medmed@gmail.com", new Date(), Gender.M, false));
            etudiantRepository.findAll().forEach(p -> {
                System.out.println(p.getNom());
            });

        };
    }
    //@Bean
    CommandLineRunner saveUsers(SecurityService securityService) {
        return args -> {
            securityService.saveNewUser("admin", "604258", "604258");
            securityService.saveNewUser("Aroukhsiss", "20002602", "20002602");
            securityService.saveNewRole("USER", "");
            securityService.saveNewRole("ADMIN", "");
            securityService.addRoleToUser("admin", "USER");
            securityService.addRoleToUser("admin", "ADMIN");
            securityService.addRoleToUser("Aroukhsiss", "USER");
        };
    }
}
