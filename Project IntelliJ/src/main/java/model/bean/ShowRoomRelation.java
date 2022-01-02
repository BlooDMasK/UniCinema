package model.bean;

import lombok.Data;

/**
 * Questa classe rappresenta la relazione tra la Sala e lo Spettacolo.
 */
@Data
public class ShowRoomRelation {
    /**
     * Rappresenta la sala in cui avviene lo spettacolo.
     */
    private Room room;

    /**
     * Rappresenta lo spettacolo.
     */
    private Show show;
}
