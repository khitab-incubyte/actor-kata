package org.incubyte.actors;

import java.util.List;

public class MovieWrapper {
    private List<MovieCredits> cast;

    public void setCast(List<MovieCredits> cast) {
        this.cast = cast;
    }

    public List<MovieCredits> getCast() {
        return cast;
    }
}
