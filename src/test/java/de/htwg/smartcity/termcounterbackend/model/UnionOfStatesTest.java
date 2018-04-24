package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UnionOfStatesTest {


    @Test
    public void createNewUnionOfStates(){
        Country country = new Country();
        country.setId(1L);
        country.setName("Germany");

        UnionOfStates unionOfStates = new UnionOfStates();
        unionOfStates.setId(1L);
        unionOfStates.setName("European Union");
        unionOfStates.getCountries().add(country);

        Assert.assertEquals("Id must be 1L", 1L, unionOfStates.getId());
        Assert.assertEquals("Name must be European Union", "European Union", unionOfStates.getName());
        Assert.assertEquals("Name of country must be Germany", "Germany", unionOfStates.getCountries().get(0).getName());
    }

}
