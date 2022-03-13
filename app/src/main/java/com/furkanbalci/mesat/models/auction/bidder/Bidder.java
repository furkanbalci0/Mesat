package com.furkanbalci.mesat.models.auction.bidder;

import androidx.annotation.NonNull;

import com.furkanbalci.mesat.models.user.User;
import com.furkanbalci.mesat.models.auction.Auction;

import java.util.Objects;

public final class Bidder {

    private final User user;
    private final Auction auction;
    private long bidAmount;

    /**
     * Bidder creator.
     *
     * @param user    User.
     * @param auction Auction.
     */
    public Bidder(@NonNull User user, @NonNull Auction auction) {
        this.user = Objects.requireNonNull(user);
        this.auction = Objects.requireNonNull(auction);
    }

    /**
     * Gets user.
     *
     * @return User.
     */
    @NonNull
    public User getUser() {
        return this.user;
    }

    /**
     * Gets auction.
     *
     * @return Auction
     */
    @NonNull
    public Auction getAuction() {
        return this.auction;
    }

    /**
     * Gets bid amount.
     *
     * @return Bid amount.
     */
    public long getBidAmount() {
        return this.bidAmount;
    }

    /**
     * Sets bid amount;
     *
     * @param bidAmount Bid amount.
     */
    public void setBidAmount(long bidAmount) {
        this.bidAmount = bidAmount;
        //todo: database yazdır.
    }
}
