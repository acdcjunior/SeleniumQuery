package infrastructure.junitrule;

import infrastructure.IntegrationTestUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class SetUpAndTearDownDriver implements TestRule {

	private static final DriverToRunTestsIn driverToRunTestsIn = DriverToRunTestsIn.PHANTOMJS;

	private final Class<?> htmlTestUrlClass;

	public SetUpAndTearDownDriver(Class<?> htmlTestUrlClass) {
		this.htmlTestUrlClass = htmlTestUrlClass;
	}

	@Override
	public Statement apply(final Statement base, Description description) {
		String url = IntegrationTestUtils.classNameToTestFileUrl(htmlTestUrlClass);
		return new RunTestMethodsInChosenDrivers(driverToRunTestsIn, base, url);
	}
	
}