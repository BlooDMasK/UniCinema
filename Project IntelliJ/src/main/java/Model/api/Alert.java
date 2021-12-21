package Model.api;

import org.json.JSONObject;

import java.util.List;

public class Alert implements JsonSerializable {

    private final List<String> messages;
    private final String type;

    public Alert(List<String> messages, String type) {
        this.messages = messages;
        this.type = type;
    }

    public List<String> getMessages() { return messages; }

    public String getType() { return type; }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("messages", messages);
        object.put("type", type);
        return object;
    }
}
