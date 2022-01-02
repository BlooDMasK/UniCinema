package utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Questa classe contiene la lista di tutte le province.
 */
public class Province {

    /**
     * Rappresenta la lista delle province e dei paesi
     */
    private HashMap<String, ArrayList<String>> countries;

    /**
     * Costruttore vuoto
     */
    public Province() {
        countries = new HashMap<>();
        ArrayList<String> tmp = new ArrayList<>();

        tmp.add("Pratola Serra");
        tmp.add("Montemiletto");
        countries.put("Avellino", tmp);

        tmp.clear();

        tmp.add("Fisciano");
        countries.put("Salerno", tmp);
    }

    /**
     * Implementa la funzionalità che permette di restituire sotto forma di lista i paesi appartenenti ad una data provincia.
     * @param province rappresenta la provincia a cui appartengono i paesi della lista
     * @return lista contenente i paesi appartenenti alla provincia
     */
    public ArrayList<String> fetchCountriesFromProvince(String province) {
        ArrayList<String> list = new ArrayList<>();
        for(String country : countries.get(province)) {
            list.add(country);
        }
        return list;
    }
}
