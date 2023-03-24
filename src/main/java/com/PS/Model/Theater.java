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


public class Theater {
    private @Id @GeneratedValue Long id;
    private String movieName, movieTime;
    private int ticketPrice;



    public Theater(String movieName, String movieTime, int ticketPrice) {
        this.movieName = movieName;
        this.movieTime = movieTime;
        this.ticketPrice = ticketPrice;
    }
}
