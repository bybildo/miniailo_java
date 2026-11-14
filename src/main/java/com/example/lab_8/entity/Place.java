package com.example.lab_8.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "places")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();
}