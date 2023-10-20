package com.example.springboottls.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private Integer age;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
