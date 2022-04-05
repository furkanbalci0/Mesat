package com.furkanbalci.mesat.network.auction;

import com.furkanbalci.mesat.models.auction.Auction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Auction retrofit service.
 */
public interface AuctionService {

    /**
     * Gets all auctions.
     *
     * @return Auctions list.
     */
    @GET("auctions/all")
    Call<List<Auction>> getAll();

    /**
     * Gets auction.
     *
     * @param id Auction Id.
     * @return Auction.
     */
    @GET("auctions/get")
    Call<Auction> get(@Query("id") int id);

    /**
     * Get auctions.
     *
     * @param key Search key.
     * @return Auctions.
     */
    @GET("auctions/find")
    Call<List<Auction>> findAuctionsByNameOrDescription(@Query("key") String key);

}
