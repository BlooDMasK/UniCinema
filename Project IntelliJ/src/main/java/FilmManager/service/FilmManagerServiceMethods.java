package FilmManager.service;

import model.bean.*;
import model.dao.*;

import java.sql.SQLException;

public class FilmManagerServiceMethods implements FilmManagerService{

    ActorDAO actorDAO = new ActorDAO();
    DirectorDAO directorDAO = new DirectorDAO();
    HouseProductionDAO houseProductionDAO = new HouseProductionDAO();
    ProductionDAO productionDAO = new ProductionDAO();
    FilmDAO filmDAO = new FilmDAO();

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
