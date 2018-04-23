package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CityTest {

    @Test
    public void createNewCity(){
        City city = new City();
        city.setId(1L);
        city.setName("Musterstadt");
        city.setPostal(12345);

        Assert.assertEquals("Id must be 1L", 1L, city.getId());
        Assert.assertEquals("Name must be Musterstadt", "Musterstadt", city.getName());
        Assert.assertEquals("Postal must be 12345", 12345, city.getPostal());

    }
}
