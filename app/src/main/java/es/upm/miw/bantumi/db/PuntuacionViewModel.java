package es.upm.miw.bantumi.db;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PuntuacionViewModel extends AndroidViewModel {

    private PuntuacionRepository mRepository;
    private final LiveData<List<Puntuacion>> mAllPuntuaciones;

    public PuntuacionViewModel(Application application) {
        super(application);
        mRepository = new PuntuacionRepository(application);
        mAllPuntuaciones = mRepository.getAllPuntuaciones();
    }

    public LiveData<List<Puntuacion>> getAllPuntuaciones() {
        return mAllPuntuaciones;
    }

    public void insert(Puntuacion puntuacion){mRepository.insert(puntuacion);}

    public void delete(){
        mRepository.delete();
    }
}
