package ru.practicum.ewm.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString

@Entity
@Table(name = "categories")
public class Category {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
