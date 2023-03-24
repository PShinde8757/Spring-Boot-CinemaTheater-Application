package com.PS.Configs;


import com.PS.Model.Theater;
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
    CommandLineRunner intiDatabase(TheaterRepository theaterRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                log.info("Preloading "+ theaterRepository.save(new Theater("RRR","12.00 PM",250)));
                log.info("Preloading "+ theaterRepository.save(new Theater("AntMan & Wasp","3 PM",350)));
            }
        };
    }

}
