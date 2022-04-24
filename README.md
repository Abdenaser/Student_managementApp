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
<img width="503" alt="3" src="https://ibb.co/89hbSpz">


> ## 3- Create the Student Controller :

* Home page:

<img width="377" alt="4" src="https://ibb.co/fNSjxNc">

<img width="934" alt="17" src="https://ibb.co/kHyYhrB">



* List of students + pagination + keyword search:

 <img width="671" alt="5" src="https://ibb.co/r21x8z5">

 <img width="932" alt="19" src="https://ibb.co/n67rwVk">


* Delete student by id:

  <img width="421" alt="6" src="https://ibb.co/syhczB9">

  <img width="933" alt="21" src="https://ibb.co/Cb89QGk">


* Add new Student:

  <img width="414" alt="7" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/Add_new_student.png">

  
<img width="935" alt="20" src="https://ibb.co/kcQ25tH">


* Edit a student:

  <img width="487" alt="8" src="https://ibb.co/fMJvxNm">

  <img width="933" alt="21" src="https://ibb.co/ZV2RmKx">

> ## 4 - Authentication with the USER role

* This user has the right to consult the list of students but cannot perform operations such as deletion, modification or addition.

 <img width="933" alt="22" src="https://ibb.co/FHRsndb">

  <img width="933" alt="23" src="https://ibb.co/56vhBfK">

 > ## 5- Error_Page 403
<img width="933" alt="24" src="https://ibb.co/S56njJT">

> ## 6- Spring Security
* Les entités AppUser & AppRole:

 <img width="388" alt="13" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/Entity%20AppUser.png">

 <img width="415" alt="12" src="https://github.com/Abdenaser/Student_managementApp/blob/402447e49b617c74e68e4df4b58ef1b4b4ce6977/src/Screenshots/Entity%20AppRole.png">

  
* AppUserRepository:

  <img width="505" alt="14" src="https://ibb.co/YBNNPxL">

  
* AppRoleRepository:

 <img width="486" alt="15" src="https://ibb.co/TBbQ6hT">

  
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

  <img width="605" alt="11" src="https://ibb.co/rb5rHXy">


> ## 9- The structure of the project
<img width="329" alt="25" src="https://ibb.co/7v1DsCp">
