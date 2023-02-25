package com.softweb.api.store.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * An entity class containing information about the operating systems supported by the application.
 * <p>
 * The class contains the system identifier and its fully qualified name.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Entity
@Table(name = "operating_system")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class OperatingSystem {

    /**
     * Automatically generated identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_operating_system")
    @SequenceGenerator(name = "sq_operating_system", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Name of OS
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * List of installers intended for this OS
     */
    @OneToMany(mappedBy = "system", fetch = FetchType.EAGER)
    private Set<Installer> installers;

}