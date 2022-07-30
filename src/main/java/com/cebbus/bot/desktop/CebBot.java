package com.cebbus.bot.desktop;

import com.cebbus.bot.api.Speculator;
import com.cebbus.bot.api.client.MarketClient;
import com.cebbus.bot.api.notification.MailNotifier;
import com.cebbus.bot.api.notification.Notifier;
import com.cebbus.bot.api.properties.Mail;
import com.cebbus.bot.api.properties.Radar;
import com.cebbus.bot.api.properties.Symbol;
import com.cebbus.bot.api.util.SpeculatorHolder;
import com.cebbus.bot.api.util.TaskScheduler;
import com.cebbus.bot.desktop.util.ClientFactory;
import com.cebbus.bot.desktop.util.PropertyReader;
import com.cebbus.bot.desktop.view.panel.CryptoAppFrame;
import com.cebbus.bot.desktop.view.panel.CryptoSplashFrame;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.List;

@Slf4j
public class CebBot {

    private static CryptoAppFrame appFrame;
    private static CryptoSplashFrame splashFrame;

    public static void main(String[] args) {
        Radar radar = PropertyReader.getRadar();
        List<Symbol> symbols = PropertyReader.getSymbolList();
        TaskScheduler scheduler = TaskScheduler.getInstance();
        SpeculatorHolder speHolder = SpeculatorHolder.getInstance();

        Mail mail = PropertyReader.getMail();
        Notifier notifier = new MailNotifier(null, mail);

        MarketClient marketClient = ClientFactory.getInstance().getClient();

        if (!GraphicsEnvironment.isHeadless()) {
            splashFrame = new CryptoSplashFrame(symbols.size());
            splashFrame.show();

            appFrame = new CryptoAppFrame();
        }

        for (Symbol symbol : symbols) {
            Speculator speculator = new Speculator(symbol, marketClient);

            if (!GraphicsEnvironment.isHeadless()) {
                appFrame.addTab(speculator);
                splashFrame.progress();
            }

            speHolder.addSpeculator(speculator);
            scheduler.scheduleSpeculator(speculator);
        }

        if (!GraphicsEnvironment.isHeadless()) {
            appFrame.addTestTab();
            appFrame.addWalkForwardTab();

            splashFrame.hide();
            appFrame.show();
        }

        scheduler.scheduleRadar(radar, marketClient, notifier);
    }

}
