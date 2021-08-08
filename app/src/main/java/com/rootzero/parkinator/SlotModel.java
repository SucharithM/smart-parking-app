package com.rootzero.parkinator;

public class SlotModel {
    String slotLocation,slotType,slotImage;
    Long slotPrice;

    public SlotModel() {
    }

    public SlotModel(String slotImage,String slotLocation, String slotType, Long slotPrice) {
        this.slotLocation = slotLocation;
        this.slotPrice = slotPrice;
        this.slotType = slotType;
        this.slotImage = slotImage;
    }

    public String getSlotLocation() {
        return slotLocation;
    }

    public void setSlotLocation(String slotLocation) {
        this.slotLocation = slotLocation;
    }

    public Long getSlotPrice() {
        return slotPrice;
    }

    public void setSlotPrice(Long slotPrice) {
        this.slotPrice = slotPrice;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public String getSlotImage() {
        return slotImage;
    }

    public void setSlotImage(String slotImage) {
        this.slotImage = slotImage;
    }
}
