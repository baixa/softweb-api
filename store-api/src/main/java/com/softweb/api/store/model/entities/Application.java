package com.softweb.api.store.model.entities;

import com.softweb.api.store.model.dto.application.ApplicationPostDto;
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
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Application {

    /**
     * Automatically generated identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_application")
    @SequenceGenerator(name = "sq_application", allocationSize = 1)
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Application(ApplicationPostDto applicationDto) {
        this.name = applicationDto.getName();
        this.shortDescription = applicationDto.getShortDescription();
        this.longDescription = applicationDto.getLongDescription();
        this.logoPath = applicationDto.getLogoBase64();
        this.lastUpdate = applicationDto.getLastUpdate();
        this.views = applicationDto.getViews();
        this.downloads = applicationDto.getDownloads();
        this.user = applicationDto.getUser();
        this.license = applicationDto.getLicense();
        this.category = applicationDto.getCategory();
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", logoPath='" + logoPath + '\'' +
                ", license=" + license +
                ", category=" + category +
                ", user=" + user +
                ", lastUpdate=" + lastUpdate +
                ", downloads=" + downloads +
                ", views=" + views +
                ", images=" + images +
                ", installers=" + installers +
                '}';
    }
}