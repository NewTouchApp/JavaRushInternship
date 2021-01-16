package com.space.model;

public enum ShipType {
    TRANSPORT,
    MILITARY,
    MERCHANT;

    public static ShipType getShipType(String input) {
        switch (input) {
            case "TRANSPORT":
                return ShipType.TRANSPORT;
            case "MILITARY":
                return ShipType.MILITARY;
            case "MERCHANT":
                return ShipType.MERCHANT;
            default:
                throw  new IllegalArgumentException();
        }
    }
}