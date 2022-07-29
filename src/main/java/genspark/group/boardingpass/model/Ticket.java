package genspark.group.boardingpass.model;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Ticket {
    /*
   Boarding Pass Number, Date, Origin, Destination, Estimated time of arrival (ETA), Departure Time
   Name, Email, Phone Number, Gender, Age
   Total Ticket Price
   */
    private UUID boardingPassNumber;
    private Date date;
    private String origin;
    private String destination;
    private Date eta;
    private Date departureTime;
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private int age;
    private double price;

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

    private Date generateEta(Date departureTime) { // randomizes flight duration based on avg
        int rand= ThreadLocalRandom.current().nextInt(3, 12+ 1);
        long HOUR= 3_600* 1_000;
        return new Date(departureTime.getTime()* rand* HOUR);
    }

    /*
    Age <= 12, 50% reduction of ticket price regardless of gender
    Age >= 60, 60% reduction of ticket price regardless of gender
    Females, 25% discount on the ticket price
    */

    private double generatePrice(int age, String gender, Date eta, Date departureTime){
        Calendar c= Calendar.getInstance();
        c.setTime(eta);
        int etaHour= c.get(Calendar.HOUR_OF_DAY); // get hour of eta
        c.setTime(departureTime);
        int departureHour= c.get(Calendar.HOUR_OF_DAY); // get hour of departure
        int flightHours= etaHour-  departureHour;
        double price= (flightHours* 515)* (1.30); // avg cost/mi, 460 – 575 mph per hour

        if(age<= 12) {
            price*= 0.5;
        } else if(age>= 60) {
            price*= 0.4;
        } else if(gender.toLowerCase().startsWith("f")) {
            price*= 0.75;
        }
        return price;
    }

    public UUID getBoardingPassNumber() {
        return boardingPassNumber;
    }

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public String getDestination() {return destination;}

    public void setDestination(String destination) {this.destination = destination;}

    public Date getDepartureTime() {return departureTime;}

    public void setDepartureTime(Date departureTime) {this.departureTime = departureTime;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getGender() {return gender;}

    public void setGender(String gender) {this.gender = gender;}

    public int getAge() {return age;}

    public void setAge(int age) {this.age = age;}

    public String getOrigin() {return origin;}

    public void setOrigin(String origin) {this.origin = origin;}

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
        result = boardingPassNumber != null ? boardingPassNumber.hashCode() : 0;
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getOrigin() != null ? getOrigin().hashCode() : 0);
        result = 31 * result + (getDestination() != null ? getDestination().hashCode() : 0);
        result = 31 * result + (eta != null ? eta.hashCode() : 0);
        result = 31 * result + (getDepartureTime() != null ? getDepartureTime().hashCode() : 0);
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
