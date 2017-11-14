package org.eugene.mod.logger;

import java.util.Currency;
import java.util.Set;

public class PlatformLoggerTest {
    public static void main(String[] args) {
        Set<Currency> c = Currency.getAvailableCurrencies();
        System.out.println("币种数量：" + c.size());
        System.Logger logger = System.getLogger("Log4jLogger");
        logger.log(System.Logger.Level.TRACE, "进入程序。");
        logger.log(System.Logger.Level.ERROR, "发生未知错误。");
        logger.log(System.Logger.Level.INFO, "FYI");
        logger.log(System.Logger.Level.TRACE, "退出程序。");
    }
}
