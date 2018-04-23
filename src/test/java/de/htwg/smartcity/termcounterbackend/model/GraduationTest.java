package de.htwg.smartcity.termcounterbackend.model;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
public class GraduationTest {


    @Test
    public void createNewGraduationNoArgsConstructor(){
        Graduation graduation = new Graduation();
        graduation.setId(1L);
        graduation.setName("Realschule");

        Assert.assertEquals("Id must be 1L", 1L, graduation.getId());
        Assert.assertEquals("Name must be Realschule", "Realschule", graduation.getName());
    }

    @Test
    public void createNewGraduationAllArgsConstructor(){
        Graduation graduation = new Graduation(1L, "Realschule");

        Assert.assertEquals("Id must be 1L", 1L, graduation.getId());
        Assert.assertEquals("Name must be Realschule", "Realschule", graduation.getName());
    }



}