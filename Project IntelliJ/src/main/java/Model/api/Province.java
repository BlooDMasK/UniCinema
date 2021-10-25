package Model.api;

import java.util.ArrayList;
import java.util.HashMap;

public class Province {

    private HashMap<String, ArrayList<String>> countries;

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

    public ArrayList<String> fetchCountriesFromProvince(String province) {
        ArrayList<String> list = new ArrayList<>();
        for(String country : countries.get(province)) {
            list.add(country);
        }
        return list;
    }
}
