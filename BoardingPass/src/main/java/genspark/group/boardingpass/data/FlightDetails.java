package genspark.group.boardingpass.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class FlightDetails {
    private String origin;
    private String destination;

    private double distance;
    private LocalTime flightDuration;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private LocalDate departureDate;
    private Map<String, Double> airports = new HashMap<>();

    public FlightDetails() {
        this.distance = 0;
        this.flightDuration = null;
        this.arrivalTime = null;
        this.departureTime = null;
        this.departureDate = null;
        this.origin = "";
        this.destination = "";
        airports.put("ATL", 0.0);
        airports.put("LAX", 1943.0);
        airports.put("ORD", 607.0);
        airports.put("DFW", 731.0);
        airports.put("DEN", 1407.0);
        airports.put("JFK", 760.0);
        airports.put("SFO", 2139.0);
        airports.put("LAS", 1747.0);
        airports.put("SEA", 2182.0);
        airports.put("CLT", 227.0);
        airports.put("MCO", 404.0);
        airports.put("MIA", 595.0);
        airports.put("PHX", 1587.0);
        airports.put("EWR", 746.0);
        airports.put("IAH", 689.0);
    }
    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public double getDistance() {
        setDistance(destination);
        return distance;
    }
    public double setDistance(String destination) {
        String key = destination;
        for (Map.Entry<String, Double> airport : airports.entrySet()) {
            if (airport.getKey() == key) {
                distance = airport.getValue();
            }
        }
        return distance;
    }

    public LocalTime getFlightDuration() {
        setFlightDuration();
        return flightDuration;
    }

    public LocalTime setFlightDuration() {
        getDistance();
        BigDecimal time = BigDecimal.valueOf(distance / 530)
                .setScale(2, RoundingMode.HALF_UP);

        int hour = (int) Math.floor(time.doubleValue());
        int min = (int) ((time.doubleValue() - hour ) * 100) / 60;
        return flightDuration = LocalTime.of(hour, min);
    }
    public LocalTime getArrivalTime() {
        setArrivalTime();
        return arrivalTime;
    }

    public void setArrivalTime() {
        int hours = this.getDepartureTime().getHour() + this.getFlightDuration().getHour();
        int min = this.getDepartureTime().getMinute() + this.getFlightDuration().getMinute();
        if (min > 59) {
            hours +=min /60;
            min = min % 60;
        }
        this.arrivalTime = LocalTime.of(hours, min);
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
