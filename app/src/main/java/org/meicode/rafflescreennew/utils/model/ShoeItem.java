package org.meicode.rafflescreennew.utils.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.util.Calendar;
import java.util.Date;

// parcelable makes it easier to pass instances of a class quicker between the various components in the app
public class ShoeItem implements Parcelable {

    private String shoeName, shoeBrandName;
    private int shoeImage;
    private double shoePrice;
    private Parcel in;
    private Date releaseDate;
    private Date raffleEndDate;
    private String releaseTime;
    private String raffleEndTime;
    private int id;
    private int quantity;
    private double totalItemPrice;

    // Constructor for shoes with release date and raffle end date
    public ShoeItem(String shoeName, String shoeBrandName, int shoeImage, double shoePrice, Date releaseDate, Date raffleEndDate, String releaseTime, String raffleEndTime) {
        // represents the different properties for the item shoe
        this.shoeName = shoeName;
        this.shoeBrandName = shoeBrandName;
        this.shoeImage = shoeImage;
        this.shoePrice = shoePrice;
        this.releaseDate = releaseDate != null ? setSpecificTime(releaseDate, releaseTime) : null;
        this.raffleEndDate = raffleEndDate != null ? setSpecificTime(raffleEndDate, raffleEndTime) : null;
        this.releaseTime = releaseTime;
        this.raffleEndTime = raffleEndTime;
    }


    // Constructor for shoes without release date and raffle end date
    public ShoeItem(String shoeName, String shoeBrandName, int shoeImage, double shoePrice) {
        this.shoeName = shoeName;
        this.shoeBrandName = shoeBrandName;
        this.shoeImage = shoeImage;
        this.shoePrice = shoePrice;
        this.releaseDate = null; // Set releaseDate to null for shoes without a release date
        this.raffleEndDate = null; // Set raffleEndDate to null for shoes without a release date
        this.releaseTime = "";
        this.raffleEndTime = "";
    }

    // creates a ShoeItem object from a parcel
    protected ShoeItem(Parcel in) {
        shoeName = in.readString();
        shoeBrandName = in.readString();
        shoeImage = in.readInt();
        shoePrice = in.readDouble();
        long releaseDateMillis = in.readLong();
        long raffleEndDateMillis = in.readLong();
        releaseDate = releaseDateMillis != -1 ? new Date(releaseDateMillis) : null;
        raffleEndDate = raffleEndDateMillis != -1 ? new Date(raffleEndDateMillis) : null;
        releaseTime = in.readString();
        raffleEndTime = in.readString();
    }


    public static final Creator<ShoeItem> CREATOR = new Creator<ShoeItem>() {
        @Override
        public ShoeItem createFromParcel(Parcel in) {
            return new ShoeItem(in);
        }

        @Override
        public ShoeItem[] newArray(int size) {
            return new ShoeItem[size];
        }
    };

    // Getter and Setter methods for accessing and modifying the private fields
    public String getShoeName() {
        return shoeName;
    }

    public void setShoeName(String shoeName) {
        this.shoeName = shoeName;
    }

    public String getShoeBrandName() {
        return shoeBrandName;
    }

    public void setShoeBrandName(String shoeBrandName) {
        this.shoeBrandName = shoeBrandName;
    }

    public int getShoeImage() {
        return shoeImage;
    }

    public void setShoeImage(int shoeImage) {
        this.shoeImage = shoeImage;
    }

    public double getShoePrice() {
        return shoePrice;
    }

    public void setShoePrice(double shoePrice) {
        this.shoePrice = shoePrice;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }



    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRaffleEndDate(Date raffleEndDate) {
        this.raffleEndDate = raffleEndDate;
    }

    public Date getRaffleEndDate() {
        return raffleEndDate;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getRaffleEndTime() {
        return raffleEndTime;
    }

    public void setRaffleEndTime(String raffleEndTime) {
        this.raffleEndTime = raffleEndTime;
    }

    // New method to set specific time for a given date
    private Date setSpecificTime(Date date, String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String[] timeComponents = time.split(":");
        int hours = Integer.parseInt(timeComponents[0]);
        int minutes = Integer.parseInt(timeComponents[1]);

        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);

        return calendar.getTime();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(shoeName);
        dest.writeString(shoeBrandName);
        dest.writeInt(shoeImage);
        dest.writeDouble(shoePrice);
        dest.writeLong(releaseDate != null ? releaseDate.getTime() : -1); // Write the release date as a long
        dest.writeLong(raffleEndDate != null ? raffleEndDate.getTime() : -1); // Write the raffle end date as a long
        dest.writeString(releaseTime != null ? releaseTime : "");
        dest.writeString(raffleEndTime != null ? raffleEndTime : "");
    }

}