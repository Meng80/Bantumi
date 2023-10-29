package es.upm.miw.bantumi.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Calendar;
import java.util.Date;

import es.upm.miw.bantumi.util.DateConverter;

@Entity(tableName = "puntuaciones")
public class Puntuacion {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "playerOneName")
    public String playerOneName;

    @ColumnInfo(name = "playerOneSeedStorage")
    public int playerOneSeedStorage;

    @ColumnInfo(name = "playerTwoName")
    public String playerTwoName;

    @ColumnInfo(name = "playerTwoSeedStorage")
    public int playerTwoSeedStorage;

    @ColumnInfo(name = "winnerName")
    public String winnerName;

    @ColumnInfo(name = "winnerSeedStorage")
    public int winnerSeedStorage;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "gameTime")
    public Date gameTime;


    public Puntuacion(String playerOneName, int playerOneSeedStorage, String playerTwoName, int playerTwoSeedStorage) {
        this.playerOneName = playerOneName;
        this.playerOneSeedStorage = playerOneSeedStorage;

        this.playerTwoName = playerTwoName;
        this.playerTwoSeedStorage = playerTwoSeedStorage;

        this.winnerName = playerOneSeedStorage > playerTwoSeedStorage ? playerOneName : playerTwoName;
        this.winnerSeedStorage = Math.max(playerOneSeedStorage, playerTwoSeedStorage);

        this.gameTime = Calendar.getInstance().getTime();
    }

    public String getPuntuacion() {
        return "Puntuacion{\n" +
                "playerOneName='" + playerOneName + "',\n" +
                "playerOneSeedStorage=" + playerOneSeedStorage + ",\n" +
                "playerTwoName='" + playerTwoName + "',\n" +
                "playerTwoSeedStorage=" + playerTwoSeedStorage + ",\n" +
                "winnerName='" + winnerName + "',\n" +
                "winnerSeedStorage=" + winnerSeedStorage + ",\n" +
                "gameTime=" + gameTime + "\n" +
                '}';
    }


}
