package com.softweb.api.store.model.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * An entity class containing information about application installers for various operating systems.
 * <p>
 * The class contains an identifier, an installer, additional information about the installer (its size and version)
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Entity
@Table(name = "installer")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Installer {
    /**
     * Automatically generated identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    /**
     * Related application
     */
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    /**
     * Supported operating system
     */
    @ManyToOne
    @JoinColumn(name = "system_id")
    private OperatingSystem system;

    /**
     * Installer URL
     */
    @Column(name = "path")
    private String installerPath;

    /**
     * Installer Version
     */
    @Column(name = "version")
    private String version;

    /**
     * Installer size (in bytes)
     */
    @Column(name = "size")
    private int size;

}