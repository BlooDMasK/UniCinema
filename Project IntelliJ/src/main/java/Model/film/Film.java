package Model.film;

import Model.actors.Actor;
import Model.director.Director;
import Model.houseproduction.HouseProduction;
import Model.production.Production;
import Model.review.Review;
import Model.show.Show;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Film {
    private int id, length, genre;
    private String title, plot, cover;
    private LocalDate datePublishing;

    private List<Actor> actorList;
    private List<Director> directorList;
    private List<HouseProduction> houseProductionList;
    private List<Production> productionList;

    private List<Review> reviewList;
    private List<Show> showList;

    public Film() {
        actorList = new ArrayList<>();
        directorList = new ArrayList<>();
        houseProductionList = new ArrayList<>();
        productionList = new ArrayList<>();
    }

    public Film(int id, int length, int genre, String title, String plot, LocalDate datePublishing, List<Actor> actorList, List<Director> directorList, List<HouseProduction> houseProductionList, List<Production> productionList, List<Review> reviewList, List<Show> showList) {
        this.id = id;
        this.length = length;
        this.genre = genre;
        this.title = title;
        this.plot = plot;
        this.datePublishing = datePublishing;
        this.actorList = actorList;
        this.directorList = directorList;
        this.houseProductionList = houseProductionList;
        this.productionList = productionList;
        this.reviewList = reviewList;
        this.showList = showList;
    }

    public Film(int id, int length, int genre, String title, String plot, LocalDate datePublishing) {
        this.id = id;
        this.length = length;
        this.genre = genre;
        this.title = title;
        this.plot = plot;
        this.datePublishing = datePublishing;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public List<Show> getShowList() {
        return showList;
    }

    public void setShowList(List<Show> showList) {
        this.showList = showList;
    }

    public List<Review> getReviewList() { return reviewList; }

    public void setReviewList(List<Review> reviewList) { this.reviewList = reviewList; }

    public List<Director> getDirectorList() {
        return directorList;
    }

    public void setDirectorList(List<Director> directorList) {
        this.directorList = directorList;
    }

    public List<HouseProduction> getHouseProductionList() {
        return houseProductionList;
    }

    public void setHouseProductionList(List<HouseProduction> houseProductionList) {
        this.houseProductionList = houseProductionList;
    }

    public List<Production> getProductionList() {
        return productionList;
    }

    public void setProductionList(List<Production> productionList) {
        this.productionList = productionList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public LocalDate getDatePublishing() {
        return datePublishing;
    }

    public void setDatePublishing(LocalDate datePublishing) {
        this.datePublishing = datePublishing;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", length=" + length +
                ", genre=" + genre +
                ", title='" + title + '\'' +
                ", plot='" + plot + '\'' +
                ", cover='" + cover + '\'' +
                ", datePublishing=" + datePublishing +
                ", actorList=" + actorList +
                ", directorList=" + directorList +
                ", houseProductionList=" + houseProductionList +
                ", productionList=" + productionList +
                ", reviewList=" + reviewList +
                ", showList=" + showList +
                '}';
    }
}
