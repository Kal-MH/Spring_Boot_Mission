package dev.kalmh.basic.controller;

import dev.kalmh.basic.controller.dto.AreaDto;
import dev.kalmh.basic.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("area")
public class AreaController {
    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);
    private final AreaService areaService;

    public AreaController(AreaService areaService) {this.areaService = areaService;}

    //create
    @PostMapping
    public ResponseEntity<AreaDto> createArea(@RequestBody AreaDto dto) {
        return ResponseEntity.ok(this.areaService.createArea(dto));
    }

    //read
    @GetMapping("{id}")
    public ResponseEntity<AreaDto> readArea(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.areaService.readArea(id));
    }

    //readall
    @GetMapping
    public ResponseEntity<Collection<AreaDto>> readAreaAll() {
        return ResponseEntity.ok(this.areaService.readAreaAll());
    }
}
