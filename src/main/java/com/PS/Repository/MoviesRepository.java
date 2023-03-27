package com.PS.Repository;

import com.PS.Model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesRepository extends JpaRepository <Movies,Long> {
}
