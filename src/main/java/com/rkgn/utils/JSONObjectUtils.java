package com.rkgn.utils;

import cn.hutool.json.JSONObject;
import com.rkgn.entity.*;

import java.sql.Timestamp;

public class JSONObjectUtils {
    public static User parseUser(Object jsonObj) {
        JSONObject map = (JSONObject) jsonObj;
        String username = (String) map.get("name");
        String password = (String) map.get("password");
        return switch ((String) map.get("role")) {
            case "browser" -> new Browser(username, password);
            case "operator" -> new Operator(username, password);
            case "administrator" -> new Administrator(username, password);
            default -> null;
        };
    }

    public static Doc parseDoc(Object jsonObj) {
        JSONObject map = (JSONObject) jsonObj;
        String id = (String) map.get("id");
        String creator = (String) map.get("creator");
        Timestamp timestamp = new Timestamp((long) map.get("timestamp"));
        String description = (String) map.get("description");
        String filename = (String) map.get("filename");
        return new Doc(id, creator, timestamp, description, filename);
    }
}
