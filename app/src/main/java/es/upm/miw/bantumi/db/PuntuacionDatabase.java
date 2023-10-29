package es.upm.miw.bantumi.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Puntuacion.class}, version = 2, exportSchema = true)
public abstract class PuntuacionDatabase extends RoomDatabase {

    public abstract PuntuacionDao puntuacionDao();

    private static final String DB_NAME = "databases.db";
    private static volatile PuntuacionDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static PuntuacionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PuntuacionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PuntuacionDatabase.class, DB_NAME).addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static synchronized PuntuacionDatabase getInstance (Context context) {
        if (INSTANCE == null) {
            INSTANCE = create(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private static PuntuacionDatabase create(final Context context) {
        return Room.databaseBuilder(context, PuntuacionDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {

                PuntuacionDao dao = INSTANCE.puntuacionDao();
                dao.deleteAll();
            });
        }
    };
}







