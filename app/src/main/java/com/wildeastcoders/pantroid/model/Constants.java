package com.wildeastcoders.pantroid.model;

/**
 * Created by Majfrendmartin on 2017-01-04.
 */

public final class Constants {

    public static final String MISSING_PANTRY_ITEM_OBJECT_ERROR_TEXT = "Missing pantry item object";
    public static final String MISSING_PANTRY_ITEM_TYPE_OBJECT_ERROR_TEXT = "Missing pantry item type object";
    public static final int MAX_NAME_LENGTH = 40;

    private Constants() {
        //no-op
    }

    public static class DialogIdentifiers {
        public static final int ABANDON_CHANGES_BACK_DIALOG_ID = 1;
        public static final int ABANDON_CHANGES_HOME_DIALOG_ID = 2;

        private DialogIdentifiers() {
            //no-op
        }
    }
}
