package utils;

import lombok.Data;

/**
 * Questa classe permette di gestire la paginazione
 */
@Data
public class Paginator {
    /**
     * Rappresenta il numero di items da mostrare
     */
    private final int limit;

    /**
     * Rappresenta l'offset da cui partire
     */
    private final int offset;

    /**
     * Costruttore dell'oggetto.
     * @param page rappresenta la pagina da cui parte la paginazione
     * @param itemsPerPage rappresenta il numero di items da mostrare nella pagina
     */
    public Paginator(int page, int itemsPerPage) {
        this.limit = itemsPerPage;
        if(page == 1)
            this.offset = 0;
        else
            this.offset = (page - 1) * itemsPerPage; //+1
    }

    /**
     * Implementa la funzionalità che permette di calcolare il numero di pagine totali da mostrare.
     * @param size rappresenta il numero d'items totali da paginare
     * @return un intero che corrisponde al numero di pagine totali necessarie
     */
    public int getPages(int size) {
        int additionalPage;
        if(size % limit == 0)
            additionalPage = 0;
        else
            additionalPage = 1;

        return (size / limit) + additionalPage;
    }
}
