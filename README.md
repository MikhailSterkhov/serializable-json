<div align="center">

# Serializable JSON
[![MIT License](https://img.shields.io/github/license/pl3xgaming/Purpur?&logo=github)](License)

---
### *WARNING!*

#### *This library is not a replacement for something more efficient in the field of JSON, it was created by me for self-practice*

</div>

------------------------------------------
### Feedback
* **[Discord Server](https://discord.gg/GmT9pUy8af)**
* **[VKontakte Page](https://vk.com/itzstonlex)**

---
## Base API

Creating a JSON Parser example:
```java
public static final JsonParser JSON_PARSER = JsonParser.newInstance();
```
---
## Serialization
JSON serialization is not required for object parsing! It's a easy method for objects parsing.


Creation a serializable object example:
```java
@NoArgsConstructor // Required
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
        name = jsonMap.get("name", String.class);
        status = jsonMap.get("status", int.class);
    }
}
```
Deserialize objects methods without classes example:

```java
@Override
public void deserialize(@NonNull JsonMap jsonMap) {
    this.name = jsonMap.<String>get("name");
    this.status = jsonMap.<Integer>get("status");
}
```
---
## JSON Parsing

Parsing object to JSON example:
```java
String json = JSON_PARSER.parse(new Player("ItzStonlex", 10));
```


Parsing JSON to object example:
```java
Player player = JSON_PARSER.parse(json, Player.class);
```

---

## SUPPORT ME

<a href="https://www.buymeacoffee.com/itzstonlex" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: 41px !important;width: 174px !important;box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;-webkit-box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;" ></a>

