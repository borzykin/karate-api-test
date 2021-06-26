package integrations.testrail;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FeatureResult {
    private List<ScenarioResult> scenarioResults;

    private class Result {
        public String status;
    }

    private class StepResult {
        public Result result;
    }

    private class ScenarioResult {
        public List<StepResult> stepResults;
        public String name;
    }

    public Map<String, String> getResultsAsStringMap() {
        Map<String, String> resultsMap = new HashMap<>();
        scenarioResults.forEach(scenario -> {
            Set<String> scenarioStepResults = scenario.stepResults.stream()
                    .map(stepResult -> stepResult.result.status).collect(Collectors.toSet());
            boolean isAllPassed = scenarioStepResults.size() == 1 && scenarioStepResults.contains("passed");
            Pattern pattern = Pattern.compile("TC\\d+.");
            Matcher matcher = pattern.matcher(scenario.name);

            while (matcher.find()) {
                resultsMap.put(StringUtils.substringBetween(matcher.group(), "TC", "."), isAllPassed ? "passed" : "retest");
            }
        });
        return resultsMap;
    }
}
