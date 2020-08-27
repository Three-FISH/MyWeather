package com.three_fish.myweather.Gson;

public class AQI {
        /**
         * city : {"aqi":"54","pm25":"29"}
         */

        private AqiCity city;
        public AqiCity getCity() {
            return city;
        }

        public void setCity(AqiCity city) {
            this.city = city;
        }

        public static class AqiCity {
            /**
             * aqi : 54
             * pm25 : 29
             */

            private String aqi;
            private String pm25;

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

    }
}
