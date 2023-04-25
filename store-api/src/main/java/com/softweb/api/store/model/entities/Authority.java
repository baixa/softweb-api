package com.softweb.api.store.model.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * The Authority entity class contains data about users' authorities.
 * <p>
 * The class contains an automatically generated identifier, the name of the authority and linked user object
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Entity
@Table(name = "authority")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Authority {
    /**
     * Generated ID value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_authority")
    @SequenceGenerator(name = "sq_authority", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    /**
     * Linked user
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    /**
     * Name of authority
     */
    @Column(name = "authority")
    private String authority;

    /**
     * Generates string view of object
     *
     * @return string view
     */
    @Override
    public String toString() {
        return "Authority{" +
                "authority='" + authority + '\'' +
                '}';
    }
}