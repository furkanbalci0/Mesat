package com.furkanbalci.mesat.models.auction;

import java.util.Date;

/**
 * Auction class.
 */
public final class Auction {

    private String id;
    private Date starting_time;
    private long owner_id;
    private long starting_price;
    private boolean sold;
    private String title;
    private String category;
    private Date end_time;
    private String showcase_photo;
    private String content;

    public Auction(String id, Date starting_time, long owner_id, long starting_price, boolean sold, String title, String category, Date end_time, String showcase_photo, String content) {
        this.id = id;
        this.starting_time = starting_time;
        this.owner_id = owner_id;
        this.starting_price = starting_price;
        this.sold = sold;
        this.title = title;
        this.category = category;
        this.end_time = end_time;
        this.showcase_photo = showcase_photo;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStarting_time() {
        return starting_time;
    }

    public void setStarting_time(Date starting_time) {
        this.starting_time = starting_time;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(long owner_id) {
        this.owner_id = owner_id;
    }

    public long getStarting_price() {
        return starting_price;
    }

    public void setStarting_price(long starting_price) {
        this.starting_price = starting_price;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getShowcase_photo() {
        return showcase_photo;
    }

    public void setShowcase_photo(String showcase_photo) {
        this.showcase_photo = showcase_photo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
