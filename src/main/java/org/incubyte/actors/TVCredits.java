package org.incubyte.actors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TVCredits {
    private String name;
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("episode_count")
    private int episodeCount;

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public void setName(String name) {
        this.name = name;
    }
}
