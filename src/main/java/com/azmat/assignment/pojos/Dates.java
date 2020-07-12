package com.azmat.assignment.pojos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.json.JSONObject;

import com.azmat.assignment.process.MyRandom;

/* This class generates CheckIn & CheckOut dates dynamically*/
public class Dates implements ToJson {

    private static final String PATTERN = "dd-MM-yyyy";

    private String checkin;
    private String checkout;

    public String getCheckIn() {
        return checkin;
    }

    public void setCheckIn(String checkIn) {
        this.checkin = checkIn;
    }

    public String getCheckOut() {
        return checkout;
    }

    public void setCheckOut(String checkOut) {
        this.checkout = checkOut;
    }

    public Dates() {

    }

    public Dates(JSONObject jsonObject) {
        this.checkin = jsonObject.getString("checkin");
        this.checkout = jsonObject.getString("checkout");
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("checkin", this.checkin);
        jsonObject.put("checkout", this.checkout);
        return jsonObject;
    }

    public static Dates getDates(String jsonStr) {
        return new Dates(new JSONObject(jsonStr));

    }

    /* Method to generate CheckIn & CheckOut dates dynamically */
    public static Dates getDynamicDates() {
        Dates dates = new Dates();

        Random random = new MyRandom();
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(Dates.PATTERN);

        int delta = random.nextInt();
        calendar.add(Calendar.DAY_OF_MONTH, delta);
        dates.setCheckIn(dateFormat.format(calendar.getTime()));

        delta = random.nextInt();
        calendar.add(Calendar.DAY_OF_MONTH, delta);
        dates.setCheckOut(dateFormat.format(calendar.getTime()));

        return dates;
    }

}
