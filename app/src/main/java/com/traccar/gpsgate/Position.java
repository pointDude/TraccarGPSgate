/*
 * Copyright 2015 - 2018 Anton Tananaev (anton@traccar.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.traccar.gpsgate;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static java.lang.Math.abs;

public class Position {

    public Position() {
    }

    public Position(String deviceId, Location location, double battery) {
        this.deviceId = deviceId;


        time = new Date(location.getTime());
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        speed = location.getSpeed() * 1.943844; // speed in knots
        course = location.getBearing();
        if (location.getProvider() != null && !location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            accuracy = location.getAccuracy();
        }
        this.battery = battery;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            this.mock = location.isFromMockProvider();
        }

        if (latitude < 0) {
            this.NsHem = "S";
        }else {
            this.NsHem = "N";
        }
        //doing the same with East West
        if (longitude < 0){
            this.EwHem = "W";
        }else {
            this.EwHem = "E";
        }
        // Changes to make the Tracker one Format Time
        try{
            Date date = new Date(location.getTime());
            //DateFormat dateFormat = new SimpleDateFormat("ddMMYY,HHmmss.SS");
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy,HHmmss.SS");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String preGPSgate = dateFormat.format(date);
            GPSgateTime = preGPSgate;

        }catch (Exception e){
            GPSgateTime = "020304";
        }
        //Latitude and Longitude
        //change to get DDMM.mmm format
        double getsLatitude = (location.getLatitude());
        String preLatitude = location.convert(getsLatitude, location.FORMAT_MINUTES);
        String preLatitude2 = preLatitude.replace(":","");
        double preLatitude3 = Double.parseDouble(preLatitude2);
        GPSgateLatitude = abs(preLatitude3);
        //Repeat change for Longitude
        double getsLongitude = (location.getLongitude());
        String preLongitude = location.convert(getsLongitude, location.FORMAT_MINUTES);
        String preLongitude2 = preLongitude.replace(":","");
        double preLongitude3 = Double.parseDouble(preLongitude2);
        GPSgateLongitude = abs(preLongitude3);

    }

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    private double latitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    private double longitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private double altitude;

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    private double speed;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private double course;

    public double getCourse() {
        return course;
    }

    public void setCourse(double course) {
        this.course = course;
    }

    private double accuracy;

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    private double battery;

    public double getBattery() {
        return battery;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    private boolean mock;

    public boolean getMock() {
        return mock;
    }

    public void setMock(boolean mock) {
        this.mock = mock;
    }



    private String NsHem = "S";
    public String getNsHem() {
        return NsHem;
    }
    public void setNsHem(String NsHem) {
        this.NsHem = NsHem;
    }

    //East and West Hemisphere
    private String EwHem = "W";
    public String getEwHem() {
        return EwHem;
    }
    public void setEwHem(String EwHem){
        this.EwHem = EwHem;
    }

    private String GPSgateTime;
    public String getGPSgateTime(){
        return GPSgateTime;
    }
    public void setGPSgateTime(String GPSgateTime){
        this.GPSgateTime =GPSgateTime;
    }


    private double GPSgateLatitude;
    public double getGPSgateLatitude() {
        return GPSgateLatitude;
    }
    public void setGPSgateLatitude(double GPSgateLatitude) {
        this.GPSgateLatitude = GPSgateLatitude;
    }
    private double GPSgateLongitude;
    public double getGPSgateLongitude(){
        return GPSgateLongitude;
    }
    public void setGPSgateLongitude(double GPSgateLongitude) {
        this.GPSgateLongitude = GPSgateLongitude;
    }

}
