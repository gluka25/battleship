package cucumber.tests

import io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("battleship/mines")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "cucumber/steps")
class RunMineCucumberTests

