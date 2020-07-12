package com.azmat.assignment.pojos;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.azmat.assignment.process.MyRandom;

/*Guest Class written to generate guest (ADT & CHD).
 * It generates guests dynamically, If Guest is
 * a child than its age would be between 1 to 16.
 * For adult age param is not needed */

public class Guest implements ToJson {

    private static final String TYPE_ADT = "ADT";
    private static final String TYPE_CHD = "CHD";

    private String type;
    private Integer age;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Guest() {

    }

    public Guest(JSONObject jsonObject) {
        this.type = jsonObject.getString("type");
        try {
            this.age = jsonObject.getInt("age");
        } catch (JSONException e) {

        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", this.type);
        if (this.age != null) {
            jsonObject.put("age", this.age);

        }
        return jsonObject;
    }

    public static Guest getGuest(String jsonStr) {
        return new Guest(new JSONObject(jsonStr));
    }

    /* Method to generate guest dynamically */
    public static Guest getDynamicGuest() {
        Guest guest = new Guest();

        Random random = new MyRandom();
        if (random.nextBoolean()) {
            guest.setType(Guest.TYPE_ADT);
        } else {
            guest.setType(Guest.TYPE_CHD);
            guest.setAge(random.nextInt());
        }

        return guest;
    }

}
