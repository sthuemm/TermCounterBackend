package de.htwg.smartcity.termcounterbackend.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private int postal;

    @OneToMany
    private List<School> schools;

    public City(){
        this.schools = new ArrayList<>();
    }

    public City(String name){
        this.schools = new ArrayList<>();
        this.name = name;
    }


}
