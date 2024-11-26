package com.hexagonal.user_auto.adapters.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_users")
@Data
@ToString(exclude = {"cars", "password"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "cars")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @NotBlank(message = "Missing firstName")
    private String firstName;
    @NotBlank(message = "Missing lastName")
    private String lastName;
    @Email(message = "Invalid E-mail format")
    private String email;
    private String birthday;
    @NotBlank(message = "Missing login")
    @Size(min = 5, max = 30, message = "Login must be between 5 and 30 characters")
    private String login;
    @NotBlank(message = "Missing password")
    private String password;
    private String phone;
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Car> cars = new ArrayList<>();
    private LocalDate createdAt;
    private LocalDateTime lastLogin;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return login;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
