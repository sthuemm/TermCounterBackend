package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CountryTest {

    @Test
    public void createNewCountry(){
        FederalState federalState = new FederalState();
        federalState.setName("Musterbundesland");
        federalState.setId(1L);

        Country country = new Country();
        country.setId(1L);
        country.setName("Germany");
        country.getFederalStates().add(federalState);

        Assert.assertEquals("Id must be 1L", 1L, country.getId());
        Assert.assertEquals("Name must be Germany", "Germany", country.getName());
        Assert.assertEquals("Name of Federal state must be Musterbundesland", "Musterbundesland", country.getFederalStates().get(0).getName());

    }
}
