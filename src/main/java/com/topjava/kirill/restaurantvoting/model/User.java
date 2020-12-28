package com.topjava.kirill.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.topjava.kirill.restaurantvoting.controller.json.View;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = User.CONSTRAINT_INDEX)})
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractNamedEntity {

    public final static String CONSTRAINT_INDEX =  "USER_UNIQUE_EMAIL_INDEX";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "registered", nullable = false)
    @JsonView(View.JsonUserProfile.class)
    private LocalDate registered = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JsonView(View.JsonUserProfile.class)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("date DESC")
    @JsonIgnoreProperties("user")
    @JsonView(View.JsonUserWithVotes.class)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Vote> votes;

    public User(Integer id, String name, String email, String password, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.roles = roles;
    }
}
