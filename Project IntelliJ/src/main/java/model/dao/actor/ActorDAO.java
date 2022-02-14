package model.dao.actor;

import model.bean.Account;
import model.bean.Actor;
import model.bean.Film;
import utils.Paginator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ActorDAO {

    List<Actor> fetchAll(Paginator paginator) throws SQLException;

    List<Actor> fetchAll() throws SQLException;

    List<Actor> fetchAll(Film film) throws SQLException;

    Actor fetch(int id) throws SQLException;

    boolean insert(Actor actor) throws SQLException;

    boolean insert(ArrayList<Actor> actorList) throws SQLException;

    boolean update(Actor actor) throws SQLException;

    boolean update(ArrayList<Actor> actorList, int filmId) throws SQLException;

    boolean delete(int id) throws SQLException;

    int countAll() throws SQLException;

}
