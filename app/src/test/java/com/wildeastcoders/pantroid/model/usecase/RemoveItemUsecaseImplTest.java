package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.database.Repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class RemoveItemUsecaseImplTest {

    @Mock
    Repository repository;

    private RemoveItemUsecase removeItemUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        removeItemUsecase = new RemoveItemUsecaseImpl(repository);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void init() throws Exception {
        final PantryItem name = new PantryItem(null, "name", 1L, new Date(), new Date(), 1);
        removeItemUsecase.init(name);

        assertEquals(name, removeItemUsecase.getPantryItem());

        removeItemUsecase.init(null);

        assertEquals(null, removeItemUsecase.getPantryItem());
    }

    @Test
    public void execute() throws Exception {

    }
}