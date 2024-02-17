package io.sharing.server.core.support.jpa;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
