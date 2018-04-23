package de.htwg.smartcity.termcounterbackend.model;



import lombok.Getter;
import lombok.Setter;


import javax.persistence.FetchType;

import javax.persistence.OneToOne;

@Getter
@Setter
public class HasAddress {

    @OneToOne(fetch = FetchType.EAGER)
    protected Address address;

}
