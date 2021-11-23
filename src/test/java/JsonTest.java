import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.stonlexx.json.JsonMap;
import org.stonlexx.json.JsonSerializable;
import org.stonlexx.json.parse.JsonParser;

public class JsonTest {

    public static final JsonParser JSON_PARSER
            = JsonParser.newInstance();

    public static void main(String[] args) {
        // Test parsing TO json
        String json = JSON_PARSER.parse(new Player("ItzStonlex", 10));

        System.out.println(json);


        // Test parsing FROM json
        Player player = JSON_PARSER.parse(json, Player.class);

        System.out.println(player.name);
        System.out.println(player.status);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Player implements JsonSerializable {

        private String name;
        private int status;

        @Override
        public void serialize(@NonNull JsonMap jsonMap) {
            jsonMap.put("name", name);
            jsonMap.put("status", status);
        }

        @Override
        public void deserialize(@NonNull JsonMap jsonMap) {
            name = jsonMap.get("name");
            status = jsonMap.<Integer>get("status");
        }
    }

}
