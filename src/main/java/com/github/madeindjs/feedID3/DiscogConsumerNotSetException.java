package com.github.madeindjs.feedID3;

public class DiscogConsumerNotSetException extends IllegalAccessException {

    public DiscogConsumerNotSetException() {
        super("You have to set `Discog.CONSUMER_KEY` & `Discog.CONSUMER_SECRET`");
    }

}
