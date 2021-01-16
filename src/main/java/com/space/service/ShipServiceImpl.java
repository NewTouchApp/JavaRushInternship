package com.space.service;

import com.space.model.ShipType;
import com.space.model.dto.ShipDTO;
import com.space.model.entity.Ship;
import com.space.repository.ShipDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShipServiceImpl implements ShipService {

    final ShipDAO shipDAO;

    @Autowired
    public ShipServiceImpl(ShipDAO shipDAO) {
        this.shipDAO = shipDAO;
    }


    @Override
    public List<ShipDTO> findAllShips(Map<String, String> filter) {
        List<ShipDTO> ships = shipDAO.findAll().stream().map(ShipDTO::new).collect(Collectors.toList());
        if (filter.size() != 0) {
            ships = filteredShiLst(ships, filter);
        }

        String order = filter.getOrDefault("order", "");
        int pageNumber = filter.containsKey("pageNumber") ? Integer.parseInt(filter.get("pageNumber")) : 0;
        int pageSize = filter.containsKey("pageSize") ? Integer.parseInt(filter.get("pageSize")) : 3;
        ships = ships.stream().sorted(getComparator(order)).skip(pageNumber * pageSize).limit(pageSize).collect(Collectors.toList());

        return  ships;
    }

    private List<ShipDTO> filteredShiLst(List<ShipDTO> ships, Map<String, String> filter) {
        List<ShipDTO> filteredShips = new ArrayList<>();
        for (ShipDTO shipDTO: ships) {
            if (filter.containsKey("name") && !shipDTO.getName().contains(filter.get("name"))) {
                continue;
            }
            if (filter.containsKey("planet") && !shipDTO.getPlanet().contains(filter.get("planet"))) {
                continue;
            }
            if (filter.containsKey("after") && (shipDTO.getProdDate().getTime() < Long.parseLong(filter.get("after")))) {
                continue;
            }
            if (filter.containsKey("before") && shipDTO.getProdDate().getTime() > Long.parseLong(filter.get("before"))) {
                continue;
            }
            if (filter.containsKey("shipType") && !shipDTO.getShipType().toString().equals(filter.get("shipType"))) {
                continue;
            }
            if (filter.containsKey("isUsed") && !shipDTO.isUsed() == Boolean.parseBoolean(filter.get("isUsed"))) {
                continue;
            }

            if (filter.containsKey("minSpeed")) {
                double minSpeed = Double.parseDouble(filter.get("minSpeed"));
                double speed = shipDTO.getSpeed();
                if (speed - minSpeed < 0.00001) {
                    continue;
                }
            }
            if (filter.containsKey("maxSpeed")) {
                double minSpeed = Double.parseDouble(filter.get("maxSpeed"));
                double speed = shipDTO.getSpeed();
                if (speed - minSpeed > 0.00001) {
                    continue;
                }
            }
            if (filter.containsKey("minCrewSize")) {
                int minCrewSize = Integer.parseInt(filter.get("minCrewSize"));
                int crewSize = shipDTO.getCrewSize();
                if (crewSize - minCrewSize < 0) {
                    continue;
                }
            }
            if (filter.containsKey("maxCrewSize")) {
                int maxCrewSize = Integer.parseInt(filter.get("maxCrewSize"));
                int crewSize = shipDTO.getCrewSize();
                if (crewSize - maxCrewSize > 0) {
                    continue;
                }
            }
            if (filter.containsKey("minRating")) {
                double minRating = Double.parseDouble(filter.get("minRating"));
                double rating = shipDTO.getRating();
                if (rating - minRating < 0.00001) {
                    continue;
                }
            }
            if (filter.containsKey("maxRating")) {
                double maxRating = Double.parseDouble(filter.get("maxRating"));
                double rating = shipDTO.getRating();
                if (rating - maxRating > 0.00001) {
                    continue;
                }
            }
            filteredShips.add(shipDTO);
        }
        return filteredShips;
    }

    private Comparator<ShipDTO> getComparator (String order) {
        switch (order) {
            case "SPEED":
                return Comparator.comparingDouble(ShipDTO::getSpeed);
            case "DATE":
                return Comparator.comparing(ShipDTO::getProdDate);
            case "RATING":
                return Comparator.comparingDouble(ShipDTO::getRating);
            default:
                return Comparator.comparingInt(ShipDTO::getId);
        }
    }

    @Override
    public int getCountShips(Map<String, String> filter) {
        List<ShipDTO> ships = shipDAO.findAll().stream().map(ShipDTO::new).collect(Collectors.toList());
        if (filter.size() != 0) {
            return filteredShiLst(ships, filter).size();
        }
        return  ships.size();
    }

    @Override
    public ShipDTO createShip(Map<String, String> paramForCreate) throws Exception {
        if (!isOkParam(paramForCreate)) {
            throw new Exception();
        }
        Ship ship = new Ship();
        ship = addParamToShip(paramForCreate, ship);
        return new ShipDTO(shipDAO.save(ship));
    }

    private boolean isOkParam(Map<String, String> paramForCreate) {
        if (!paramForCreate.containsKey("name") ||
                paramForCreate.get("name").isEmpty() ||
                paramForCreate.get("name").length() > 50 ||
                !paramForCreate.containsKey("planet") ||
                paramForCreate.get("planet").isEmpty() ||
                paramForCreate.get("planet").length() > 50 ||
                !paramForCreate.containsKey("speed") ||
                Double.parseDouble(paramForCreate.get("speed")) > 0.99 ||
                Double.parseDouble(paramForCreate.get("speed")) < 0.01 ||
                !paramForCreate.containsKey("crewSize") ||
                Integer.parseInt(paramForCreate.get("crewSize")) < 1 ||
                Integer.parseInt(paramForCreate.get("crewSize")) > 9999 ||
                !paramForCreate.containsKey("prodDate") ||
                Long.parseLong(paramForCreate.get("prodDate")) < 0 ||
                (new Date(Long.parseLong(paramForCreate.get("prodDate"))).getYear() + 1900) < 2800 ||
                (new Date(Long.parseLong(paramForCreate.get("prodDate"))).getYear() + 1900) > 3019) {
            return false;
        }
        return true;
    }

    private Ship addParamToShip (Map<String, String> paramForCreate, Ship ship) {
        boolean isUsed = paramForCreate.containsKey("isUsed") && Boolean.parseBoolean(paramForCreate.get("isUsed"));
        double speed = Double.parseDouble(paramForCreate.get("speed"));
        double k = isUsed ? 0.5 : 1;
        int y0 = 3019;
        int prodDate = new Date(Long.parseLong(paramForCreate.get("prodDate"))).getYear() + 1900;
        double rating = Math.round((80 * speed * k * 100) / (y0 - prodDate + 1)) * 1.00 / 100;
        ship.setName(paramForCreate.get("name"));
        ship.setPlanet(paramForCreate.get("planet"));
        ship.setUsed(isUsed);
        ship.setShipType(ShipType.getShipType(paramForCreate.get("shipType")));
        ship.setSpeed(speed);
        ship.setCrewSize(Integer.parseInt(paramForCreate.get("crewSize")));
        ship.setProdDate(new Date(Long.parseLong(paramForCreate.get("prodDate"))));
        ship.setRating(rating);
        return ship;
    }

    @Override
    public boolean deleteShip(int id) {
        Optional<Ship> ship = shipDAO.findById(id);
        if (ship.isPresent()) {
            shipDAO.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public ShipDTO getShip(int id) {
        Optional<Ship> ship = shipDAO.findById(id);
        return ship.map(ShipDTO::new).orElse(null);
    }

    @Override
    public ShipDTO updateShip(int id, Map<String, String> paramForCreate) throws Exception {
        Optional<Ship> shipOptional = shipDAO.findById(id);

        if (shipOptional.isPresent()) {
            Ship ship = shipOptional.get();
            if (paramForCreate.containsKey("name")) {
                String name = paramForCreate.get("name");
                if (name.isEmpty() || name.length() > 50)   {
                    throw new IllegalArgumentException();
                }
                ship.setName(name);
            }
            if (paramForCreate.containsKey("planet")) {
                String planet = paramForCreate.get("planet");
                if (planet.isEmpty() || planet.length() > 50)   {
                    throw new IllegalArgumentException();
                }
                ship.setPlanet(planet);
            }
            if (paramForCreate.containsKey("speed")) {
                double speed = Double.parseDouble(paramForCreate.get("speed"));
                if (speed > 0.99 || speed < 0.01)   {
                    throw new IllegalArgumentException();
                }
                ship.setSpeed(speed);
            }
            if (paramForCreate.containsKey("crewSize")) {
                int crewSize = Integer.parseInt(paramForCreate.get("crewSize"));
                if (crewSize < 1 || crewSize > 9999)   {
                    throw new IllegalArgumentException();
                }
                ship.setCrewSize(crewSize);
            }
            if (paramForCreate.containsKey("prodDate")) {
                Date prodDate = new Date(Long.parseLong(paramForCreate.get("prodDate")));
                if (Long.parseLong(paramForCreate.get("prodDate")) < 0 || (prodDate.getYear() + 1900) < 2800 || (prodDate.getYear() + 1900) > 3019)   {
                    throw new IllegalArgumentException();
                }
                ship.setProdDate(prodDate);
            }
            if (paramForCreate.containsKey("shipType")) {
                ShipType shipType = ShipType.getShipType(paramForCreate.get("shipType"));
                ship.setShipType(shipType);
            }
            if (paramForCreate.containsKey("isUsed")) {
                boolean isUsed = Boolean.parseBoolean(paramForCreate.get("isUsed"));
                ship.setUsed(isUsed);
            }

            double k = ship.isUsed() ? 0.5 : 1;
            int y0 = 3019;
            double rating = Math.round((80 * ship.getSpeed() * k * 100) / (y0 - ship.getProdDate().getYear() - 1900 + 1)) * 1.00 / 100;
            ship.setRating(rating);
            return new ShipDTO(ship);
        }
        else {
            throw new Exception("not found");
        }


    }
}
