package com.three_fish.myweather.Gson;

import com.google.gson.annotations.SerializedName;

public class Forecast {


    /**
     * date : 2020-08-14
     * cond : {"txt_d":"多云"}
     * tmp : {"max":"21","min":"14"}
     */

    private String date;
    @SerializedName("cond")
    private More more;
    @SerializedName("tmp")
    private Tmperature tmperature;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public More getMore() {
        return more;
    }

    public void setMore(More more) {
        this.more = more;
    }

    public Tmperature getTmperature() {
        return tmperature;
    }

    public void setTmperature(Tmperature tmperature) {
        this.tmperature = tmperature;
    }

    public static class More {
        /**
         * txt_d : 多云
         */

        @SerializedName("txt_d")
        private String info;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    public static class Tmperature {
        /**
         * max : 21
         * min : 14
         */

        private String max;
        private String min;

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }
    }
}
