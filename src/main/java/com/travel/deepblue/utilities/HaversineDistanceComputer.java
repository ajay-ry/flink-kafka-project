package com.travel.deepblue.utilities;

import java.text.DecimalFormat;

//Compute Haversine distance between the pairs of lat,lon
public class HaversineDistanceComputer {

    public static double calculate_distance(String latlon1, String latlon2) {

        try{

            /*
            Haversine
            formula:	a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
            c = 2 ⋅ atan2( √a, √(1−a) )
            d   = R ⋅ c
            where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
            note that angles need to be in radians to pass to trig functions!
             */
            String latlon_1[] = latlon1.split(",");

            Double lat1 = Double.parseDouble(latlon_1[0]);
            Double lon1 = Double.parseDouble(latlon_1[1]);

            String latlon_2[] = latlon2.split(",");
            Double lat2 = Double.parseDouble(latlon_2[0]);
            Double lon2 = Double.parseDouble(latlon_2[1]);

            final int R = 6371; // Radius of the earth, radius = 6,371km

            double latDistance = Math.toRadians(lat2 - lat1);
            double lonDistance = Math.toRadians(lon2 - lon1);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = R * c; // distance in Kilometers

            DecimalFormat df = new DecimalFormat("###.##");

            String dist_decimal = df.format(distance);
            double dist_km = Double.parseDouble(dist_decimal);
            return dist_km;
        }
        catch(Exception e){
            return 0;
        }
    }

}
