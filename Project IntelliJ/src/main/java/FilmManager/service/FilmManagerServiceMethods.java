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

public class FilmManagerServiceMethods implements FilmManagerService{

    ActorDAO actorDAO;
    DirectorDAO directorDAO;
    HouseProductionDAO houseProductionDAO;
    ProductionDAO productionDAO;
    FilmDAO filmDAO;

    public FilmManagerServiceMethods() throws SQLException {
        actorDAO = new ActorDAOMethods();
        directorDAO = new DirectorDAOMethods();
        houseProductionDAO = new HouseProductionDAOMethods();
        productionDAO = new ProductionDAOMethods();
        filmDAO = new FilmDAOMethods();
    }

    public void setActorDAO(ActorDAOMethods ActorDAO) {
        this.actorDAO = ActorDAO;
    }

    public void setDirectorDAO(DirectorDAOMethods DirectorDAO) {
        this.directorDAO = DirectorDAO;
    }

    public void setHouseProductionDAO(HouseProductionDAOMethods houseProductionDAOMethods) {
        this.houseProductionDAO = houseProductionDAOMethods;
    }

    public void setProductionDAO(ProductionDAOMethods productionDAOMethods) {
        this.productionDAO = productionDAOMethods;
    }

    public void setFilmDAO(FilmDAOMethods filmDAOMethods) {
        this.filmDAO = filmDAOMethods;
    }

    @Override
    public boolean removeFilm(int filmId) throws SQLException {
        return filmDAO.delete(filmId);
    }

    @Override
    public boolean insert(Film film) throws SQLException {
        return
                filmDAO.insertAndReturnID(film) != 0 &&
                actorDAO.insert(film.getActorList()) &&
                directorDAO.insert(film.getDirectorList()) &&
                houseProductionDAO.insert(film.getHouseProductionList()) &&
                productionDAO.insert(film.getProductionList());
    }

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
