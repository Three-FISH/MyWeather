package com.three_fish.myweather.Gson;

import com.google.gson.annotations.SerializedName;

public  class Now {
        /**
         * tmp : 18
         * cond : {"txt":"多云"}
         */

        @SerializedName("tmp")
        private String tmperature;
        @SerializedName("cond")
        private More more;

        public String getTmperature() {
            return tmperature;
        }

        public void setTmperature(String tmperature) {
            this.tmperature = tmperature;
        }

        public More getMore() {
            return more;
        }

        public void setMore(More more) {
            this.more = more;
        }

        public static class More {
            /**
             * txt : 多云
             */

            @SerializedName("txt")
            private String info;

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }
        }
    }
