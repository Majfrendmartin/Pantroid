package com.wildeastcoders.pantroid.model.event;

import android.support.annotation.NonNull;

/**
 * Created by Majfrendmartin on 2017-02-12.
 */

public class NewItemAddedEvent {
    private final EventItemTypes type;

    public NewItemAddedEvent(@NonNull EventItemTypes type) {
        this.type = type;
    }

    public EventItemTypes getType() {
        return type;
    }

    public enum EventItemTypes {
        PANTRY_ITEM,
        ITEM_TYPE
    }
}
