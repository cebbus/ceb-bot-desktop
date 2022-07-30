package com.cebbus.util;

import com.cebbus.bot.api.binance.order.TradeStatus;
import com.cebbus.bot.desktop.util.PropertyReader;
import com.cebbus.bot.api.dto.CsIntervalAdapter;
import com.cebbus.bot.api.properties.Symbol;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PropertyReaderIT {

    @Test
    void getApiKey() {
        String actual = PropertyReader.getApiKey();
        assertEquals("kjPvnb6TMJvaR33nXoVUtudaRBHAJKHDssurQkgZtikkuAtNbwuleJLJe3RJkPCI", actual);
    }

    @Test
    void getApiSecret() {
        String actual = PropertyReader.getApiSecret();
        assertEquals("yuUBaD6IwszVn8KGqpCRyYKgmc2ImUKeqaqI9ipBf9MF3rO0k3kp9nx7pEazRruY", actual);
    }

    @Test
    void getCacheSize() {
        Integer actual = PropertyReader.getCacheSize();
        assertEquals(90, actual);
    }

    @Test
    void isCredentialsExist() {
        assertTrue(PropertyReader.isCredentialsExist());
    }

    @Test
    void isTestProfile() {
        assertTrue(PropertyReader.isTestProfile());
    }

    @Test
    void isDevelopmentProfile() {
        assertFalse(PropertyReader.isDevelopmentProfile());
    }

    @Test
    void isProductionProfile() {
        assertFalse(PropertyReader.isProductionProfile());
    }

    @Test
    void getSymbols() {
        List<Symbol> expected = new ArrayList<>();
        Integer limit = PropertyReader.getCacheSize();

        expected.add(Symbol.builder()
                .id(0)
                .weight(0.9)
                .base("BNB")
                .quote("USDT")
                .strategy("MacdStrategy")
                .interval(CsIntervalAdapter.ONE_MINUTE)
                .status(TradeStatus.ACTIVE)
                .cacheSize(limit)
                .build());

        expected.add(Symbol.builder()
                .id(1)
                .weight(0.1)
                .base("ETH")
                .quote("USDT")
                .strategy("MacdStrategy")
                .interval(CsIntervalAdapter.ONE_MINUTE)
                .status(TradeStatus.ACTIVE)
                .cacheSize(limit)
                .build());

        expected.add(Symbol.builder()
                .id(2)
                .weight(0.0)
                .base("XRP")
                .quote("USDT")
                .strategy("MacdStrategy")
                .interval(CsIntervalAdapter.ONE_MINUTE)
                .status(TradeStatus.INACTIVE)
                .cacheSize(limit)
                .build());

        List<Symbol> actual = PropertyReader.getSymbolList();
        assertIterableEquals(expected, actual);
    }
}
