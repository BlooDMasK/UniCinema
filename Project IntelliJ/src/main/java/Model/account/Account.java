package Model.account;

import Model.purchase.Purchase;
import Model.review.Review;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Account {
    private String email, firstname, lastname, psrwd;
    private int id;
    private boolean administrator;

    private List<Purchase> purchaseList;
    private List<Review> reviewList;

    public Account(String email, String firstname, String lastname, String psrwd, int id, boolean administrator, List<Purchase> purchaseList, List<Review> reviewList) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.psrwd = psrwd;
        this.id = id;
        this.administrator = administrator;
        this.purchaseList = purchaseList;
        this.reviewList = reviewList;
    }

    public Account(String email, String firstname, String lastname, String psrwd, int id, boolean administrator) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.psrwd = psrwd;
        this.id = id;
        this.administrator = administrator;
    }

    public Account() { }

    public String getPsrwd() {
        return psrwd;
    }

    public void setPsrwd(String psrwd) {
        this.psrwd = psrwd;
    }

    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPswrd() {
        return psrwd;
    }

    public void setPswd(String pswd) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashedPwd = digest.digest(pswd.getBytes(StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        for(byte bit : hashedPwd)
            builder.append(String.format("%02x", bit));
        this.psrwd = builder.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }
}
