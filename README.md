# Student management
JEE application based on spring to manage students.
# Enancé :
Create a web application based on Spring MVC, Spring Data JPA and Spring Security which allows to manage students.
Each student is defined by:
- His id
- his last name
- his first name
- his email
- his birthday
- Its gender: MASCULINE or FEMININE
- An attribute that indicates whether it is in good standing or not
   The application must offer the following functionalities:
- Search for students by name
- Do the pagination
- Delete students using method (DELETE instead of GET)
- Enter and Add students with form validation
- Edit and update students
- Create a template page
- Secure access to the application with an authentication system based on Spring security using the UseDetails Service policy
- Add other additional features
# Report :
> ## 1-Student Entity : 
*  `Etudiant` Entity.

<img width="441" alt="1" src="https://github.com/Abdenaser/Student_managementApp/blob/23051afd6d72fb17a4cdf5d96a47f5ad0c1053be/src/Screenshots/Entity%20Etudiant.png">

* The `Gender` entity of type enum.

<img width="350" alt="2" src="https://github.com/Abdenaser/Student_managementApp/blob/23051afd6d72fb17a4cdf5d96a47f5ad0c1053be/src/Screenshots/Entity%20Genre.png">


> ## 2- Create the EtudiantRepository repository: 
<img width="503" alt="3" src="https://github.com/Abdenaser/Student_managementApp/blob/23051afd6d72fb17a4cdf5d96a47f5ad0c1053be/src/Screenshots/Etudiant%20Repository.png">


> ## 3- Create the Student Controller :

* Home page:

<img width="377" alt="4" src="https://github.com/Abdenaser/Student_managementApp/blob/23051afd6d72fb17a4cdf5d96a47f5ad0c1053be/src/Screenshots/Home%20Page%20.png">

<img width="934" alt="17" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/HomePage.png">



* List of students + pagination + keyword search:

 <img width="671" alt="5" src="https://github.com/Abdenaser/Student_managementApp/blob/23051afd6d72fb17a4cdf5d96a47f5ad0c1053be/src/Screenshots/Student%20List%20+%20pagination%20.png">

 <img width="932" alt="19" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/Student_List.png">


* Delete student by id:

  <img width="421" alt="6" src="https://github.com/Abdenaser/Student_managementApp/blob/23051afd6d72fb17a4cdf5d96a47f5ad0c1053be/src/Screenshots/Delete.png">

  <img width="933" alt="21" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/Alert.png">


* Add new Student:

  <img width="414" alt="7" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/Add_new_student.png">

  
<img width="935" alt="20" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/Add%20new%20student.png">


* Edit a student:

  <img width="487" alt="8" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/Edit-Student.png">

  <img width="933" alt="21" src="https://github.com/Abdenaser/Student_managementApp/blob/ff3452096bebec9972d30d01f0b39833dd2808e9/src/Screenshots/editstudent.png">

> ## 4 - Authentication with the USER role

* This user has the right to consult the list of students but cannot perform operations such as deletion, modification or addition.

 <img width="933" alt="22" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/UserInterface.png">

  <img width="933" alt="23" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/StudentInfo.png">

 > ## 5- Error_Page 403
<img width="933" alt="24" src="https://github.com/Abdenaser/Student_managementApp/blob/20b86f465b18c2775350320d3db624c18917c58e/src/Screenshots/Eror403.png">

> ## 6- Spring Security
* Les entités AppUser & AppRole:

 <img width="388" alt="13" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/Entity%20AppUser.png">

 <img width="415" alt="12" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/Entity%20AppRole.png">

  
* AppUserRepository:

  <img width="505" alt="14" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/AppUserRepository.png">

  
* AppRoleRepository:

 <img width="486" alt="15" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/AppRoleRespository.png">

  
* SecurityConfig:
Access to the application is secured with an authentication system based on Spring security using the User Details Service policy:
### 7 - The implementation class of UserDetailsService 'UserDetailsServiceImpl'


```java=10
package ma.enset.student_managementapp.security.service;

import ma.enset.student_managementapp.security.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SecurityService securityService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = securityService.loadUserByUsername(username);

        Collection<GrantedAuthority> authorities1 = appUser
                .getAppRoles()
                .stream()
                .map(role-> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

        User user = new User(appUser.getUsername(),appUser.getPassword(),authorities1);
        return user;
    }
}

```

### 8 - The security configuration class
```java=10
package ma.enset.student_managementapp.security;

import lombok.AllArgsConstructor;
import ma.enset.student_managementapp.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity @AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;
    private UserDetailsServiceImpl userDetailsService;
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/images/**").permitAll();
        http.authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/user/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
    }
}
```

* Application.proprieties:

  <img width="605" alt="11" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/Application.proprieties.png">


> ## 9- The structure of the project
<img width="329" alt="25" src="https://github.com/Abdenaser/Student_managementApp/blob/883a3e5eeaf0b296e913c485dff883dcecbbeb51/src/Screenshots/structure%20du%20Projet.png">
