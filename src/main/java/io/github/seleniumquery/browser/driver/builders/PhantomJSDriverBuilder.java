package io.github.seleniumquery.browser.driver.builders;

import io.github.seleniumquery.SeleniumQueryException;
import io.github.seleniumquery.browser.driver.DriverBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static io.github.seleniumquery.browser.driver.builders.DriverInstantiationUtils.*;
import static java.lang.String.format;

/**
 * Builds PhantomJSDriver instances for SeleniumQueryDriver.
 *
 * @author acdcjunior
 *
 * @since 0.9.0
 */
public class PhantomJSDriverBuilder extends DriverBuilder<PhantomJSDriverBuilder> {

    private static final String PHANTOMJS_EXECUTABLE_SYSTEM_PROPERTY = "phantomjs.binary.path";

    private static final String EXCEPTION_MESSAGE = " \nDownload the latest release at http://phantomjs.org/download.html and place it: \n" +
            "(1) on the classpath of this project; or\n" +
            "(2) on the path specified by the \"" + PHANTOMJS_EXECUTABLE_SYSTEM_PROPERTY + "\" system property; or\n" +
            "(3) on a folder in the system's PATH variable; or\n" +
            "(4) on the path set in the \""+PHANTOMJS_EXECUTABLE_SYSTEM_PROPERTY+"\" capability; or\n" +
            "(5) wherever and set the path via $.driver.usePhantomJS().withPathToPhantomJsExe(\"other/path/to/phantomjs.exe\").\n" +
            "For more information, see https://github.com/seleniumQuery/seleniumQuery/wiki/seleniumQuery-and-PhantomJS-Driver";

    private static final String BAD_PATH_PROVIDED_EXCEPTION_MESSAGE = "The PhantomJS executable file was not found (or is a directory) at \"%s\"." + EXCEPTION_MESSAGE;

    static String PHANTOMJS_EXE = "phantomjs.exe"; // package visibility so it can be changed during test

    private String customPathToPhantomJs;

    /**
     * Sets the path used by the PhantomJSDriver to find the phantomjs executable.
     * @param pathToPhantomJs Path to phantomjs.exe.
     * @return A self reference.
     */
    public PhantomJSDriverBuilder withPathToPhantomJsExe(String pathToPhantomJs) {
        this.customPathToPhantomJs = pathToPhantomJs;
        return this;
    }

    @Override
    protected WebDriver build() {
        DesiredCapabilities capabilities = capabilities(new DesiredCapabilities());

        if (customPathWasProvidedAndExecutableExistsThere(this.customPathToPhantomJs, BAD_PATH_PROVIDED_EXCEPTION_MESSAGE)) {
            System.setProperty(PHANTOMJS_EXECUTABLE_SYSTEM_PROPERTY, getFullPath(this.customPathToPhantomJs));
        } else if (executableExistsInClasspath(PHANTOMJS_EXE)) {
            System.setProperty(PHANTOMJS_EXECUTABLE_SYSTEM_PROPERTY, getFullPathForFileInClasspath(PHANTOMJS_EXE));
        }
        try {
            return new PhantomJSDriver(capabilities);
        } catch (IllegalStateException e) {
            throwCustomExceptionIfExecutableWasNotFound(e);
            throw e;
        }
    }

    private void throwCustomExceptionIfExecutableWasNotFound(IllegalStateException e) {
        if (e.getMessage().contains("path to the driver executable must be set")) {
            throw new SeleniumQueryException(
                    format(
                            "The PhantomJS executable (%s) was not found in the classpath, in the \"%s\" system property or in the system's PATH variable. %s",
                            PHANTOMJS_EXE, PHANTOMJS_EXECUTABLE_SYSTEM_PROPERTY, EXCEPTION_MESSAGE
                    ), e);
        }
    }

}