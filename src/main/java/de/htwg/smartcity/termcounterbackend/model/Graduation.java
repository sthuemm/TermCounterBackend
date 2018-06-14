package de.htwg.smartcity.termcounterbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Graduation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Person> persons;

    public double getAverageTermCount(){
        double terms = persons.stream().mapToDouble(Person::getNumberTerms).sum();
        int numberPersons = persons.size();
        return terms / numberPersons;
    }



    public Graduation(){
        this.persons = new ArrayList<>();
    }

    public Graduation(String name){
        this.persons = new ArrayList<>();
        this.name = name;
    }
}
