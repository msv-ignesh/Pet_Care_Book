package com.petcarebook.petcarebook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Owner name is required")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @Email(message = "Enter a valid email address")
    @Size(max = 150)
    @Column(length = 150)
    private String email;

    @Size(max = 20)
    @Column(length = 20)
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Pet> pets = new ArrayList<>();

    public Owner() {}

    public Owner(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public List<Pet> getPets() { return pets; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPets(List<Pet> pets) { this.pets = pets; }
}
