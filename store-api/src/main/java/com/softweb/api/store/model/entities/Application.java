package com.softweb.api.store.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

/**
 * The Application entity class contains application data.
 * <p>
 * The class contains an automatically generated identifier, the name of the application,
 * its description and title, the path and image of the application logo, license information, and application analytics data: the number of downloads and views.
 * <p>
 * The class also contains fields that associate the application with its user, image list, and application installers.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Entity
@Table(name = "application")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Application {

    /**
     * Automatically generated identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Name of application
     */
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    /**
     * Short description, that are used as label
     */
    @Column(name = "short_description", length = 50, nullable = false)
    private String shortDescription;

    /**
     * Long description, that contains details about application
     */
    @Column(name = "long_description")
    private String longDescription;

    /**
     * Path to logo image of application
     */
    @Column(name = "logo_path")
    private String logoPath;

    /**
     * License of application
     */
    @ManyToOne
    @JoinColumn(name = "license")
    private License license;

    /**
     * User of application
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    /**
     * The date the app was last updated
     */
    @Column(name = "last_update")
    private Date lastUpdate;

    /**
     * Number of app downloads
     */
    @Column(name = "downloads")
    private int downloads;

    /**
     * App Views
     */
    @Column(name = "views")
    private int views;

    /**
     * Application image list
     */
    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    private Set<Image> images;

    /**
     * List of app installers
     */
    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    private Set<Installer> installers;
}