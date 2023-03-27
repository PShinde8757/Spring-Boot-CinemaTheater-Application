package com.PS.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Movies {

    private @Id @GeneratedValue Long id;
    private String movieDuration;
    private Status status;


    public Movies(String movieDuration, Status status) {
        this.movieDuration = movieDuration;
        this.status = status;
    }
}
