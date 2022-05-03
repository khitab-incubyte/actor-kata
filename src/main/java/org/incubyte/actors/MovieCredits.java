package org.incubyte.actors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieCredits {
    private String title;
    private String overview;
    private String character;

    @JsonProperty("poster_path")
    private String posterPath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public String getCharacter() {
        return character;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
