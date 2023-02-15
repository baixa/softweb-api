package com.softweb.api.store.model.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * An entity class containing information about supported application licenses.
 * <p>
 * The class contains the license code (identifier) and the name of the license.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Table(name = "license")
@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class License {

    /**
     * Short code of license
     */
    @Id
    @Column(name = "code", nullable = false, length = 100)
    private String code;

    /**
     * Name of license
     */
    @Column(name = "name", nullable = false)
    private String name;

}
