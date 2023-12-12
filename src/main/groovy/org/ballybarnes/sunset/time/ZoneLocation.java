package org.ballybarnes.sunset.time;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

@AllArgsConstructor(access=AccessLevel.PRIVATE)
public enum ZoneLocation {

    EUROPE_LONDON(51.5072, 0.1276),
    EUROPE_DUBLIN(53.3498,6.2603),
    EUROPE_BELFAST(54.607868, -5.926437);
    private static final Map<String, ZoneLocation> zones = Arrays.stream(ZoneLocation.values()).collect(
        Collectors.toMap(it-> it.name().replace("_","/").toUpperCase(), it2-> it2));

    final double latitude;
    final double longitude;


    public static ZoneLocation findLocationFromZone(ZoneId zoneId) {
        return zones.get(zoneId.getId().toUpperCase());
    }
}

