package genspark.group.boardingpass.model;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Ticket implements Serializable {
    /*
   Boarding Pass Number, Date, Origin, Destination, Estimated time of arrival (ETA), Departure Time
   Name, Email, Phone Number, Gender, Age
   Total Ticket Price
   */
    private final UUID boardingPassNumber;
    private Date date;
    private String origin;
    private String destination;
    private final Date eta;
    private final Date departureTime;
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private int age;
    private final double price;

    public Ticket( Date date,String origin, String destination, Date departureTime, String name,
                   String email, String phoneNumber, String gender, int age) {
        this.date= date;
        this.destination= destination;
        this.departureTime= departureTime;
        this.name= name;
        this.email= email;
        this.phoneNumber= phoneNumber;
        this.gender= gender;
        this.age= age;
        this.origin= origin;

        this.boardingPassNumber= (generateBoardingPassNumber());
        this.eta= generateEta(this.departureTime);
        this.price= generatePrice(this.age, this.gender, this.eta, this.departureTime);
    }

    private UUID generateBoardingPassNumber() { // attempting to use UUId and inserted MAX_VALUE to force positive
        return UUID.randomUUID();
    }
    public UUID getBoardingPassNumber() {return boardingPassNumber;}

    /*
    setters / getters :
    name, age, gender, phoneNumber, email
    date, origin, destination, departureTime
    departure time, arrival time, flight time
    ticket price, gender discount, age discount, price
    */
    public void setName(String name) {this.name = name;}
    public String getName() {return name;}

    public void setAge(int age) {this.age = age;}
    public int getAge() {return age;}

    public void setGender(String gender) {this.gender = gender;}
    public String getGender() {return gender;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getPhoneNumber() {return phoneNumber;}

    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return email;}

    public void setDate(Date date) {this.date = date;}
    public Date getDate() {return date;}

    public void setOrigin(String origin) {this.origin = origin;}
    public String getOrigin() {return origin;}

    public void setDestination(String destination) {this.destination = destination;}
    public String getDestination() {return destination;}

    public Date getEta() { // arrival (simple date format)
        return eta;
    }

    public Date getDepartureTime() { // departure (sim[ple date format)
        return departureTime;
    }

    public int getFlightTime() { // flight time : (8 hr)
        return flightTime(eta, departureTime);
    }

    public double getTicketPrice() { // ticket price :
        return ticketPrice(eta, departureTime);
    }

    public double getGenderDiscount() { // gender discount :
        return genderDiscount(gender, eta, departureTime);
    }

    public double getAgeDiscount() { // age discount :
        return ageDiscount(age, eta, departureTime);
    }

    public double getTotalPrice() { // total cost :
        return price;
    }

    private Date generateEta(Date departureTime) { // randomizes flight duration based on avg
        int rand= ThreadLocalRandom.current().nextInt(3, 12+ 1);
        Calendar cl= Calendar.getInstance();
        cl.setTime(departureTime);
        cl.add(Calendar.HOUR_OF_DAY, rand);
        return cl.getTime();

    }

    /*
    Age <= 12, 50% reduction of ticket price regardless of gender
    Age >= 60, 60% reduction of ticket price regardless of gender
    Females, 25% discount on the ticket price
    */
    private double generatePrice(int age, String gender, Date eta, Date departureTime){
        double price= ticketPrice(eta, departureTime);
        double genderDiscount= genderDiscount(gender, eta, departureTime);
        double ageDiscount= ageDiscount(age, eta, departureTime);
        return price- genderDiscount- ageDiscount;
    }

    // flight details
    private Date getDepartureTime(Date date) { // simple format - 'time' to get departure time
        return date;
    }

    private Date getArrivalTime(Date eta) { // simple format - 'time' to get arrival time
        return eta;
    }

    private int flightTime(Date eta, Date departureTime) { // flight time returns as int ~ e.g 8 hrs
        Calendar c= Calendar.getInstance();
        c.setTime(eta);
        int etaHour= c.get(Calendar.HOUR_OF_DAY); // get hour of eta
        c.setTime(departureTime);
        int departureHour= c.get(Calendar.HOUR_OF_DAY); // get hour of departure
        return etaHour-  departureHour;
    }

    // pricing details :
    private double ticketPrice(Date eta, Date departureTime) { // returns total price of ticket
        int flightHours= flightTime(eta, departureTime);
        return flightHours* (80);
    }

    private double genderDiscount(String gender, Date eta, Date departureTime) { // returns gender discount
        double price= ticketPrice(eta, departureTime);
        if(gender.toLowerCase().startsWith("f")) {
            price*= .25;
        } else return 0;
        return price;
    }

    private double ageDiscount(int age, Date eta, Date departureTime) { // returns age discount
        double price= ticketPrice(eta, departureTime);
        if(age<= 12) {
            price*= 0.5;
        } else if(age>= 60) {
            price *= 0.6;
        } else return 0;
        return price;
    }



    @Override
    public String toString() {
        return "BoardingPass{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", boardingPassNumber=" + boardingPassNumber +
                ", date='" + date + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", estimatedTimeOfArrival='" + eta + '\'' +
                ", ticketPrice=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        Ticket ticket;

        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;

        ticket = (Ticket) o;
        if(boardingPassNumber == null) return false;
        if(boardingPassNumber.equals(ticket.boardingPassNumber))return true;

        //Numbers didn't match
        return false;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getBoardingPassNumber() != null ? getBoardingPassNumber().hashCode() : 0;
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getOrigin() != null ? getOrigin().hashCode() : 0);
        result = 31 * result + (getDestination() != null ? getDestination().hashCode() : 0);
        result = 31 * result + (eta != null ? eta.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        result = 31 * result + getAge();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}