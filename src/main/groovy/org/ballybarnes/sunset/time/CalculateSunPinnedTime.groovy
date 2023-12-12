package org.ballybarnes.sunset.time

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator
import com.luckycatlabs.sunrisesunset.dto.Location

import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.util.stream.Collectors


class CalculateSunPinnedTime {
    def static ninePMSecondOfDay = 75600
    def static final SECONDS_IN_DAY = 86400

    static void main(String [] args) {
        def calendar = Calendar.getInstance(TimeZone.getDefault())
        def result = calculatePinnedTime(calendar)
        def zones = TimeZone.getAvailableIDs().findAll {it-> it.contains("Europe")}
        def results = zones.stream().map {[it,calculatePinnedTime(Calendar.getInstance(TimeZone.getTimeZone(it)))]}.collect(Collectors.toList())
        print results.toString()
    }

    static def calculatePinnedTime(Instant instant, ZoneId zone) {
        def zoneLocation = ZoneLocation.findLocationFromZone(zone)
        Location location = new Location(zoneLocation.latitude, zoneLocation.longitude)
        def sunCalc = new SunriseSunsetCalculator(location, zone.id)
        Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        def sunsetTimeUTC = sunCalc.getOfficialSunsetCalendarForDate(localCalendar).toInstant()
        def secondsInDay = sunsetTimeUTC.getEpochSecond() % SECONDS_IN_DAY
        def offsetSeconds = ninePMSecondOfDay - secondsInDay
        return LocalTime.ofInstant(instant.plusSeconds(offsetSeconds), zone)
    }

    static def calculatePinnedTime(Calendar calendar) {
        def instant = calendar.toInstant()
        def zone = calendar.getTimeZone()
        return calculatePinnedTime(instant, zone.toZoneId())
    }
}