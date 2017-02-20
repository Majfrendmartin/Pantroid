package com.wildeastcoders.pantroid.model.event;


/**
 * Created by Majfrendmartin on 2017-02-15.
 */

public class DialogButtonClickedEvent {

    private final int dialogId;
    private final int buttonId;

    public DialogButtonClickedEvent(int dialogId, int buttonId) {
        this.dialogId = dialogId;
        this.buttonId = buttonId;
    }

    public int getDialogId() {
        return dialogId;
    }

    public int getButtonId() {
        return buttonId;
    }
}
