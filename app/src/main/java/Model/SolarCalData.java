package Model;

import android.os.Parcel;
import android.os.Parcelable;

public class SolarCalData implements Parcelable {
    private final String deviceName;
    private int voltage;
    private int amps;
    private int hoursUsedDaily;
    private int quantity;

    //Constructor
    private SolarCalData(String deviceName) {
        this.deviceName = deviceName;
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


    //getters & setters
    public String getDeviceName() {
        return deviceName;
    }

    public int getVoltage() {
        return voltage;
    }

    public int getAmps() {
        return amps;
    }

    public int getHoursUsedDaily() {
        return hoursUsedDaily;
    }

    public int getQuantity() {
        return quantity;
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

        public SolarCalData build(){
            SolarCalData solarCalData = new SolarCalData(deviceName);
            solarCalData.amps =amps;
            solarCalData.voltage=voltage;
            solarCalData.hoursUsedDaily=hoursUsedDaily;
            solarCalData.quantity = quantity;
            return solarCalData;
        }
    }
}
