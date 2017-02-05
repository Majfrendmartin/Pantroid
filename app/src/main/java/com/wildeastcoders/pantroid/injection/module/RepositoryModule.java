package com.wildeastcoders.pantroid.injection.module;

import android.app.Application;

import com.wildeastcoders.pantroid.injection.scope.PerApplication;
import com.wildeastcoders.pantroid.model.DaoSession;
import com.wildeastcoders.pantroid.model.FieldsValidator;
import com.wildeastcoders.pantroid.model.FieldsValidatorImpl;
import com.wildeastcoders.pantroid.model.database.DbUtils;
import com.wildeastcoders.pantroid.model.database.Repository;
import com.wildeastcoders.pantroid.model.database.RepositoryImpl;

import dagger.Module;
import dagger.Provides;

import static com.wildeastcoders.pantroid.BuildConfig.DB_NAME;

/**
 * Created by Majfrendmartin on 2017-02-05.
 */

@Module
public class RepositoryModule {
    @Provides
    @PerApplication
    public Repository provideRepository(DaoSession session) {
        return new RepositoryImpl(session);
    }

    @Provides
    @PerApplication
    public DaoSession provideDaoSession(Application application) {
        return DbUtils.getDaoSession(application, DB_NAME);
    }

    @Provides
    @PerApplication
    public FieldsValidator provideFieldsValidator(){
        return new FieldsValidatorImpl();
    }
}
