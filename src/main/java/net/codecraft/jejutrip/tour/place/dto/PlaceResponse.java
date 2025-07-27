package net.codecraft.jejutrip.tour.place.dto;

import lombok.Getter;
import net.codecraft.jejutrip.tour.place.domain.Place;
import net.codecraft.jejutrip.tour.place.domain.PlaceType;

@Getter
public class PlaceResponse {
    private final Long id;
    private final String title;
    private final PlaceType placeType;
    private final String introduction;
    private final String address;
    private final String phoneNumber;
    private final double latitude;
    private final double longitude;
    private final String allTag;
    private final String imgPath;
    private final String thumbnailPath;

    public PlaceResponse(Place place) {
        this.id = place.getId();
        this.title = place.getTitle();
        this.placeType = place.getPlaceType();
        this.introduction = place.getIntroduction();
        this.address = place.getAddress();
        this.phoneNumber = place.getPhoneNumber();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
        this.allTag = place.getAllTag();
        this.imgPath = place.getImgPath();
        this.thumbnailPath = place.getThumbnailPath();
    }
}