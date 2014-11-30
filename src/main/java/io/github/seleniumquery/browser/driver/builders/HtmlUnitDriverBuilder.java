package io.github.seleniumquery.browser.driver.builders;

import io.github.seleniumquery.browser.driver.DriverBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.openqa.selenium.remote.CapabilityType.*;

/**
 * Builds HtmlUnitDriver instances for SeleniumQueryDriver.
 *
 * @author acdcjunior
 *
 * @since 0.9.0
 */
public class HtmlUnitDriverBuilder extends DriverBuilder<HtmlUnitDriverBuilder> {

    private static final String DEFAULT_EMULATED_BROWSER_NAME = BrowserType.CHROME;
    private static final String DEFAULT_EMULATED_BROWSER_VERSION = null; // HtmlUnit does not use version when browser is chrome

    private String emulatedBrowserName = DEFAULT_EMULATED_BROWSER_NAME;
    private String emulatedBrowserVersion = DEFAULT_EMULATED_BROWSER_VERSION;

    private Boolean javaScriptEnabled;

    /**
     * Configures HtmlUnit to have JavaScript disabled.
     *
     * @return A self reference.
     */
    public HtmlUnitDriverBuilder withoutJavaScript() {
        this.javaScriptEnabled = false;
        return this;
    }

    /**
     * Configures HtmlUnit to have JavaScript enabled.
     *
     * @return A self reference.
     */
    public HtmlUnitDriverBuilder withJavaScript() {
        this.javaScriptEnabled = true;
        return this;
    }

    /**
     * Configures HtmlUnit to emulate latest available Firefox version.
     *
     * @return A self reference.
     */
    public HtmlUnitDriverBuilder emulatingFirefox() {
        this.emulatedBrowserName = BrowserType.FIREFOX;
        return this;
    }

    /**
     * Configures HtmlUnit to emulate Chrome.
     *
     * @return A self reference.
     */
    public HtmlUnitDriverBuilder emulatingChrome() {
        this.emulatedBrowserName = BrowserType.CHROME;
        return this;
    }

    /**
     * Configures HtmlUnit to emulate latest available Internet Explorer version.
     *
     * @return A self reference.
     */
    public HtmlUnitDriverBuilder emulatingInternetExplorer() {
        return emulatingInternetExplorer11();
    }

    /**
     * Configures HtmlUnit to emulate Internet Explorer 11.
     *
     * @return A self reference.
     */
    public HtmlUnitDriverBuilder emulatingInternetExplorer11() {
        this.emulatedBrowserName = BrowserType.IE;
        this.emulatedBrowserVersion = "11";
        return this;
    }

    /**
     * Configures HtmlUnit to emulate Internet Explorer 9.
     *
     * @return A self reference.
     * @deprecated as of HtmlUnit 2.14
     */
    @SuppressWarnings("deprecation")
    public HtmlUnitDriverBuilder emulatingInternetExplorer9() {
        this.emulatedBrowserName = BrowserType.IE;
        this.emulatedBrowserVersion = "9";
        return this;
    }

    /**
     * Configures HtmlUnit to emulate Internet Explorer 8.
     *
     * @return A self reference.
     * @deprecated as of HtmlUnit 2.14
     */
    @SuppressWarnings("deprecation")
    public HtmlUnitDriverBuilder emulatingInternetExplorer8() {
        this.emulatedBrowserName = BrowserType.IE;
        this.emulatedBrowserVersion = "8";
        return this;
    }

    @Override
    protected WebDriver build() {
        DesiredCapabilities capabilities = capabilities(DesiredCapabilities.htmlUnitWithJs());
        overwriteCapabilityIfValueNotNull(capabilities, BROWSER_NAME, this.emulatedBrowserName);
        overwriteCapabilityIfValueNotNull(capabilities, VERSION, this.emulatedBrowserVersion);
        overwriteCapabilityIfValueNotNull(capabilities, SUPPORTS_JAVASCRIPT, this.javaScriptEnabled);
        return new HtmlUnitDriver(capabilities);
    }

}