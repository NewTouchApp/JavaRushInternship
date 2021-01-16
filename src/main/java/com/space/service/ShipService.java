package com.space.service;

import com.space.model.dto.ShipDTO;

import java.util.List;
import java.util.Map;

public interface ShipService {
    List<ShipDTO> findAllShips(Map<String, String> filter);

    int getCountShips (Map<String, String> filter);

    ShipDTO createShip(Map<String, String> paramForCreate) throws Exception;

    boolean deleteShip(int id);

    ShipDTO getShip(int id);

    ShipDTO updateShip(int id, Map<String, String> paramForCreate) throws Exception;
}
