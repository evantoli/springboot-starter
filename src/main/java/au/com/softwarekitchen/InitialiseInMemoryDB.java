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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
            nottingHill.setDescription("William Thacker (Hugh Grant) is a London bookstore owner whose humdrum existence is thrown into romantic turmoil when famous American actress Anna Scott (Julia Roberts) appears in his shop.");

            final Movie bridgetJonesDiary = movieRepository.save(new Movie("Bridget Jones's Diary", LocalDate.of(2001, Month.APRIL, 4)));
            bridgetJonesDiary.setDescription("Bridget Jones is an average woman struggling against her age, her weight, her job, her lack of a man, and her many imperfections. As a New Year's Resolution, Bridget decides to take control of her life, starting by keeping a diary.");

            final Movie aboutABoy = movieRepository.save(new Movie("About a Boy", LocalDate.of(2002, Month.APRIL, 26)));
            final Movie eatPrayLove = movieRepository.save(new Movie("Eat Pray Love", LocalDate.of(2010, Month.AUGUST, 13)));
            final Movie oceansEleven = movieRepository.save(new Movie("Ocean's Eleven", LocalDate.of(2001, Month.DECEMBER, 7)));
            final Movie oceansTwelve = movieRepository.save(new Movie("Ocean's Twelve", LocalDate.of(2004, Month.DECEMBER, 10)));

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

            final Actor juliaRoberts = actorRepository.save(new Actor("Julia", "Roberts"));
            juliaRoberts.addMovie(nottingHill);
            juliaRoberts.addMovie(eatPrayLove);
            juliaRoberts.addMovie(oceansEleven);
            juliaRoberts.addMovie(oceansTwelve);
            actorRepository.save(juliaRoberts);

            final Actor bradPitt = actorRepository.save(new Actor("Brad", "Pitt"));
            bradPitt.addMovie(oceansEleven);
            bradPitt.addMovie(oceansTwelve);
            actorRepository.save(bradPitt);

            // fetch all actors
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

            // fetch actors by last name
            log.info("Actor found with findByLastName('Grant'):");
            log.info("--------------------------------------------");
            final Pageable pageable = new PageRequest(0, 50);
            for (Actor a : actorRepository.findByLastName("Grant", pageable).getContent()) {
                log.info(a.toString());
            }
            log.info("");
        };
    }

}
