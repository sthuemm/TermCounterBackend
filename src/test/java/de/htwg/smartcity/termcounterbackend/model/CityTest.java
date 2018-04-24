package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CityTest {

    @Test
    public void createNewCity(){
        Graduation graduation = new Graduation();
        graduation.setId(1L);
        graduation.setName("Realschule");

        City city = new City();
        city.setId(1L);
        city.setName("Musterstadt");
        city.setPostal(12345);
        city.getGraduations().add(graduation);

        Assert.assertEquals("Id must be 1L", 1L, city.getId());
        Assert.assertEquals("Name must be Musterstadt", "Musterstadt", city.getName());
        Assert.assertEquals("Postal must be 12345", 12345, city.getPostal());
        Assert.assertEquals("Name of graduation must be Realschule", "Realschule", city.getGraduations().get(0).getName());

    }
}
