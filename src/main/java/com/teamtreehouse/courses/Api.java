package com.teamtreehouse.courses;

import com.google.gson.Gson;
import com.teamtreehouse.courses.dao.CourseDao;
import com.teamtreehouse.courses.dao.Sql2oCourseDao;
import com.teamtreehouse.courses.model.Course;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class Api {
    public static void main(String[] args) {
        String datasource = "jdbc:h2:~/reviews.db";
        if (args.length > 0) {
            if (args.length != 2) {
                System.out.println("java Api <port> <datasource>");
                System.exit(0);
            }
            port(Integer.parseInt(args[0]));
            datasource = args[1];
        }
        Sql2o sql2o = new Sql2o(
                String.format("%s;INIT=RUNSCRIPT from 'classpath:db/init.sql'", datasource)
                , "", "");
        CourseDao courseDao = new Sql2oCourseDao(sql2o);
        Gson gson = new Gson();

        // Allow users of our API to create a new course
        post("/courses", "application/json", (req,res) -> {
            Course course = gson.fromJson(req.body(), Course.class); // this is going to return JSON that's been sent across
            courseDao.add(course);
            res.status(201);
            res.type("application/json");
            return course;
        }, gson::toJson); // This means: on this gson object, run toJson and pass it course. It is called method reference

        // Route to find all courses
        get("/courses", "application/json",
                (req, res) -> courseDao.findAll(), gson::toJson);

        // Route to find course by ID
        get("/courses/:id", "application/json", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Course course = courseDao.findById(id);
            return course;
        }, gson::toJson);

        after((req, res) -> {
            res.type("application/json");
        });
    }
}
