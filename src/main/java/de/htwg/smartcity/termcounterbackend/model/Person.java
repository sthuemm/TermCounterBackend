package de.htwg.smartcity.termcounterbackend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
public class Person extends HasAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstname;

    private String lastname;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dayOfBirth;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "person_term", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "term_id"))
    private List<Term> terms;

    private int numberTerms;

    public Person(){
        this.terms = new ArrayList<>();

    }

    public Person(String firstname, String lastname) {
        this.terms = new ArrayList<>();
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
