package com.hsalf.menuanim.dto;

import android.support.annotation.DrawableRes;

public class Card {

    public int image;
    public String content;
    private boolean dummy;

    private Card() {
        dummy = true;
    }

    public Card(@DrawableRes int res, String content) {
        this.image = res;
        this.content = content;
    }

    public boolean isDummy() {
        return dummy;
    }

    public static Card createDummy() {
        return new Card();
    }

}
