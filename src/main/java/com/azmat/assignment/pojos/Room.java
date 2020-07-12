package com.azmat.assignment.pojos;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.azmat.assignment.process.MyRandom;

/* This class created Room dynamically, it also fill
 guest list with dynamically generated guests*/

public class Room implements ToJson {

    private List<Guest> guest;

    public List<Guest> getGuest() {
        return guest;
    }

    public void setGuest(List<Guest> guest) {
        this.guest = guest;
    }

    public Room() {

    }

    public Room(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("guest");
        int guestListSize = jsonArray.length();
        List<Guest> guestList = new ArrayList<Guest>(guestListSize);
        for (int i = 0; i < guestListSize; i++) {
            guestList.add(new Guest(jsonArray.getJSONObject(i)));
        }
    }

    public static Room getRoom(String jsonStr) {
        return new Room(new JSONObject(jsonStr));
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("guest", this.guest);
        return jsonObject;
    }

    /* This method generates Rooms Dynamically */
    public static Room getDynamicRoom() {
        Room room = new Room();

        int guestListSize = new MyRandom().nextInt();
        List<Guest> guestList = new ArrayList<Guest>(guestListSize);

        for (int i = 0; i < guestListSize; i++) {
            guestList.add(Guest.getDynamicGuest());
        }
        room.setGuest(guestList);

        return room;
    }

}
