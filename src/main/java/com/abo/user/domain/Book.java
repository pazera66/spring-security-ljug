package com.abo.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;

/**
 * Created by karol on 04.08.16.
 */
@Builder
@Entity(name = "_book")
@Getter
@Setter
@AllArgsConstructor
public class Book implements Serializable {

    public Book(){}

    private @Id @GeneratedValue @Max(1000) int id;
    private @Column(nullable = false) String title;
    private @Column(nullable = false) String author;
    @ManyToOne
    private User owner;

}
