package FilmManager.service;

import model.bean.*;
import model.dao.*;

import java.sql.SQLException;

public class FilmManagerServiceMethods implements FilmManagerService{

    ActorDAO actorDAO;
    DirectorDAO directorDAO;
    HouseProductionDAO houseProductionDAO;
    ProductionDAO productionDAO;
    FilmDAO filmDAO;


    public FilmManagerServiceMethods() {
        actorDAO = new ActorDAO();
        directorDAO = new DirectorDAO();
        houseProductionDAO = new HouseProductionDAO();
        productionDAO = new ProductionDAO();
        filmDAO = new FilmDAO();
    }

    public void setActorDAO(ActorDAO actorDAO) {
        this.actorDAO = actorDAO;
    }

    public void setDirectorDAO(DirectorDAO directorDAO) {
        this.directorDAO = directorDAO;
    }

    public void setHouseProductionDAO(HouseProductionDAO houseProductionDAO) {
        this.houseProductionDAO = houseProductionDAO;
    }

    public void setProductionDAO(ProductionDAO productionDAO) {
        this.productionDAO = productionDAO;
    }

    public void setFilmDAO(FilmDAO filmDAO) {
        this.filmDAO = filmDAO;
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
