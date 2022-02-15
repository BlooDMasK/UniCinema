package FilmManager.service;

import model.bean.*;
import model.dao.actor.ActorDAO;
import model.dao.actor.ActorDAOMethods;
import model.dao.director.DirectorDAO;
import model.dao.director.DirectorDAOMethods;
import model.dao.film.FilmDAO;
import model.dao.film.FilmDAOMethods;
import model.dao.houseproduction.HouseProductionDAO;
import model.dao.houseproduction.HouseProductionDAOMethods;
import model.dao.production.ProductionDAO;
import model.dao.production.ProductionDAOMethods;

import java.sql.SQLException;

/**
 *Classe che implementa i metodi definiti nell'interfaccia service del sottosistema Film (FilmManager)
 */
public class FilmManagerServiceMethods implements FilmManagerService{

    /**
     * Si occupa delle operazioni CRUD di un attore.
     */
    ActorDAO actorDAO;

    /**
     * Si occupa delle operazioni CRUD di un regista.
     */
    DirectorDAO directorDAO;

    /**
     * Si occupa delle operazioni CRUD di una casa di produzione.
     */
    HouseProductionDAO houseProductionDAO;

    /**
     * Si occupa delle operazioni CRUD di un produttore.
     */
    ProductionDAO productionDAO;

    /**
     * Si occupa delle operazioni CRUD di un film.
     */
    FilmDAO filmDAO;

    public FilmManagerServiceMethods() throws SQLException {
        actorDAO = new ActorDAOMethods();
        directorDAO = new DirectorDAOMethods();
        houseProductionDAO = new HouseProductionDAOMethods();
        productionDAO = new ProductionDAOMethods();
        filmDAO = new FilmDAOMethods();
    }

    /**
     * Metodo che permette di settare l'actorDAO con la propria implementazione.
     * @param ActorDAO
     */
    public void setActorDAO(ActorDAOMethods ActorDAO) {
        this.actorDAO = ActorDAO;
    }

    /**
     * Metodo che permette di settare l'directorDAO con la propria implementazione.
     * @param DirectorDAO
     */
    public void setDirectorDAO(DirectorDAOMethods DirectorDAO) {
        this.directorDAO = DirectorDAO;
    }

    /**
     * Metodo che permette di settare l'houseProductionDAO con la propria implementazione.
     * @param houseProductionDAOMethods
     */
    public void setHouseProductionDAO(HouseProductionDAOMethods houseProductionDAOMethods) {
        this.houseProductionDAO = houseProductionDAOMethods;
    }

    /**
     * Metodo che permette di settare l'productionDAO con la propria implementazione.
     * @param productionDAOMethods
     */
    public void setProductionDAO(ProductionDAOMethods productionDAOMethods) {
        this.productionDAO = productionDAOMethods;
    }

    /**
     * Metodo che permette di settare l'filmDAO con la propria implementazione.
     * @param filmDAOMethods
     */
    public void setFilmDAO(FilmDAOMethods filmDAOMethods) {
        this.filmDAO = filmDAOMethods;
    }

    /**
     * Implementa la funzionalità che permette di rimuovere un film
     * @param filmId identificativo numerico del film.
     * @return true se la rimozione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean removeFilm(int filmId) throws SQLException {
        return filmDAO.delete(filmId);
    }

    /**
     * Implementa la funzionalità che permette di registrare un film nel database
     * @param film da registrare
     * @return true se la registrazione va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean insert(Film film) throws SQLException {
        return
                filmDAO.insertAndReturnID(film) != 0 &&
                actorDAO.insert(film.getActorList()) &&
                directorDAO.insert(film.getDirectorList()) &&
                houseProductionDAO.insert(film.getHouseProductionList()) &&
                productionDAO.insert(film.getProductionList());
    }

    /**
     * Implementa la funzionalità che permette di aggiornare un film
     * @param film da aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean update(Film film) throws SQLException {
            return
                    filmDAO.update(film) &&
                    actorDAO.update(film.getActorList(), film.getId()) &&
                    directorDAO.update(film.getDirectorList(), film.getId()) &&
                    houseProductionDAO.update(film.getHouseProductionList(), film.getId()) &&
                    productionDAO.update(film.getProductionList(), film.getId());
    }
}
