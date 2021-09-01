package com.tourkakao.carping.EcoCarping.DTO;

import java.util.List;

public class ResultSearchKeyword {

    private List documents;

    public List getDocuments() {
        return documents;
    }

    public class Place{
        private String place_name;
        private String address_name;
        private String category_name;
        private String road_address_name;
        private String x;
        private String y;

        public String getPlace_name() {
            return place_name;
        }

        public String getAddress_name() {
            return address_name;
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

        public String getCategory_name(){return category_name;}
    }
}


