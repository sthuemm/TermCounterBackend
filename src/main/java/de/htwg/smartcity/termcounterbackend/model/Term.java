package de.htwg.smartcity.termcounterbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Term {

    @Id
    private String word;

    @ManyToMany(mappedBy = "terms")
    @JsonIgnore
    private List<Person> persons;

    public Term(){}

    public Term(String word) {
        persons = new ArrayList<>();
        this.word = word;
    }
}
