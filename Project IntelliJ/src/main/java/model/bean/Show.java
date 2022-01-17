package model.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import utils.JsonSerializable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Questa classe rappresenta uno spettacolo.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Show implements Comparable<Show>, JsonSerializable {
    /**
     * Rappresenta il numero identificativo di uno spettacolo.
     */
    private int id;

    /**
     * Rappresenta la data in cui si svolge lo spettacolo.
     */
    @NonNull
    private LocalDate date;

    /**
     * Rappresenta l'orario in cui si svolge lo spettacolo.
     */
    @NonNull
    private LocalTime time;

    /**
     * Rappresenta i biglietti acquistati per questo spettacolo.
     */
    private List<Ticket> ticketList;

    /**
     * Rappresenta il film trasmesso durante lo spettacolo.
     */
    private Film film;

    private Room room;

    public static Map<LocalDate, ArrayList<Show>> toHashMapDateTime(List<Show> showList) {
        Map<LocalDate, ArrayList<Show>> dateMap = new LinkedHashMap<>(); //Chiave: data, Valore: orari
        for(Show show : showList) {
            if(!dateMap.containsKey(show.getDate()))
                dateMap.put(show.getDate(), new ArrayList<>());

            dateMap.get(show.getDate()).add(show);
        }

        return dateMap;
    }

    /**
     * Implementa la funzionalità che permette di riordinare gli orari di ogni spettacolo in base al valore di ritorno.
     * @param show rappresenta lo spettacolo
     * @return un intero (-1, 0 o 1).
     */
    @Override
    public int compareTo(Show show) {
        return LocalDateTime.of(this.getDate(), this.getTime()).compareTo(LocalDateTime.of(show.getDate(), show.getTime()));
    }

    @Override
    public JSONObject toJson() {
        JSONObject root = new JSONObject();
        root.put("id", id);
        root.put("date", date.toString());
        root.put("time", time.getHour() + ":" + ((time.getMinute() < 10) ? (time.getMinute() + "0") : time.getMinute()));
        root.put("filmTitle", film.getTitle());
        return root;
    }
}
