package com.wildeastcoders.pantroid.model.database;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.wildeastcoders.pantroid.model.DaoMaster;
import com.wildeastcoders.pantroid.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Majfrendmartin on 2016-12-17.
 */

public abstract class DbUtils {
    private DbUtils() {
        //no-op
    }

    public static final DaoSession getDaoSession(@NonNull Application application, @NonNull String dbName) {
        final DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(application, dbName);
        final Database database = helper.getWritableDb();
        return new DaoMaster(database).newSession();
    }
}
