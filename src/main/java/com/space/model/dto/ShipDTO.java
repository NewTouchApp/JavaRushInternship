package com.space.model.dto;

import com.space.model.ShipType;
import com.space.model.entity.Ship;

import java.util.Date;
import java.util.Objects;

public class ShipDTO {
    private int id; //BIGINT(20)  NOT NULL AUTO_INCREMENT,
    private String name; //VARCHAR(50) NULL,
    private String planet; //VARCHAR(50) NULL,
    private ShipType shipType; //VARCHAR(9)  NULL,
    private Date prodDate; //date        NULL,
    private boolean isUsed; //BIT(1)      NULL,
    private double speed; //DOUBLE      NULL,
    private int crewSize; //INT(4)      NULL,
    private double rating; //DOUBLE      NULL

    public ShipDTO(Ship ship) {
        this.id = ship.getId();
        this.name = ship.getName();
        this.planet = ship.getPlanet();
        this.shipType = ship.getShipType();
        this.prodDate = ship.getProdDate();
        this.isUsed = ship.isUsed();
        this.speed = ship.getSpeed();
        this.crewSize = ship.getCrewSize();
        this.rating = ship.getRating();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(int crewSize) {
        this.crewSize = crewSize;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShipDTO)) return false;
        ShipDTO shipDTO = (ShipDTO) o;
        return id == shipDTO.id && isUsed == shipDTO.isUsed && Double.compare(shipDTO.speed, speed) == 0 && crewSize == shipDTO.crewSize && Double.compare(shipDTO.rating, rating) == 0 && name.equals(shipDTO.name) && planet.equals(shipDTO.planet) && shipType == shipDTO.shipType && prodDate.equals(shipDTO.prodDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, planet, shipType, prodDate, isUsed, speed, crewSize, rating);
    }
}
