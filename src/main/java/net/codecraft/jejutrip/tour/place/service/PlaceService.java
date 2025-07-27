package net.codecraft.jejutrip.tour.place.service;

import lombok.RequiredArgsConstructor;
import net.codecraft.jejutrip.tour.place.domain.Place;
import net.codecraft.jejutrip.tour.place.dto.PlaceResponse;
import net.codecraft.jejutrip.tour.place.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

    private final PlaceRepository placeRepository;

    // 모든 장소 조회
    public List<PlaceResponse> findAllPlaces() {
        return placeRepository.findAll().stream()
                .map(PlaceResponse::new)
                .collect(Collectors.toList());
    }

    // ID로 특정 장소 조회
    public PlaceResponse findPlaceById(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소를 찾을 수 없습니다. id=" + id));
        return new PlaceResponse(place);
    }

    // 제목으로 장소 검색
    public List<PlaceResponse> searchPlacesByTitle(String title) {

        return placeRepository.findAll().stream()
                .filter(place -> place.getTitle().contains(title))
                .map(PlaceResponse::new)
                .collect(Collectors.toList());
    }
}
