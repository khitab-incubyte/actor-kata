package org.incubyte.actors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeopleServiceShould {
    @Mock
    TmbdClient tmbdClient;
    Page page;
    Page page2;
    private final Clock clock = Clock.fixed(ZonedDateTime.parse("2021-10-25T00:00:00.000+09:00[Asia/Seoul]").toInstant(), ZoneId.of("Asia/Seoul"));
    Person person;
    MovieWrapper movieWrapper;
    TVCreditsWrapper tvWrapper;

    @BeforeEach
    public void init() {
        page = new Page();
        // create result object
        SearchResult searchResult1 = new SearchResult();
        searchResult1.setName("Tom Cruise");
        searchResult1.setProfilePath("/Abc");
        SearchResult searchResult2 = new SearchResult();
        searchResult2.setName("Tom Cruise123");
        searchResult2.setProfilePath("/xyz");
        //add result object to list
        List<SearchResult> allResults = new ArrayList<SearchResult>();
        allResults.add(searchResult1);
        allResults.add(searchResult2);
        page.setResults(allResults);

        page2 = new Page();
        page2.setResults(new ArrayList<>());

        person = new Person();
        person.setBirthday("1962-07-03");

        movieWrapper = new MovieWrapper();
        MovieCredits movieCredits = new MovieCredits();
        movieCredits.setTitle("War of the Worlds");
        List<MovieCredits> movieCreditsList = new ArrayList<>();
        movieCreditsList.add(movieCredits);
        movieWrapper.setCast(movieCreditsList);

        TVCredits tvCredits = new TVCredits();
        tvCredits.setName("aBC");
        tvCredits.setEpisodeCount(6);
        tvCredits.setOverview("shbfdhvfods");
        tvCredits.setPosterPath("sjfbidjbf");

        List<TVCredits> tvCreditsList = new ArrayList<>();
        tvCreditsList.add(tvCredits);
        tvWrapper = new TVCreditsWrapper();
        tvWrapper.setCast(tvCreditsList);

    }

    @Test
    void invoke_http_client() {
        when(tmbdClient.searchByName("tom cruise", null)).thenReturn(Optional.of(page));
        PeopleService peopleService = new PeopleService(tmbdClient);

        peopleService.searchByName("tom cruise");
        verify(tmbdClient).searchByName("tom cruise", null);
    }

    @Test
    public void return_empty_optional_when_no_results_found() {
        when(tmbdClient.searchByName("abc xyz", null)).thenReturn(Optional.of(page2));
        PeopleService peopleService = new PeopleService(tmbdClient);
        Optional<List<SearchResult>> results = peopleService.searchByName("abc xyz");
        assertThat(results.isPresent()).isFalse();
    }

    @Test
    public void invoke_http_client_to_retrieve_person() {
        when(tmbdClient.getById(500, null)).thenReturn(Optional.of(person));
        var mockClock = mockStatic(Clock.class);
        when(clock.systemDefaultZone()).thenReturn(clock);
        PeopleService peopleService = new PeopleService(tmbdClient);
        Optional<Person> person = peopleService.getById(500);
        assertThat(person.get().getAge()).isEqualTo(59);
    }

    @Test
    public void invoke_http_client_to_get_movie_credits_by_id()
    {
        when(tmbdClient.getMovieCreditsById(500, null)).thenReturn(Optional.of(movieWrapper));
        PeopleService peopleService = new PeopleService(tmbdClient);

        Optional<List<MovieCredits>> movieWrapperMaybe = peopleService.getMovieCreditsById(500);
        assertThat(movieWrapperMaybe.get().get(0).getTitle()).isEqualTo("War of the Worlds");
    }

    @Test
    public void invoke_http_client_to_get_tv_credits_by_id() {
        when(tmbdClient.getTVCreditsByID(123,null)).thenReturn(Optional.of(tvWrapper));
        PeopleService peopleService = new PeopleService(tmbdClient);

        Optional<List<TVCredits>> tvCreditsListMaybe = peopleService.getTVCreditsById(123);
        Assertions.assertThat(tvCreditsListMaybe.get().get(0)).isNotNull();
    }
}
