package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class FederalStateTest {

    @Test
    public void createNewFederalStateNoArgsConstructor(){

        FederalState federalState = new FederalState();
        federalState.setName("Musterbundesland");
        federalState.setId(1L);

        Assert.assertEquals("Id must be 1L", 1L, federalState.getId());
        Assert.assertEquals("Name must be Musterbundesland", "Musterbundesland", federalState.getName());

    }

    @Test
    public void createNewFederalStateAllArgsConstructor(){

        FederalState federalState = new FederalState(1L, "Musterbundesland");

        Assert.assertEquals("Id must be 1L", 1L, federalState.getId());
        Assert.assertEquals("Name must be Musterbundesland", "Musterbundesland", federalState.getName());

    }

}
