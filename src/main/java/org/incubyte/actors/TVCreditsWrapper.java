package org.incubyte.actors;

import java.util.List;

public class TVCreditsWrapper {
    public List<TVCredits> getTvCreditsList() {
        return tvCreditsList;
    }

    private List<TVCredits> tvCreditsList;

    public void setCast(List<TVCredits> tvCreditsList) {
        this.tvCreditsList = tvCreditsList;
    }
}
