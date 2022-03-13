package com.furkanbalci.mesat.models.auction;

import androidx.annotation.NonNull;

import com.furkanbalci.mesat.models.user.User;
import com.furkanbalci.mesat.models.auction.defaults.AuctionStatus;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Auction class.
 */
public final class Auction {

    private final UUID uid;
    private final Date startingDate;
    private final User owner;
    private final int startingPrice;

    private AuctionStatus status;
    private String title;
    private String description;
    private Date endDate;


    /**
     * Auction creator.
     *
     * @param owner      User.
     * @param auctionUID Auction Id.
     */
    public Auction(@NonNull User owner, @NonNull UUID auctionUID) {
        this.owner = Objects.requireNonNull(owner);
        this.uid = Objects.requireNonNull(auctionUID);
        this.startingDate = new Date(); //TODO: Veri database üzerinden çekilecektir.
        this.startingPrice = 1; //TODO: Veri database üzerinden çekilecektir.
    }

    /**
     * Auction creator.
     *
     * @param owner User.
     */
    public Auction(@NonNull User owner) {
        this.owner = Objects.requireNonNull(owner);
        this.uid = UUID.randomUUID();
        this.startingDate = new Date();
        this.startingPrice = 1;
    }

    /**
     * Gets UUID.
     *
     * @return UUID.
     */
    @NonNull
    public UUID getUID() {
        return this.uid;
    }

    /**
     * Gets starting date. The date is
     * determined when the auction is
     * created.
     *
     * @return Date.
     */
    @NonNull
    public Date getStartingDate() {
        return this.startingDate;
    }

    /**
     * Gets owner of Auction as user.
     *
     * @return User.
     */
    @NonNull
    public User getOwner() {
        return this.owner;
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
     * Gets end date. If the date is not entered by user,
     * sets automatically.
     *
     * @return End date.
     */
    @NonNull
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate Date.
     */
    public void setEndDate(@NonNull Date endDate) {
        this.endDate = Objects.requireNonNull(endDate);
    }

    /**
     * Gets starting price. Default as 1.
     *
     * @return Starting price.
     */
    public int getStartingPrice() {
        return this.startingPrice;
    }

    /**
     * Gets auction status.
     * @return Status.
     */
    @NonNull
    public AuctionStatus getStatus() {
        return this.status;
    }

    /**
     * Sets auction status.
     * @param status Status.
     */
    public void setStatus(@NonNull AuctionStatus status) {
        this.status = Objects.requireNonNull(status);
    }
}
