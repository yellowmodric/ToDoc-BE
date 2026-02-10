package com.solinone.todoc.global.util;

public class LocationUtils {

    private static final double EARTH_RADIUS_KM = 6371.0;

    /**
     * 두 지점 간 거리 계산(Haversine 공식)
     * @return 거리 (미터)
     */

    public static double calculateDistance(
            double lat1, double lon1, //가게 위치
            double lat2, double lon2
    ) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return EARTH_RADIUS_KM * c * 1000; //미터 단위로 변환
    }

    /**
     * 범위 내 여부 확인
     * @param distance 거리 (미터)
     * @param rangeMeters 허용 범위 (미터)
     */
    public static boolean isWithinRange(double distance, double rangeMeters) {
        return distance <= rangeMeters;
    }
}
