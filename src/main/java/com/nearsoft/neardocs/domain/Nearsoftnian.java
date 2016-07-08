package com.nearsoft.neardocs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Nearsoftnian.
 */
@Entity
@Table(name = "nearsoftnian")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Nearsoftnian implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_2")
    private String name2;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "last_name_2")
    private String lastName2;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Receipt> ownedReceipts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public Set<Receipt> getOwnedReceipts() {
        return ownedReceipts;
    }

    public void setOwnedReceipts(Set<Receipt> receipts) {
        this.ownedReceipts = receipts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Nearsoftnian nearsoftnian = (Nearsoftnian) o;
        if(nearsoftnian.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, nearsoftnian.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Nearsoftnian{" +
            "id=" + id +
            ", email='" + email + "'" +
            ", name='" + name + "'" +
            ", name2='" + name2 + "'" +
            ", lastName='" + lastName + "'" +
            ", lastName2='" + lastName2 + "'" +
            '}';
    }
}
