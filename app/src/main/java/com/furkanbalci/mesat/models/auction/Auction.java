package com.furkanbalci.mesat.models.auction;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

/**
 * Auction class.
 */
public final class Auction {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("starting_time")
    @Expose
    private long startingDate;

    @SerializedName("owner_id")
    @Expose
    private int owner_id;

    @SerializedName("starting_price")
    @Expose
    private int startingPrice;

    @SerializedName("sold")
    @Expose
    private boolean status;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("end_time")
    @Expose
    private long endDate;

    @SerializedName("showcase_photo_id")
    @Expose
    private String showcase_photo_id;


    /**
     * Gets auction id.
     *
     * @return Auction id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets user id.
     *
     * @return User id.
     */
    public int getOwner_id() {
        return this.owner_id;
    }

    /**
     * Gets auction title.
     *
     * @return Title.
     */
    @NonNull
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets auction title.
     *
     * @param title Title.
     */
    public void setTitle(@NonNull String title) {
        this.title = Objects.requireNonNull(title);
    }

    /**
     * Gets description of Auction.
     *
     * @return Auction.
     */
    @NonNull
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets description of Auction.
     *
     * @param description Description.
     */
    public void setDescription(@NonNull String description) {
        this.description = Objects.requireNonNull(description);
    }
    /**
     * Gets starting price. Default as 1.
     *
     * @return Starting price.
     */
    public int getStartingPrice() {
        return this.startingPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getShowcase_photo_id() {
        return showcase_photo_id;
    }
}
