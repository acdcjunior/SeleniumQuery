package testinfrastructure.junitrule.config;

import testinfrastructure.testutils.EnvironmentTestUtils;

public class EndToEndTestConfig {

    public static DriverToRunTestsIn whatDriversShouldTestsRun() {
        if (EnvironmentTestUtils.isRunningAtTravis()) {
            return DriverToRunTestsIn.PHANTOMJS;
        }
        if (EnvironmentTestUtils.isRunningAtAppveyor()) {
            return DriverToRunTestsIn.FIREFOX;
        }
        if (EnvironmentTestUtils.isRunningAtShippable()) {
            return DriverToRunTestsIn.CHROME_HEADLESS;
        }
        if (EnvironmentTestUtils.isRunningAtCodeShip()) {
            // will also run DriverToRunTestsIn.REMOTE if last commit message contains [run sauce]
            return DriverToRunTestsIn.HTMLUNIT_CHROME_JS_ON_ONLY;
        }
        if (EnvironmentTestUtils.isRunningAtWercker()) {
            return DriverToRunTestsIn.HTMLUNIT_ALL_JS_ON_AND_OFF;
        }
        if (EnvironmentTestUtils.isRunningAtContinuousIntegrationServer()) {
            return DriverToRunTestsIn.HEADLESS_DRIVERS_JS_ON_AND_OFF;
        }
        return DriverToRunTestsIn.HTMLUNIT_CHROME_JS_ON_ONLY;
    }

}
