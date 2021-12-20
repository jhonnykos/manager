package com.learnup.test.jbdc.entities;

public class Day {
    Integer day;
    Integer steps;

    public Day(Integer day, Integer steps) {
        this.day = day;
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Day{" +
                "day=" + day +
                ", steps=" + steps +
                '}';
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getSteps() {
        return steps;
    }
}
