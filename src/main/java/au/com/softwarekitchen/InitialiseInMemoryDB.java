package au.com.softwarekitchen;

import au.com.softwarekitchen.model.Actor;
import au.com.softwarekitchen.model.Movie;
import au.com.softwarekitchen.persistence.ActorRepository;
import au.com.softwarekitchen.persistence.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;

@Configuration
public class InitialiseInMemoryDB {

    private static final Logger log = LoggerFactory.getLogger(InitialiseInMemoryDB.class);

    @Bean
    public CommandLineRunner demo(ActorRepository actorRepository, MovieRepository movieRepository) {
        return (args) -> {

            log.info("");
            log.info("Movies being saved into the database.");
            log.info("----------------------------------------");
            log.info("");

            // save some more movies
            final Movie nottingHill = movieRepository.save(new Movie("Notting Hill", LocalDate.of(1999, Month.MAY, 21)));
            final Movie bridgetJonesDiary = movieRepository.save(new Movie("Bridget Jones's Diary", LocalDate.of(2001, Month.APRIL, 4)));
            final Movie aboutABoy = movieRepository.save(new Movie("About a Boy", LocalDate.of(2002, Month.APRIL, 26)));

            log.info("");
            log.info("Actors being saved into the database.");
            log.info("----------------------------------------");
            log.info("");

            // save some actors
            final Actor hughGrant = actorRepository.save(new Actor("Hugh", "Grant"));
            hughGrant.addMovie(nottingHill);
            hughGrant.addMovie(bridgetJonesDiary);
            hughGrant.addMovie(aboutABoy);
            actorRepository.save(hughGrant);


            // save some more actors
            actorRepository.save(new Actor("Jack", "Bauer"));
            actorRepository.save(new Actor("Chloe", "O'Brian"));
            actorRepository.save(new Actor("Kim", "Bauer"));
            actorRepository.save(new Actor("David", "Palmer"));
            actorRepository.save(new Actor("Michelle", "Dessler"));


            // fetch all customers
            log.info("Actors found with findAll():");
            log.info("-------------------------------");
            for (Actor actor : actorRepository.findAll()) {
                log.info(actor.toString());
            }
            log.info("");

            // fetch an individual actor by ID
            Actor actor = actorRepository.findOne(1L);
            log.info("Actor found with findOne(1L):");
            log.info("--------------------------------");
            log.info(actor.toString());
            log.info("");

            // fetch customers by last name
            log.info("Actor found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            for (Actor bauer : actorRepository.findByLastName("Bauer")) {
                log.info(bauer.toString());
            }
            log.info("");
        };
    }

}
