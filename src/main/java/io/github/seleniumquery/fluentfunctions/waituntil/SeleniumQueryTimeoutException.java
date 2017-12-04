package io.github.seleniumquery.fluentfunctions.waituntil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import io.github.seleniumquery.SeleniumQueryConfig;
import io.github.seleniumquery.SeleniumQueryObject;

/**
 * @since 0.18.0
 */
@SuppressWarnings("deprecation")
public class SeleniumQueryTimeoutException extends io.github.seleniumquery.wait.SeleniumQueryTimeoutException {

    public SeleniumQueryTimeoutException(TimeoutException sourceException, SeleniumQueryObject seleniumQueryObject, String
        reason) {
        super("Timeout while waiting for " + seleniumQueryObject + " " + reason, sourceException);

        try {
            saveErrorPage(seleniumQueryObject.getWebDriver());
            saveErrorScreenshot(seleniumQueryObject.getWebDriver());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveErrorScreenshot(WebDriver webDriver) throws IOException {
        if (webDriver instanceof TakesScreenshot) {
            File srcFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(SeleniumQueryConfig.get("ERROR_PAGE_SCREENSHOT_LOCATION")));
        }
    }

    private void saveErrorPage(WebDriver webDriver) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(SeleniumQueryConfig.get("ERROR_PAGE_HTML_LOCATION"));
        out.println(webDriver.getPageSource());
        out.close();
    }

}
