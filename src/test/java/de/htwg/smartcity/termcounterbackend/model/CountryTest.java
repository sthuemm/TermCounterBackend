package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CountryTest {

    @Test
    public void createNewCountry(){

        Country country = new Country();
        country.setId(1L);
        country.setName("Germany");

        Assert.assertEquals("Id must be 1L", 1L, country.getId());
        Assert.assertEquals("Name must be Germany", "Germany", country.getName());

    }
}
