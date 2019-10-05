package com.example.solarcalculator.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SolarCalData implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private final String deviceName;
    private int voltage;
    private int amps;
    private int hoursUsedDaily;
    private int quantity;
    private long roomUserId;
    private String googleUserId;


    //Constructor
    public SolarCalData(String deviceName) {
        this.deviceName = deviceName;
    }

    //getters & setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getAmps() {
        return amps;
    }

    public void setAmps(int amps) {
        this.amps = amps;
    }

    public int getHoursUsedDaily() {
        return hoursUsedDaily;
    }

    public void setHoursUsedDaily(int hoursUsedDaily) {
        this.hoursUsedDaily = hoursUsedDaily;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getRoomUserId() {
        return roomUserId;
    }

    public void setRoomUserId(long roomUserId) {
        this.roomUserId = roomUserId;
    }

    public String getGoogleUserId() {
        return googleUserId;
    }

    public void setGoogleUserId(String googleUserId) {
        this.googleUserId = googleUserId;
    }

    public static Builder getBuilder(String deviceName){
        return new Builder(deviceName);
    }

    public int getWattage() { return getVoltage()*getAmps(); }


    //Builder Class & Methods
    public static class Builder{
        private String deviceName;
        private int voltage;
        private int amps;
        private int hoursUsedDaily;
        private int quantity;
        private Long roomUserId;
        private String googleUserId;

        private Builder(String deviceName) {
            this.deviceName = deviceName;
        }

        public Builder voltage(int voltage) {
            this.voltage = voltage;
            return this;
        }

        public Builder amps(int amps) {
            this.amps = amps;
            return this;
        }

        public Builder hoursUsedDaily(int hoursUsedDaily) {
            this.hoursUsedDaily = hoursUsedDaily;
            return this;
        }

        public Builder quantity(int userSunlightAccessInHours) {
            this.quantity = userSunlightAccessInHours;
            return this;
        }

        public Builder googleID(Long userId){
            this.roomUserId = userId;
            return this;
        }
        public Builder roomUserId(Long roomUserId) {
            this.roomUserId = roomUserId;
            return this;
        }

        public Builder googleUserId(String googleUserId) {
            this.googleUserId = googleUserId;
            return this;
        }

        public SolarCalData build(){
            SolarCalData solarCalData = new SolarCalData(deviceName);
            solarCalData.amps =amps;
            solarCalData.voltage=voltage;
            solarCalData.hoursUsedDaily=hoursUsedDaily;
            solarCalData.quantity = quantity;
            solarCalData.roomUserId = roomUserId;
            solarCalData.googleUserId=googleUserId;
            return solarCalData;
        }
    }


    //Parcelable Methods
    protected SolarCalData(Parcel in) {
        deviceName = in.readString();
        voltage = in.readInt();
        amps = in.readInt();
        hoursUsedDaily = in.readInt();
        quantity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deviceName);
        dest.writeInt(voltage);
        dest.writeInt(amps);
        dest.writeInt(hoursUsedDaily);
        dest.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SolarCalData> CREATOR = new Creator<SolarCalData>() {
        @Override
        public SolarCalData createFromParcel(Parcel in) {
            return new SolarCalData(in);
        }

        @Override
        public SolarCalData[] newArray(int size) {
            return new SolarCalData[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolarCalData)) return false;
        SolarCalData that = (SolarCalData) o;
        return getRoomUserId() == that.getRoomUserId() &&
                getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoomUserId());
    }
}
