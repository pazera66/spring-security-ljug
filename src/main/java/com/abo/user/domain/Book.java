package com.abo.user.domain;

import lombok.Builder;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by karol on 04.08.16.
 */
@Builder
@Entity(name = "_book")
public class Book implements Serializable{


    private @Id @NonNull int id;
    private @Column(nullable = false) String title;
    private @Column(nullable = false) String author;
    @ManyToOne
    private User owner;



}
