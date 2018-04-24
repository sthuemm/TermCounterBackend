package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DistrictTest {

    @Test
    public void createNewDistrict(){
        City city = new City();
        city.setId(1L);
        city.setName("Musterstadt");
        city.setPostal(12345);

        District district = new District();
        district.setId(1L);
        district.setName("Musterlandkreis");
        district.getCities().add(city);

        Assert.assertEquals("Id must be 1L", 1L, district.getId());
        Assert.assertEquals("Name must be Musterlandkreis", "Musterlandkreis", district.getName());
        Assert.assertEquals("Name of city must be Musterstadt", "Musterstadt", district.getCities().get(0).getName());

    }

}
