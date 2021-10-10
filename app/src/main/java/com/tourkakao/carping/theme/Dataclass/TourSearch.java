package com.tourkakao.carping.theme.Dataclass;

import java.util.List;

public class TourSearch {
    private List documents;

    public List getDocuments() {
        return documents;
    }

    public class Place implements Comparable<Place>{
        private String place_name;
        private String phone;
        private String address_name;
        private String category_name;
        private String road_address_name;
        private String x;
        private String y;
        private String place_url;
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

        public String getPhone() {
            return phone;
        }

        public String getPlace_url() {
            return place_url;
        }

        @Override
        public int compareTo(Place o) {
            if (o.getDistance().trim() != "" && o.getDistance() != null && !o.getDistance().isEmpty()) {
                return Integer.parseInt(getDistance()) - Integer.parseInt(o.getDistance());
            }
            return 0;
        }
    }
}
