package com.sampana.robotapocalypsesampana.model;

import com.sampana.robotapocalypsesampana.annotation.NotEnum;
import com.sampana.robotapocalypsesampana.model.enums.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "survivor")
public class Survivor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String uuid;
    @Column(unique = true)
    @NotBlank(message = "name is required")
    private String name;
    @Min(value = 1L, message = "Minimum age is 1")
    private int age;
    @Enumerated(EnumType.STRING)
    @NotEnum(regexp = "Male|Female|Other")
    private Gender gender;
    private boolean infected;
    @ElementCollection
    private List<String> informants;
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull(message = "location is required")
    private Location location;
    @OneToOne(cascade = CascadeType.ALL)
    private Resource resource;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Survivor survivor = (Survivor) o;
        return id != 0 && Objects.equals(id, survivor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
