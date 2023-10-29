package es.upm.miw.bantumi.db;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PuntuacionRepository {
    private PuntuacionDao mPuntuacionDao;
    private LiveData<List<Puntuacion>> mAllPuntuaciones;

    public PuntuacionRepository(Application application){
        PuntuacionDatabase db = PuntuacionDatabase.getDatabase(application);
        mPuntuacionDao = db.puntuacionDao();
        mAllPuntuaciones = mPuntuacionDao.getTop10Scores();
    }

    LiveData<List<Puntuacion>> getAllPuntuaciones(){ return mAllPuntuaciones;}

    public void insert(Puntuacion puntuacion) {
        PuntuacionDatabase.databaseWriteExecutor.execute(() -> {
            mPuntuacionDao.insert(puntuacion);
        });
    }

    public void delete(){
        PuntuacionDatabase.databaseWriteExecutor.execute(() -> {
            mPuntuacionDao.deleteAll();
        });
    }

}
