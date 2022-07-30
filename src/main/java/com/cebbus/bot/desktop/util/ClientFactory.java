package com.cebbus.bot.desktop.util;

import com.cebbus.bot.api.client.BinanceClient;
import com.cebbus.bot.api.client.MarketClient;

public class ClientFactory {

    private static final ClientFactory INSTANCE = new ClientFactory();
    private MarketClient client;

    private ClientFactory() {
    }

    public MarketClient getClient() {
        if (this.client == null) {
            String key = PropertyReader.getApiKey();
            String secret = PropertyReader.getApiSecret();
            boolean productionProfile = PropertyReader.isProductionProfile();
            this.client = new BinanceClient(key, secret, !productionProfile);
        }

        return this.client;
    }

    public static ClientFactory getInstance() {
        return INSTANCE;
    }

}
