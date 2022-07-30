package com.cebbus;

import com.cebbus.bot.api.Speculator;
import com.cebbus.bot.api.analysis.TheOracle;
import com.cebbus.bot.api.binance.order.TradeStatus;
import com.cebbus.bot.api.client.MarketClient;
import com.cebbus.bot.api.properties.Symbol;
import com.cebbus.bot.api.util.SpeculatorHolder;
import com.cebbus.bot.api.util.TaskScheduler;
import com.cebbus.bot.desktop.util.ClientFactory;
import com.cebbus.bot.desktop.util.PropertyReader;
import com.cebbus.bot.desktop.view.panel.CryptoAppFrame;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CebBotIT {

    private Speculator speculator;

    @BeforeEach
    void setUp() {
        CryptoAppFrame appFrame = new CryptoAppFrame();

        Symbol symbol = PropertyReader.getSymbolList().get(0);
        SpeculatorHolder specHolder = SpeculatorHolder.getInstance();
        MarketClient client = ClientFactory.getInstance().getClient();

        this.speculator = new Speculator(symbol, client);

        appFrame.addTab(this.speculator);
        specHolder.addSpeculator(this.speculator);
    }

    @Test
    @Order(1)
    void buyAndSell() {
        assertTrue(this.speculator.buy());
        assertTrue(this.speculator.sell());
    }

    @Test
    void buyAndSellInactiveMode() {
        this.speculator.setStatus(TradeStatus.INACTIVE);
        this.speculator.changeParameters(1000, 1000);

        assertTrue(this.speculator.buy());
        assertTrue(this.speculator.sell());
    }

    @Test
    void changeParameters() {
        Number[] expected = new Number[]{1, 2};
        this.speculator.changeParameters(expected);

        assertEquals(expected, this.speculator.getTheOracle().getProphesyParameters());
    }

    @Test
    void changeStrategy() {
        TheOracle oldOracle = this.speculator.getTheOracle();

        this.speculator.changeStrategy("AdxStrategy");
        TheOracle newOracle = this.speculator.getTheOracle();

        assertNotEquals(oldOracle, newOracle);
    }

    @Test
    void calcStrategies() {
        List<Pair<String, String>> calcResultList = this.speculator.calcStrategies();
        assertFalse(calcResultList.isEmpty());
    }

    @Test
    @Order(2)
    void startSpec() {
        TaskScheduler scheduler = TaskScheduler.getInstance();

        assertDoesNotThrow(() -> {
            Date fireDate = scheduler.scheduleSpeculator(this.speculator);
            Date now = new Date();

            while (now.before(fireDate)) {
                Thread.sleep(10000);
                now = new Date();
            }
        });
    }
}
