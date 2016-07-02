/*
 * Copyright (c) 2016 seleniumQuery authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package testinfrastructure.testutils;

import io.github.seleniumquery.utils.DriverVersionUtils;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * No test code may use {@link DriverVersionUtils} directly to test the browser under test, because it may
 * have had its behavior overridden. Test code should use this class, that ensures {@link DriverVersionUtils}'s
 * behavior is reset before using it.
 */
public class DriverInTest {

    public static void restoreDriverVersionUtilsInstance() {
        // restore an eventually overridden (during other tests) instance
        DriverVersionUtils.overrideSingletonInstance(new DriverVersionUtils());
    }

    public static boolean isHtmlUnitDriver(WebDriver driver) {
        restoreDriverVersionUtilsInstance();
        return DriverVersionUtils.getInstance().isHtmlUnitDriver(driver);
    }

    public static boolean isNotHtmlUnitDriver(WebDriver driver) {
        return !isHtmlUnitDriver(driver);
    }

    public static boolean isIEDriver(WebDriver driver) {
        return driver instanceof InternetExplorerDriver || isDriverByName(driver, BrowserType.IE);
    }

    private static boolean isDriverByName(WebDriver driver, String browserName) {
        return driver instanceof HasCapabilities && ((HasCapabilities) driver).getCapabilities().getBrowserName().equals(browserName);
    }

    public static boolean isHtmlUnitDriverEmulatingIE(WebDriver webDriver) {
        restoreDriverVersionUtilsInstance();
        return DriverVersionUtils.isHtmlUnitDriverEmulatingIE(webDriver);
    }

    public static boolean isChromeDriver(WebDriver webDriver) {
        return webDriver instanceof ChromeDriver || isDriverByName(webDriver, BrowserType.CHROME);
    }

    public static boolean isEdgeDriver(WebDriver webDriver) {
        return webDriver instanceof EdgeDriver || isDriverByName(webDriver, BrowserType.EDGE);
    }

    public static boolean isFirefoxDriver(WebDriver webDriver) {
        return webDriver instanceof FirefoxDriver || isDriverByName(webDriver, BrowserType.FIREFOX);
    }

    public static boolean isPhantomJSDriver(WebDriver webDriver) {
        return webDriver instanceof PhantomJSDriver || isDriverByName(webDriver, BrowserType.PHANTOMJS);
    }

    public static boolean isNotFirefoxDriver(WebDriver webDriver) {
        return !isFirefoxDriver(webDriver);
    }

    public static boolean isRemoteDriver(WebDriver webDriver) {
        return webDriver.getClass().equals(RemoteWebDriver.class);
    }

    public static boolean isRemoteEdge(WebDriver driver) {
        return isDriverByName(driver, BrowserType.EDGE) && isRemoteDriver(driver);
    }

}
