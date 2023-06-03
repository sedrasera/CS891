package edu.vandy.recommender.movies;

import edu.vandy.recommender.movies.model.Movie;
import edu.vandy.recommender.movies.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.yaml.snakeyaml.util.UriEncoder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static edu.vandy.recommender.movies.Constants.EndPoint.GET_ALL_MOVIES;
import static edu.vandy.recommender.movies.Constants.EndPoint.GET_SEARCHES;
import static edu.vandy.recommender.movies.Constants.EndPoint.GET_SEARCH;

/**
 * This class provides proxies to various endpoints in the 'movies'
 * microservice.
 */
@Component
public class MoviesSyncProxy {
    @Autowired
    private RestTemplate mMoviesRestTemplate;

    /**
     * @return A {@link List} of {@link Movie} objects
     */
    List<Movie> getMovies() {
        // Use the UriComponentsBuilder to create a URL to the
        // "all-movies" endpoint of the 'movies' microservice.

        // TODO -- you fill in here, replacing 'null' with the proper
        // code.
        String url = UriComponentsBuilder.fromPath(GET_ALL_MOVIES).build().toUriString();

        // Use WebUtils.makeGetRequestList() and mMoviesRestTemplate
        // to get a List of all movies from the 'movie' microservice.

        // TODO -- you fill in here, replacing 'null'
        // with the proper code.
        List<Movie> movies = WebUtils.makeGetRequestList(mMoviesRestTemplate, url,Movie[].class);

        if (movies == null)
            throw new IllegalStateException
                ("Unable to retrieve movies from 'movies' microservice.");

        return movies;
    }

    /**
     * Search for movie titles in the database containing the given
     * query {@link String}.
     *
     * @param query The {@link String} to search for
     * @return A {@link List} of {@link Movie} objects that match the
     *         query
     */
    List<Movie> searchMovies(String query) {
        // Use the UriComponentsBuilder to create a URL to the
        // "search" endpoint of the 'movies' microservice.
        // TODO -- you fill in here, replacing 'null'
        // with the proper code.
        String url = UriComponentsBuilder
                .fromPath(GET_SEARCH + "/" + URLEncoder.encode(query, StandardCharsets.UTF_8))
                .build()
                .toUriString();

        // Use WebUtils.makeGetRequestList() and mMoviesRestTemplate
        // to get a List of all matching movies from the 'movie'
        // microservice.
        // TODO -- you fill in here, replacing 'null'
        // with the proper code.
        List<Movie> matchingMovies = WebUtils.makeGetRequestList(mMoviesRestTemplate, url, Movie[].class);

        if (matchingMovies == null)
            throw new IllegalStateException
                    ("Unable to retrieve movies from 'movies' microservice.");

        return matchingMovies;
    }

    /**
     * Search for movie titles in the database containing the given
     * {@link List} of queries.
     *
     * @param queries The {@link List} queries to search for
     * @return A {@link List} of {@link Movie} objects that match the
     *         queries
     */
    List<Movie> searchMovies(List<String> queries) {
        // Use the UriComponentsBuilder to create a URL to the
        // "searches" endpoint of the 'movies' microservice.  You'll
        // need to convert the List of queries into a String.

        // TODO -- you fill in here, replacing 'null' with
        // the proper code.
        String url = UriComponentsBuilder
                .fromPath(GET_SEARCHES)
                .queryParam("queries", WebUtils.list2String(queries))
                .build()
                .toUriString();

        // Use WebUtils.makeGetRequestList() and mMoviesRestTemplate
        // to get a List of all matching movies from the 'movie'
        // microservice.
        // TODO -- you fill in here, replacing 'null'
        // with the proper code.
        List<Movie> matchingMovies = WebUtils.makeGetRequestList(mMoviesRestTemplate, url, Movie[].class);

        if (matchingMovies == null)
            throw new IllegalStateException
                    ("Unable to retrieve movies from 'movies' microservice.");

        return matchingMovies;
    }
}

