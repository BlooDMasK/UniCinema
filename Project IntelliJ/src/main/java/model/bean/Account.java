package model.bean;

import lombok.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Questa classe rappresenta l'Utente Registrato.
 * L'Utente Registrato viene considerato amministratore se la variabile administrator è true,
 * mentre se è false, viene considerato Cliente.
 */
@Generated
@Data
@NoArgsConstructor
public class Account {

    /**
     * Rappresenta l'email dell'Utente Registrato.
     */
    private String email;

    /**
     * Rappresenta il nome dell'Utente Registrato.
     */
    private String firstname;

    /**
     * Rappresenta il cognome dell'Utente Registrato.
     */
    private String lastname;

    /**
     * Rappresenta la password dell'Utente Registrato.
     */
    @Getter
    private String pswrd;

    /**
     * Rappresenta l'identificativo numerico dell'Utente Registrato.
     */
    private int id;

    /**
     * Rappresenta il ruolo amministrativo che ricopre l'Utente Registrato.
     */
    private boolean administrator;

    /**
     * Rappresenta gli acquisti effettuati dall'Utente Registrato.
     */
    private List<Purchase> purchaseList;

    /**
     * Rappresenta le recensioni effettuate dall'Utente Registrato.
     */
    private List<Review> reviewList;

    /**
     *
     * @param email l'email dell'Utente Registrato
     * @param firstname il nome dell'Utente Registrato
     * @param lastname il cognome dell'Utente Registrato
     * @param psrwd la password dell'Utente Registrato
     * @param id l'identificativo numerico dell'Utente Registrato
     * @param administrator il ruolo amministrativo dell'Utente Registrato
     * @param purchaseList gli acquisti effettuati dall'Utente Registrato
     * @param reviewList le recensioni effettuate dall'Utente Registrato
     */
    public Account(String email, String firstname, String lastname, String psrwd, int id, boolean administrator, List<Purchase> purchaseList, List<Review> reviewList) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pswrd = psrwd;
        this.id = id;
        this.administrator = administrator;
        this.purchaseList = purchaseList;
        this.reviewList = reviewList;
    }

    /**
     *
     * @param email  l'email dell'Utente Registrato
     * @param firstname il nome dell'Utente Registrato
     * @param lastname il cognome dell'Utente Registrato
     * @param pswrd la password dell'Utente Registrato
     * @param id l'identificativo numerico dell'Utente Registrato
     * @param administrator il ruolo amministrativo dell'Utente Registrato
     * @throws NoSuchAlgorithmException
     */
    public Account(String email, String firstname, String lastname, String pswrd, int id, boolean administrator) throws NoSuchAlgorithmException {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        setPswrd(pswrd);
        this.id = id;
        this.administrator = administrator;
    }

    /**
     *
     * @param email l'email dell'Utente Registrato
     * @param pswrd la password dell'Utente Registrato
     * @param administrator Rappresenta il ruolo amministrativo che ricopre l'Utente Registrato.
     * @throws NoSuchAlgorithmException
     */
    public Account(String email, String pswrd,boolean administrator) throws NoSuchAlgorithmException {
        this.email = email;
        setPswrd(pswrd);
        this.administrator = administrator;
    }

    /**
     *
     * @param pswrd password non criptata
     * @throws NoSuchAlgorithmException
     */
    public void setPswrd(String pswrd) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashedPwd = digest.digest(pswrd.getBytes(StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        for(byte bit : hashedPwd)
            builder.append(String.format("%02x", bit));
        this.pswrd = builder.toString();
    }
}
