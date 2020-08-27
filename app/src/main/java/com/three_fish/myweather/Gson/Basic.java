package com.three_fish.myweather.Gson;

import com.google.gson.annotations.SerializedName;

public class Basic {


        /**
         * city : 苏州
         * id : CN101190401
         * update : {"loc":"2020-08-13 16:34"}
         */

        @SerializedName("city")
        private String cityName;
        @SerializedName("id")
        private String weatherId;
        private Update update;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getWeatherId() {
            return weatherId;
        }

        public void setWeatherId(String weatherId) {
            this.weatherId = weatherId;
        }

        public Update getUpdate() {
            return update;
        }

        public void setUpdate(Update update) {
            this.update = update;
        }

        public static class Update {
            /**
             * loc : 2020-08-13 16:34
             */

            @SerializedName("loc")
            private String updateTime;

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }

