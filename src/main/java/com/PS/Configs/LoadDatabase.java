package com.PS.Configs;


import com.PS.Model.Movies;
import com.PS.Model.Status;
import com.PS.Model.Theater;
import com.PS.Repository.MoviesRepository;
import com.PS.Repository.TheaterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LoadDatabase {

    private final static Logger log= LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner intiDatabase(TheaterRepository theaterRepository, MoviesRepository moviesRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                log.info("Preloading "+ theaterRepository.save(new Theater("RRR","12.00 PM",250)));
                log.info("Preloading "+ theaterRepository.save(new Theater("AntMan & Wasp","3 PM",350)));

                theaterRepository.findAll().forEach(theater -> log.info("Preload "+theater));


                moviesRepository.save(new Movies("150 Minutes", Status.COMPLETE));
                moviesRepository.save(new Movies("120 Minutes",Status.IN_PROGRESS));

                moviesRepository.findAll().forEach(movies -> log.info("Preload "+movies));

            }
        };
    }

}
