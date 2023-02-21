package com.softweb.api.store.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authority")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Authority {
    @Id
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @Column(name = "authority")
    private String authority;

    @Override
    public String toString() {
        return "Authority{" +
                "authority='" + authority + '\'' +
                '}';
    }
}