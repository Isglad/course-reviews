package com.teamtreehouse.courses.dao;

import com.teamtreehouse.courses.model.Course;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/*
This is unit testing
You can do Manual testing with POSTMAN
* Set method to POST + url
* Headers= key: Content-Type and value:application/json
* Body; select raw and in curly bracket, do this as an example: {"name": "Java Basics", "url": "http://teamtreehouse.com/library/java-basics"}
* Next, you can perform get for all courses or id
 */
public class Sql2oCourseDaoTest {

    private Sql2oCourseDao dao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        dao = new Sql2oCourseDao(sql2o);
        // Keep connection open through entire test so that it is not wiped out.
        conn = sql2o.open();
    }

    @After
   public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Course course = newTestCourse();
        int originalCourseId = course.getId();

        dao.add(course);

        assertNotEquals(originalCourseId, course.getId());
    }

    @Test
    public void addedCoursesAreReturnedFromFindAll() throws Exception {
        Course course = newTestCourse();

        dao.add(course);

        assertEquals(1, dao.findAll().size());
    }

    @Test
    public void noCoursesReturnsEmptyList() throws Exception {
        assertEquals(0, dao.findAll().size());
    }

    @Test
    public void existingCoursesCanBeFoundById() throws Exception {
        Course course = newTestCourse();
        dao.add(course);

        Course foundCourse = dao.findById(course.getId());

        assertEquals(course, foundCourse);
    }

    private static Course newTestCourse() {
        return new Course("Test", "http://test.com");
    }
}