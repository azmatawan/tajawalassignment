package com.azmat.assignment.pojos;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.azmat.assignment.process.MyRandom;

/* This class generates Request Body*/
public class Body implements ToJson {

    private Dates dates;
    private String destination = "paris";
    private List<Room> room;
    private String placeId = "ChIJD7fiBh9u5kcRYJSMaMOCCwQ";

    public Body() {

    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Room> getRoom() {
        return room;
    }

    public void setRoom(List<Room> room) {
        this.room = room;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Body(JSONObject jsonObject) {
        this.dates = new Dates(jsonObject.getJSONObject("dates"));
        this.destination = jsonObject.getString("destination");

        JSONArray jsonArray = jsonObject.getJSONArray("room");
        int roomListSize = jsonArray.length();
        List<Room> roomList = new ArrayList<Room>(roomListSize);
        for (int i = 0; i < roomListSize; i++) {
            roomList.add(new Room(jsonArray.getJSONObject(i)));
        }
        this.room = roomList;
        this.placeId = jsonObject.getString("placeId");
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dates", this.dates.toJson());
        jsonObject.put("destination", this.destination);
        jsonObject.put("room", this.room);
        jsonObject.put("placeId", this.placeId);
        return jsonObject;
    }

    public static Body getBody(String jsonStr) {
        return new Body(new JSONObject(jsonStr));
    }

    public static Body getDynamicBody() {
        Body body = new Body();

        body.setDates(Dates.getDynamicDates());
        int roomListSize = new MyRandom().nextInt();
        List<Room> roomList = new ArrayList<Room>(roomListSize);
        for (int i = 0; i < roomListSize; i++) {
            roomList.add(Room.getDynamicRoom());
        }
        body.setRoom(roomList);

        return body;
    }

}
