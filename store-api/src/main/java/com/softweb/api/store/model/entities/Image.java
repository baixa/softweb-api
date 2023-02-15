package com.softweb.api.store.model.entities;

import jakarta.persistence.*;

/**
 * An entity class that contains information about the associated application's images.
 * <p>
 * The class contains an automatically generated identifier, an image, and a link to the associated application.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Entity
@Table(name = "image")
public class Image {
    /**
     * Automatically generated identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    /**
     * App Image URL
     */
    @Column(name = "path")
    private String path;

    /**
     * App Image
     */
    @Transient
    private Image image;

    /**
     * Related application
     */
    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;
}