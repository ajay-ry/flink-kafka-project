package com.travel.deepblue.travel;

import java.util.ArrayList;
import java.util.List;

public class TravellerBookingData {

    private List<TravelUser> traveller_data;

    public TravellerBookingData(){
        traveller_data = new ArrayList<TravelUser>();
    }

    public void addTravellerUser(TravelUser travelUser){
        traveller_data.add(travelUser);
    }

    public List<TravelUser> getTravellerBooking(){
        return traveller_data;
    }

}
