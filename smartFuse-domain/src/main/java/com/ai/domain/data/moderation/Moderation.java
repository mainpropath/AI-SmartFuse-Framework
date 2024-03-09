package com.ai.domain.data.moderation;

import lombok.Data;

@Data
public class Moderation {

    private boolean flagged;
    private String flaggedText;
    private String type;

    public Moderation() {
        this.flagged = false;
        this.flaggedText = null;
        this.type = null;
    }

    public Moderation(String flaggedText, String type) {
        this.flagged = true;
        this.flaggedText = flaggedText;
        this.type = type;
    }

    public static Moderation flagged(String flaggedText, String type) {
        return new Moderation(flaggedText, type);
    }

    public static Moderation notFlagged() {
        return new Moderation();
    }
}

