package com.space.controller;

import com.space.model.dto.ShipDTO;
import com.space.model.entity.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class HTMLController {

    final ShipService shipService;

    @Autowired
    public HTMLController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping(value="/ships")
    public ResponseEntity<List<ShipDTO>> showAllShips (@RequestParam Map<String, String> param) {
        List<ShipDTO> ships = shipService.findAllShips(param);
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @GetMapping(value = "/ships/count")
    public ResponseEntity<Integer> getCountShips(@RequestParam Map<String, String> param) {
        return new ResponseEntity<>(shipService.getCountShips(param), HttpStatus.OK);
    }

    @PostMapping(value = "/ships")
    public ResponseEntity<ShipDTO> createShip(@RequestBody Map<String, String> playload) {
        try {
            ShipDTO createdShip = shipService.createShip(playload);
            return new ResponseEntity<>(createdShip, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/ships/{idStr}")
    public ResponseEntity<ShipDTO> deleteShip(@PathVariable String idStr) {
        int id;
        try {
            id = Integer.parseInt(idStr);
            if (id > 0) {
                boolean isRemoved = shipService.deleteShip(id);
                if (!isRemoved) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                else {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/ships/{idStr}")
    public ResponseEntity<ShipDTO> getShip(@PathVariable String idStr) {
        int id;
        try {
            id = Integer.parseInt(idStr);
            if (id > 0) {
                ShipDTO shipDTO = shipService.getShip(id);
                if (shipDTO == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                else {
                    return new ResponseEntity<>(shipDTO, HttpStatus.OK);
                }
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/ships/{idStr}")
    public ResponseEntity<ShipDTO> updateShip(@PathVariable String idStr, @RequestBody Map<String, String> paramForUpdate) {
        int id;
        try {
            id = Integer.parseInt(idStr);
            if (id > 0) {
                try {
                    if (paramForUpdate.size() == 0) { return new ResponseEntity<>(shipService.getShip(id), HttpStatus.OK); }
                    ShipDTO newShip = shipService.updateShip(id, paramForUpdate);
                    return new ResponseEntity<>(newShip, HttpStatus.OK);
                }
               catch (IllegalArgumentException e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
