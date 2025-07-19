package com.lypaka.lypakautils.Handlers;

import com.lypaka.lypakautils.LypakaUtils;
import net.impactdev.impactor.api.economy.EconomyService;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.kyori.adventure.key.Key;
import net.minecraft.server.network.ServerPlayerEntity;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public class EconomyHandler {

    private static EconomyService ecoService;

    public static void init() {

        ecoService = EconomyService.instance();
        LypakaUtils.logger.info("Initialized economy service. Thanks Nick.");

    }

    public static EconomyService getService() {

        return ecoService;

    }

    public static void charge (ServerPlayerEntity player, double amount) throws ExecutionException, InterruptedException {

        ecoService.account(player.getUuid()).get().withdraw(BigDecimal.valueOf(amount));

    }

    public static void charge (ServerPlayerEntity player, double amount, @Nullable Currency currency) throws ExecutionException, InterruptedException {

        if (currency != null) {

            ecoService.account(currency, player.getUuid()).get().withdraw(BigDecimal.valueOf(amount));

        } else {

            charge(player, amount);

        }

    }

    public static void charge (ServerPlayerEntity player, double amount, String currencyName) throws ExecutionException, InterruptedException {

        Currency currency = ecoService.currencies().currency(Key.key(currencyName)).orElse(null);
        if (currency != null) {

            charge(player, amount, currency);

        } else {

            charge(player, amount); // I assume this will just do the default currency

        }

    }

    public static double getBalance (ServerPlayerEntity player) throws ExecutionException, InterruptedException {

        return ecoService.account(player.getUuid()).get().balance().doubleValue();

    }

    public static double getBalance (ServerPlayerEntity player, Currency currency) throws ExecutionException, InterruptedException {

        return ecoService.account(currency, player.getUuid()).get().balance().doubleValue();

    }

    public static double getBalance (ServerPlayerEntity player, String currencyName) throws ExecutionException, InterruptedException {

        Currency currency = ecoService.currencies().currency(Key.key(currencyName)).orElse(null);
        if (currency != null) {

            return getBalance(player, currency);

        } else {

            return getBalance(player); // I assume this will just do the default currency

        }

    }

    public static boolean canAfford (ServerPlayerEntity player, double amount) throws ExecutionException, InterruptedException {

        double balance = getBalance(player);
        return balance >= amount;

    }

    public static boolean canAfford (ServerPlayerEntity player, double amount, Currency currency) throws ExecutionException, InterruptedException {

        double balance = getBalance(player, currency);
        return balance >= amount;

    }

    public static boolean canAfford (ServerPlayerEntity player, double amount, String currencyName) throws ExecutionException, InterruptedException {

        Currency currency = ecoService.currencies().currency(Key.key(currencyName)).orElse(null);
        if (currency != null) {

            return canAfford(player, amount, currency);

        } else {

            return canAfford(player, amount);

        }

    }

    public static void payToPlayer (ServerPlayerEntity player, double amount) throws ExecutionException, InterruptedException {

        ecoService.account(player.getUuid()).get().deposit(BigDecimal.valueOf(amount));

    }

    public static void payToPlayer (ServerPlayerEntity player, double amount, Currency currency) throws ExecutionException, InterruptedException {

        ecoService.account(currency, player.getUuid()).get().deposit(BigDecimal.valueOf(amount));

    }

    public static void payToPlayer (ServerPlayerEntity player, double amount, String currencyName) throws ExecutionException, InterruptedException {

        Currency currency = ecoService.currencies().currency(Key.key(currencyName)).orElse(null);
        if (currency != null) {

            payToPlayer(player, amount, currency);

        } else {

            payToPlayer(player, amount);

        }

    }

}
