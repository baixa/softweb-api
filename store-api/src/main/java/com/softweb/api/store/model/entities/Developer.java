package com.softweb.api.store.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

/**
 * The Developer entity class contains data about the application developers who host products in the system.
 * <p>
 * The class contains an identifier and data for user authorization in the system.
 * <p>
 * The class also contains a link to applications published in the system.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Entity
@Table(name = "developer")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Developer {

    /**
     * Automatically generated identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Username of developer
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
     * Indicator of privileged user
     */
    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    /**
     * Last login date.
     */
    @Column(name = "last_entered")
    private Date lastEntered;

    /**
     * List of published applications.
     */
    @OneToMany(mappedBy = "developer", fetch = FetchType.EAGER)
    private Set<Application> applications;

}