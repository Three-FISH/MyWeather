package com.three_fish.myweather.Gson;

import com.google.gson.annotations.SerializedName;

public class Suggestion {


    /**
     * comf : {"txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"}
     * sport : {"txt":"天气较好，较适宜进行各种运动，但因湿度偏高，请适当降低运动强度。"}
     * cw : {"txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}
     */

    @SerializedName("comf")
    private Comfort comfort;

    private Sport sport;

    @SerializedName("cw")
    private CarWash carWash;

    public Comfort getComfort() {
        return comfort;
    }

    public void setComfort(Comfort comfort) {
        this.comfort = comfort;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public CarWash getCarWash() {
        return carWash;
    }

    public void setCarWash(CarWash carWash) {
        this.carWash = carWash;
    }

    public static class Comfort {
        /**
         * txt : 白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。
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

    public static class Sport {
        /**
         * txt : 天气较好，较适宜进行各种运动，但因湿度偏高，请适当降低运动强度。
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

    public static class CarWash {
        /**
         * txt : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
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

