package model.bean;

import lombok.*;
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

@Generated
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Show implements Comparable<Show>, JsonSerializable {

    /**
     *
     * @param id Rappresenta il numero identificativo di uno spettacolo.
     * @param date Rappresenta la data in cui si svolge lo spettacolo.
     * @param time Rappresenta l'orario in cui si svolge lo spettacolo.
     * @param film Rappresenta il film trasmesso durante lo spettacolo.
     */
    public Show(int id, @NonNull LocalDate date, @NonNull LocalTime time, Film film) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.film = film;
    }

    /**
     *
     * @param id Rappresenta il numero identificativo di uno spettacolo.
     * @param date Rappresenta la data in cui si svolge lo spettacolo.
     * @param time Rappresenta l'orario in cui si svolge lo spettacolo.
     */
    public Show(int id, @NonNull LocalDate date, @NonNull LocalTime time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

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

    /**
     * rappresenta la sala in cui sarà trasmesso lo spettacolo
     */
    private Room room;

    /**
     *
     * @param showList Rappresenta la lista degli spettacoli
     * @return
     */
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


    /**
     * metodo che converte la classe in un oggetto JSON
     * @return root, oggetto JSON
     */
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
