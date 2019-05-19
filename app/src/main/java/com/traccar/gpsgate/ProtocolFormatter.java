/*
 * Copyright 2012 - 2016 Anton Tananaev (anton@traccar.org)
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

import android.net.Uri;

public class ProtocolFormatter {

    public static String formatRequest(String url, Position position) {
        return formatRequest(url, position, null);
    }

    public static String formatRequest(String url, Position position, String alarm) {

        //http://ligo.marshallgis.com:8008/GpsGate/?cmd=$FRCMD,1234,_SendMessage,,4337.88695,N,11616.46606,W,809.6,0.000,0.0,171118,195039.796,1

        Uri serverUrl = Uri.parse(url);

        Uri.Builder builder = serverUrl.buildUpon()
                .path("GpsGate/")
                .encodedQuery(
                                "cmd=$FRCMD"
                                + addParameter(position.getDeviceId())
                                + addParameter("_SendMessage,")
                                + addParameter(String.valueOf(position.getGPSgateLatitude()))
                                + addParameter(String.valueOf(position.getNsHem()))
                                + addParameter(String.valueOf(position.getGPSgateLongitude()))
                                + addParameter(String.valueOf(position.getEwHem()))
                                + addParameter(String.valueOf(position.getAltitude()))
                                + addParameter(String.valueOf(position.getSpeed()))
                                + addParameter(String.valueOf(position.getCourse()))
                                + addParameter(String.valueOf(position.getGPSgateTime()))
                                + addParameter("1")
                                + addParameter("BatteryLevel=" + position.getBattery())
                        )
                .appendQueryParameter("tmp", "tmp");


        if (position.getMock()) {
            builder.appendQueryParameter("mock", String.valueOf(position.getMock()));
        }

        if (alarm != null) {
            builder.appendQueryParameter("alarm", alarm);
        }

        return builder.build().toString();
    }


    public String parameter = "$FRCMD";
    private static String addParameter(String data){
         return "," + data;
    }
}
