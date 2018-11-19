package com.travel.deepblue.adexchange;

import java.util.ArrayList;
import java.util.List;

public class ExchangeData {

    private List<MobileUser> exchange_data;

    public ExchangeData(){
        exchange_data = new ArrayList<MobileUser>();
    }

    public void addMobileUser(MobileUser mobileUser){
        exchange_data.add(mobileUser);
    }

    public List<MobileUser> getExchangeData(){
        return exchange_data;
    }

}
