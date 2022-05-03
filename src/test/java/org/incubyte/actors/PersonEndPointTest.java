package org.incubyte.actors;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@MicronautTest
public class PersonEndPointTest {
    @Client("/")
    @Inject
    HttpClient httpClient;
    @Test
    public void should_search_for_people_based_on_query() {
        List<SearchResult> results = httpClient.toBlocking().retrieve(HttpRequest.GET("people?query=tom+cruise"), Argument.listOf(SearchResult.class));
        assertThat(results.size()).isGreaterThan(0);
        SearchResult result = results.get(0);
        assertThat(result.getName()).isNotEmpty();
        assertThat(result.getProfilePath()).isNotEmpty();
        assertThat(result.getId()).isEqualTo(500L);
    }

    @Test()
    public void should_return_404_if_person_not_found() {
        HttpClientResponseException httpClientResponseException = assertThrows(
                HttpClientResponseException.class, () -> {
                    List<SearchResult> results = httpClient.toBlocking().retrieve(HttpRequest.GET("people?query=ABC+XYZ"),
                            Argument.listOf(SearchResult.class));
                }
        );
        assertThat( httpClientResponseException.getMessage()).isEqualTo("Not Found");
        assertThat(httpClientResponseException.getResponse().code()).isEqualTo(HttpResponse.notFound().status().getCode());
    }

    @Test
    public void should_return_person_info(){

        Person person = httpClient.toBlocking().retrieve(HttpRequest.GET("people/500"), Argument.of(Person.class));
        assertThat(person.getName()).isEqualTo("Tom Cruise");
        assertThat(person.getImage()).isNotEmpty();
        assertThat(person.getGender()).isEqualTo(2);
        assertThat(person.getPlaceOfBirth()).isEqualTo("Syracuse, New York, USA");
    }

    @Test
    public void should_return_movie_credits()
    {
        List<MovieCredits> movieCredits = new ArrayList<MovieCredits>();
        movieCredits = httpClient.toBlocking().retrieve(HttpRequest.GET("people/500/movies"), Argument.listOf(MovieCredits.class));
        MovieCredits movie = movieCredits.get(0);
        assertThat(movie.getTitle()).isEqualTo("War of the Worlds");
    }

}
