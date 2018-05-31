package de.htwg.smartcity.termcounterbackend.model;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
public class GraduationTest {


    @Test
    public void createNewGraduation(){
        School school = new School();
        school.setId(1L);
        school.setName("Max Mustermann Schule");

        Graduation graduation = new Graduation();
        graduation.setId(1L);
        graduation.setName("Realschule");
//        graduation.getSchools().add(school);

        Assert.assertEquals("Id must be 1L", 1L, graduation.getId());
        Assert.assertEquals("Name must be Realschule", "Realschule", graduation.getName());
//        Assert.assertEquals("Name of school must be Max Mustermann Schule", "Max Mustermann Schule", graduation.getSchools().get(0).getName());
    }
}