package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CountryTest {

    @Test
    public void createNewCountryNoArgsConstructor(){

        Country country = new Country();
        country.setId(1L);
        country.setName("Germany");

        Assert.assertEquals("Id must be 1L", 1L, country.getId());
        Assert.assertEquals("Name must be Germany", "Germany", country.getName());

    }

    @Test
    public void createNewCountryAllArgsConstructor(){

        Country country = new Country(1L, "Germany");

        Assert.assertEquals("Id must be 1L", 1L, country.getId());
        Assert.assertEquals("Name must be Germany", "Germany", country.getName());

    }


}
