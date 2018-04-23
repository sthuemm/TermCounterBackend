package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UnionOfStatesTest {


    @Test
    public void createUnionOfStatesObjectNoArgsConstructor(){
        UnionOfStates unionOfStates = new UnionOfStates();
        unionOfStates.setId(1L);
        unionOfStates.setName("European Union");
        Assert.assertEquals("Id must be 1L", 1L, unionOfStates.getId());
        Assert.assertEquals("Name must be European Union", "European Union", unionOfStates.getName());
    }

    @Test
    public void createUnionOfStatesObjectAllArgsConstructor(){
        UnionOfStates unionOfStates = new UnionOfStates(1L, "European Union");
        Assert.assertEquals("Id must be 1L", 1L, unionOfStates.getId());
        Assert.assertEquals("Name must be European Union", "European Union", unionOfStates.getName());
    }

}
