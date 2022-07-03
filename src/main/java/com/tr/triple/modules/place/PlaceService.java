package com.tr.triple.modules.place;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public Long savePlace(Double latitude, Double longitude) throws Exception {
        Place place = placeRepository.save(Place.builder()
                                            .latitude(latitude)
                                            .longitude(longitude).build());
        if(place != null)
            return place.getPlaceId();
        else
            throw new Exception("");
    }
}
