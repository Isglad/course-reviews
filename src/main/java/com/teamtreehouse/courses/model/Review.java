package com.teamtreehouse.courses.model;

import java.util.Objects;

public class Review {
    private int id;
    private int courseId;
    private int rating;
    private String comment;

    // courseId is required upon comment creation!
    public Review(int courseId, int rating, String comment) {
        this.courseId = courseId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;
        return id == review.id && courseId == review.courseId && rating == review.rating && Objects.equals(comment, review.comment);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + courseId;
        result = 31 * result + rating;
        result = 31 * result + Objects.hashCode(comment);
        return result;
    }
}

/*
************ Setting up database ****************
SQL2o (a lightweight Java database framework) is not an ORM, it allows you to run SQL statements and have them populate your model objects.
In a standard Java DataBase Connectivity (JDBC) application, a developer writes an SQL statement and gets back a result set object which he then
uses to create new objects and populate them manually, which is time-consuming!
* The other end is using an ORM.
In this scenario, you decorate your model objects, and the ORM will generate the appropriate SQL statement
and then populate your model object behind the scenes.
* The downside of this is the amount of configuration is quite large and time-consuming, and often it has
a fairly steep learning curve to even realize what the ORM is capable of.
* SQL2o sits right there in the middle, it sits on top of JDBC, and you write SQL. But instead of having
to loop over the result set, you give it a class blueprint, and it creates filled-out objects based on the
query results. No configuration needed.
 */
