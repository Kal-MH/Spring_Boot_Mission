package dev.kalmh.community.service;

import dev.kalmh.community.controller.dto.AreaDto;
import dev.kalmh.community.entity.AreaEntity;
import dev.kalmh.community.repository.AreaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AreaService {
    private static final Logger logger = LoggerFactory.getLogger(AreaService.class);
    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;

        //dummy data
        AreaEntity dummyArea1 = new AreaEntity();
        dummyArea1.setRegionMajor("서울시");
        dummyArea1.setRegionMinor("서초구");
        dummyArea1.setRegionPatch("서초동");
        dummyArea1.setLatitude(37.4877);
        dummyArea1.setLatitude(127.0174);
        this.areaRepository.save(dummyArea1);

        AreaEntity dummyArea2 = new AreaEntity();
        dummyArea2.setRegionMajor("서울시");
        dummyArea2.setRegionMinor("강남구");
        dummyArea2.setRegionPatch("역삼동");
        dummyArea2.setLatitude(37.4999);
        dummyArea2.setLatitude(127.0374);
        this.areaRepository.save(dummyArea2);

        AreaEntity dummyArea3 = new AreaEntity();
        dummyArea3.setRegionMajor("서울시");
        dummyArea3.setRegionMinor("강남구");
        dummyArea3.setRegionPatch("삼성동");
        dummyArea3.setLatitude(37.5140);
        dummyArea3.setLatitude(127.0565);
        this.areaRepository.save(dummyArea3);
    }

    public AreaDto createArea(AreaDto areaDto){
        AreaEntity areaEntity = new AreaEntity();
        areaEntity.setRegionMajor(areaDto.getRegionMajor());
        areaEntity.setRegionMinor(areaDto.getRegionMinor());
        areaEntity.setRegionPatch(areaDto.getRegionPatch());
        areaEntity.setLatitude(areaDto.getLatitude());
        areaEntity.setLongitude(areaDto.getLongitude());
        areaEntity = areaRepository.save(areaEntity);

        return new AreaDto(areaEntity);
    }

    public AreaDto readArea(Long id) {
        Optional<AreaEntity> areaEntityOptional = areaRepository.findById(id);
        if (areaEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new AreaDto(areaEntityOptional.get());
    }

    public List<AreaDto> readAreaAll(){
        List<AreaDto> areaDtoList = new ArrayList<>();
        areaRepository.findAll().forEach(areaEntity -> areaDtoList.add(new AreaDto(areaEntity)));
        return areaDtoList;
    }
}
