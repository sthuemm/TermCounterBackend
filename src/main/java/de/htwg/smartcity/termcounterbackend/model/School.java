package de.htwg.smartcity.termcounterbackend.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class School extends HasAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany
    private List<Graduation> graduations;



    public School(){
        this.graduations = new ArrayList<>();
    }

    public School(String name){
        this.graduations = new ArrayList<>();
        this.name = name;
    }

}
