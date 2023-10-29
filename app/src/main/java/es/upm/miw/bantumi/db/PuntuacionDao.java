package es.upm.miw.bantumi.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface PuntuacionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Puntuacion puntuacion);

    @Query("DELETE FROM puntuaciones")
    void deleteAll();

    @Query("SELECT * FROM puntuaciones ORDER BY winnerSeedStorage DESC LIMIT 0, 10")
    LiveData<List<Puntuacion>> getTop10Scores();

}
