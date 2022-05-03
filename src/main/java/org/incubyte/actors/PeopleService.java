package org.incubyte.actors;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Singleton
public class PeopleService {
    private TmbdClient tmbdClient;

    @Value("${tmdb.api-key}")
    private String apiKey;

    public PeopleService(TmbdClient tmbdClient) {
        this.tmbdClient = tmbdClient;
    }

    public Optional<List<SearchResult>> searchByName(String name) {
        Optional<Page> mayBeResultWrapper = tmbdClient.searchByName(name, apiKey);
        Page page = mayBeResultWrapper.get();
        List<SearchResult> results = page.getResults();
        if (results.size()==0) {
            return Optional.empty();
        }
        return Optional.of(results);
    }

    public Optional<Person> getById(int id) {
        Optional<Person> byId = this.tmbdClient.getById(id, apiKey);
        Person person = byId.get();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date birthDate = format.parse(person.getBirthday());
            int year = birthDate.getYear();
            long millis = Clock.systemDefaultZone().millis();
            int thisYear = new Date(millis).getYear();
            person.setAge(thisYear - year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return byId;
    }

    public Optional<List<MovieCredits>> getMovieCreditsById(int id) {
        Optional<MovieWrapper> maybeMovieWrapper = this.tmbdClient.getMovieCreditsById(id, apiKey);
        MovieWrapper movieWrapper = maybeMovieWrapper.get();
        List<MovieCredits> movieCredits = movieWrapper.getCast();
        if (movieCredits.size()== 0)
        {
            return Optional.empty();
        }
        return  Optional.of(movieCredits);
    }
}
