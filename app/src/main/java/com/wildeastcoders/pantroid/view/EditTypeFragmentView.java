package com.wildeastcoders.pantroid.view;

import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.repository.ValidationResult;


/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface EditTypeFragmentView extends View {
    void displayValidationError(ValidationResult validationResult);
}
