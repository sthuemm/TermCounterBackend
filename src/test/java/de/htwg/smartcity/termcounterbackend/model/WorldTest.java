package de.htwg.smartcity.termcounterbackend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class WorldTest {

    @Test
    public void createNewWorld(){
        UnionOfStates unionOfStates = new UnionOfStates();
        unionOfStates.setId(1L);
        unionOfStates.setName("European Union");

        World world = new World();
        world.setName("Erde");
        world.setId(1L);
        world.getUnionOfStates().add(unionOfStates);

        Assert.assertEquals("Name must be Erde", "Erde", world.getName());
        Assert.assertEquals("Id musst be 1L", 1L, world.getId());
        Assert.assertEquals("Union of states name must be European Union", "European Union", world.getUnionOfStates().get(0).getName());
    }

}
