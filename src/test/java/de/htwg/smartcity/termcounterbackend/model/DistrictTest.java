package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DistrictTest {

    @Test
    public void createNewDistrict(){

        District district = new District();
        district.setId(1L);
        district.setName("Musterlandkreis");

        Assert.assertEquals("Id must be 1L", 1L, district.getId());
        Assert.assertEquals("Name must be Musterlandkreis", "Musterlandkreis", district.getName());

    }

}
