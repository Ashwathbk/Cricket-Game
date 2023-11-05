package com.tekion.cricketgame.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    // @OneToMany(mappedBy = "team")
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,
            mappedBy="team")
    private List<Players> players;

//    @JsonIgnore
//    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,
//            mappedBy="team1")
//    private List<Players> players;

    public String toString() {
        return "{ id : " + id +
                ", name :" + name +
                ", country : " + country +
               // ", players : " + players +
                "}";
    }
}