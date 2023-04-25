package com.softweb.api.store.model.entities;

import com.softweb.api.store.model.dto.user.AbstractUserSaveDto;
import com.softweb.api.store.model.dto.user.UserPutDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * The User entity class contains data about the application users who host products in the system.
 * <p>
 * The class contains an identifier and data for user authorization in the system.
 * <p>
 * The class also contains a link to applications published in the system.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Entity
@Table(name = "users")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class User {

    /**
     * Automatically generated identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_user")
    @SequenceGenerator(name = "sq_user", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Username of user
     */
    @Column(name = "username", length = 30, nullable = false)
    private String username;

    /**
     * Full name of user or company
     */
    @Column(name = "full_name", length = 100)
    private String fullName;

    /**
     * Hashed password
     */
    @Column(name = "password", length = 64, nullable = false)
    private String password;

    /**
     * Is user enabled
     */
    @Column(name = "enabled", nullable = false)
    private boolean isEnabled;

    /**
     * Last login date.
     */
    @Column(name = "last_entered")
    private LocalDateTime lastEntered;

    /**
     * List of published applications.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Application> applications;

    /**
     * User authority
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Authority authority;

    /**
     * Constructor
     *
     * @param userDto Dto view of creatable User object
     */
    public User(AbstractUserSaveDto userDto) {
        if (userDto instanceof UserPutDto)
            this.id = ((UserPutDto) userDto).getId();

        this.username = userDto.getUsername();
        this.fullName = userDto.getFullName();
        this.password = userDto.getPassword();
        this.lastEntered = userDto.getLastEntered();
        this.isEnabled = true;
    }

    /**
     * Generates string view of object
     *
     * @return string view
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", lastEntered=" + lastEntered +
                ", authority=" + authority +
                '}';
    }
}