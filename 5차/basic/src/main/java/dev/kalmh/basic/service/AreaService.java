package dev.kalmh.basic.service;

import dev.kalmh.basic.controller.dto.AreaDto;
import dev.kalmh.basic.entity.AreaEntity;
import dev.kalmh.basic.repository.AreaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AreaService {
    private static final Logger logger = LoggerFactory.getLogger(AreaService.class);
    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {this.areaRepository = areaRepository;}

    public AreaDto createArea(AreaDto areaDto) {
        //area save
        AreaEntity areaEntity = new AreaEntity();
        areaEntity.setRegionMajor(areaDto.getRegionMajor());
        areaEntity.setRegionMinor(areaDto.getRegionMinor());
        areaEntity.setRegionPatch(areaDto.getRegionPatch());
        areaEntity.setLatitude(areaDto.getLatitude());
        areaEntity.setLongitude(areaDto.getLongitude());

        areaEntity = this.areaRepository.save(areaEntity);
        return new AreaDto(areaEntity);
    }
    public AreaDto readArea(Long id) {
        Optional<AreaEntity> areaEntityOptional = this.areaRepository.findById(id);
        if (areaEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new AreaDto(areaEntityOptional.get());
    }
    public List<AreaDto> readAreaAll() {
        List<AreaDto> areaDtoList = new ArrayList<>();
        this.areaRepository.findAll().forEach(
                areaEntity -> areaDtoList.add(new AreaDto(areaEntity))
        );
        return areaDtoList;
    }

    public AreaDto getLocationInfo(Double latitude, Double longitude) {
        double minDistance = Long.MAX_VALUE;
        AreaEntity minAreaEntity = null;
        Iterable<AreaEntity> iterable = this.areaRepository.findAll();
        Iterator iterator = iterable.iterator();

        while (iterator.hasNext()) {
            AreaEntity areaEntity = (AreaEntity) iterator.next();
            double xDistance = Math.pow(Math.abs(latitude - areaEntity.getLatitude()), 2);
            double yDistance = Math.pow(Math.abs(longitude - areaEntity.getLongitude()), 2);
            if (minDistance > Math.sqrt(xDistance + yDistance)) {
                minDistance = Math.sqrt(xDistance + yDistance);
                minAreaEntity = areaEntity;
            }
        }
        logger.info("AreaService getLocationInfo : " + minAreaEntity);
        return new AreaDto(minAreaEntity);
    }
}
