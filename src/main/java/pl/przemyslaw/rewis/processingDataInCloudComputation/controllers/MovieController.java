package pl.przemyslaw.rewis.processingDataInCloudComputation.controllers;

import org.springframework.web.bind.annotation.RestController;
import pl.przemyslaw.rewis.processingDataInCloudComputation.models.Movie;
import pl.przemyslaw.rewis.processingDataInCloudComputation.repostories.MovieRepository;

import java.util.List;

@RestController
public class MovieController {
    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public List<Movie> prepareMoviesRecommendation(String actorName) {
        return movieRepository.findMoviesByActorName(actorName);
    }

}
