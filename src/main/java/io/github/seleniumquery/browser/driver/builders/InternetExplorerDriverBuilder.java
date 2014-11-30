package io.github.seleniumquery.browser.driver.builders;

import io.github.seleniumquery.SeleniumQueryException;
import io.github.seleniumquery.browser.driver.DriverBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.SessionNotFoundException;

import java.io.File;

/**
 * Builds InternetExplorerDriver instances for SeleniumQueryDriver.
 *
 * @author acdcjunior
 *
 * @since 0.9.0
 */
public class InternetExplorerDriverBuilder extends DriverBuilder<InternetExplorerDriverBuilder> {

    private static final Log LOGGER = LogFactory.getLog(InternetExplorerDriverBuilder.class);

    private String customPathToIEDriverServerExe;

    /**
     * Configures the builder to look for the <code>IEDriverServer.exe</code> at the path specified by the argument.
     *
     * @param pathToIEDriverServerExe The path to the executable server file. Examples:
     *     <code>"C:\\myFiles\\IEDriverServer.exe"</code>; can be relative, as in <code>"..\\stuff\\IEDriverServer.exe"</code>;
     *     does not matter if the executable was renamed, such as <code>"drivers\\ie\\iedriverserver_v12345.exe"</code>.
     *
     * @return A self reference, allowing further configuration.
     *
     * @since 0.9.0
     */
    public InternetExplorerDriverBuilder withPathToIEDriverServerExe(String pathToIEDriverServerExe) {
        this.customPathToIEDriverServerExe = pathToIEDriverServerExe;
        return this;
    }

    @Override
    protected WebDriver build() {
        if (this.customPathToIEDriverServerExe != null) {
            return instantiateIeDriverWithPath(this.customPathToIEDriverServerExe);
        }
        return instantiateIeDriverWithoutPath();
    }

    private WebDriver instantiateIeDriverWithoutPath() {
        return instantiateIeDriverWithPath(DriverInstantiationUtils.getFullPathForFileInClasspath("IEDriverServer.exe"));
    }

    private WebDriver instantiateIeDriverWithPath(String pathToIEDriverServerExe) {
        try {
            WebDriver iEWebDriver = DriverInstantiationUtils.instantiateDriverWithPath(pathToIEDriverServerExe,
                    "IE Driver Server",
                    "http://selenium-release.storage.googleapis.com/index.html",
                    "$.driver().useInternetExplorer().withPath(\"other/path/to/IEDriverServer.exe\")",
                    "webdriver.ie.driver",
                    InternetExplorerDriver.class);

            guaranteeActiveXIsNotBlocked(iEWebDriver);

            return iEWebDriver;
        } catch (SessionNotFoundException snfe) {
            String message = snfe.getLocalizedMessage();
            if (message != null && message.contains("Protected Mode")) {
                throw new SeleniumQueryException("IE Driver requires Protected Mode settings to be the same for all zones. Go to\n\t\t" +
                        "'Tools' -> 'Internet Options' -> 'Security Tab', and set all zones to the same protected mode," +
                        " be it enabled or disabled, does not matter.\n\t\t" +
                        "If this does not solve the problem, or for more info, check our IE Driver wiki page at: " +
                        "https://github.com/seleniumQuery/seleniumQuery/wiki/seleniumQuery-and-IE-Driver", snfe);
            }
            throw snfe;
        }
    }

    private void guaranteeActiveXIsNotBlocked(WebDriver iEWebDriver) {
        try {
            iEWebDriver.get(new File(DriverInstantiationUtils.getFullPathForFileInClasspath("ie.html")).toURI().toString());
            iEWebDriver.findElements(By.xpath("/nobody"));
        } catch (InvalidSelectorException ise) {
            LOGGER.debug("Failed while testing if ActiveX is enabled in IE Driver.", ise);
            try {
                System.out.println("Your IE Driver is probably blocking ActiveX. Enable it.");
                iEWebDriver.get(new File(DriverInstantiationUtils.getFullPathForFileInClasspath("ie-activex.html")).toURI().toString());
                for (int i = 0; i < 45; i++) {
                    try {
                        iEWebDriver.findElements(By.xpath("/nobody"));
                        break;
                    } catch (Exception e) {
                        LOGGER.debug("Forcing ActiveX error again["+i+"].", e);
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.debug("Failed after giving the user some time to enable ActiveX.", e);
            }
        }
    }

}