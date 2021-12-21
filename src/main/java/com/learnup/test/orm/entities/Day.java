package com.learnup.test.orm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="manager")
public class Day {
    @Id
    Integer day;

    public Day() {
    }

    @Column(name = "steps")
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
