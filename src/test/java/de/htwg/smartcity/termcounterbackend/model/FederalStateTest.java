package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class FederalStateTest {

    @Test
    public void createNewFederalState(){
        District district = new District();
        district.setId(1L);
        district.setName("Musterlandkreis");

        FederalState federalState = new FederalState();
        federalState.setName("Musterbundesland");
        federalState.setId(1L);
        federalState.getDistricts().add(district);

        Assert.assertEquals("Id must be 1L", 1L, federalState.getId());
        Assert.assertEquals("Name must be Musterbundesland", "Musterbundesland", federalState.getName());
        Assert.assertEquals("Name of district must be Musterlandkreis", "Musterlandkreis", federalState.getDistricts().get(0).getName());

    }


}
