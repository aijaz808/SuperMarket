package com.amnijaz.supermarket.offer;

public class OfferModel {

    private Long offerCount;
    private Integer index;

    private Long startingPoint;

    public OfferModel() {
    }

    public OfferModel(Long offerCount, Integer index, Long startingPoint) {
        this.offerCount = offerCount;
        this.index = index;
        this.startingPoint = startingPoint;
    }

    public Long getOfferCount() {
        return offerCount;
    }

    public void setOfferCount(Long offerCount) {
        this.offerCount = offerCount;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Long startingPoint) {
        this.startingPoint = startingPoint;
    }
}
