package com.space.model.entity;

import com.space.model.ShipType;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ship")
public class Ship {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id; //BIGINT(20)  NOT NULL AUTO_INCREMENT,
    @Column (name = "name")
    private String name; //VARCHAR(50) NULL,
    @Column (name = "planet")
    private String planet; //VARCHAR(50) NULL,
    @Enumerated (EnumType.STRING)
    @Column (name = "shipType")
    private ShipType shipType; //VARCHAR(9)  NULL,
    @Column (name = "prodDate")
    private Date prodDate; //date        NULL,
    @Column (name = "isUsed")
    private boolean isUsed; //BIT(1)      NULL,
    @Column (name = "speed")
    private double speed; //DOUBLE      NULL,
    @Column (name = "crewSize")
    private int crewSize; //INT(4)      NULL,
    @Column (name = "rating")
    private double rating; //DOUBLE      NULL

    public Ship(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlanet() {
        return planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public double getSpeed() {
        return speed;
    }

    public int getCrewSize() {
        return crewSize;
    }

    public double getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setCrewSize(int crewSize) {
        this.crewSize = crewSize;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ship)) return false;
        Ship ship = (Ship) o;
        return id == ship.id && isUsed == ship.isUsed && Double.compare(ship.speed, speed) == 0 && crewSize == ship.crewSize && Double.compare(ship.rating, rating) == 0 && Objects.equals(name, ship.name) && Objects.equals(planet, ship.planet) && shipType == ship.shipType && Objects.equals(prodDate, ship.prodDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, planet, shipType, prodDate, isUsed, speed, crewSize, rating);
    }
}