package com.tourkakao.carping.Map.DataClass;

import java.util.List;

public class MapSearch{
    private List documents;

    public List getDocuments() {
        return documents;
    }



    public class Place implements Comparable<Place>{
        private String place_name;
        private String address_name;
        private String category_name;
        private String road_address_name;
        private String x;
        private String y;
        private String distance;

        public String getPlace_name() {
            return place_name;
        }

        public String getAddress_name() {
            return address_name;
        }

        public String getCategory_name() {
            return category_name;
        }

        public String getRoad_address_name() {
            return road_address_name;
        }

        public String getX() {
            return x;
        }

        public String getY() {
            return y;
        }

        public String getDistance() {
            return distance;
        }

        @Override
        public int compareTo(Place o) {
            if(o.getDistance().trim()!="" && o.getDistance()!=null && !o.getDistance().isEmpty()) {
                return Integer.parseInt(getDistance()) - Integer.parseInt(o.getDistance());
            }
            return 0;
        }
    }
}
