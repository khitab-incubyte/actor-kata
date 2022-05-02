package org.incubyte.actors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchResult {
    private String name;
    @JsonProperty("profile_path")
    private String profilePath;
    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
