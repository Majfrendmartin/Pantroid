package com.wildeastcoders.pantroid.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wildeastcoders.pantroid.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditItemActivityFragment extends Fragment {

    public EditItemActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_item, container, false);
    }
}
