package com.sampana.robotapocalypsesampana.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

@Getter
@Setter
@ToString
@Entity(name = "resource")
public class Resource {
    private static final Random rand = new Random();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long water;
    private long food;
    private long medication;
    private long ammunition;

    public Resource() {
        this.water = rand.nextInt(101);
        this.food = rand.nextInt(101);
        this.medication = rand.nextInt(101);
        this.ammunition = rand.nextInt(101);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Resource resource = (Resource) o;
        return id != 0 && Objects.equals(id, resource.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
