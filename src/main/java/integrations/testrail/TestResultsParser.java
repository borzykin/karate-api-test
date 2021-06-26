package integrations.testrail;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.nio.file.Files.isRegularFile;
import static java.nio.file.Files.newBufferedReader;

public class TestResultsParser {
    private static final Map<String, String> RESULTS = new HashMap<>();

    public static Map<String, String> getTestResultsMap() {
        final Path resultsFolder = Paths.get(System.getProperty("user.dir"), "target/karate-reports");
        final List<Path> pathList = new ArrayList<>();
        final Gson gson = new Gson();

        // Read folder for result files
        try (Stream<Path> paths = Files.walk(resultsFolder)) {
            paths.filter(x -> isRegularFile(x) && x.toString().endsWith(".karate-json.txt")).forEach(pathList::add);
        } catch (IOException e) {
            //todo logger
            e.printStackTrace();
        }

        // Map each json file to results POJO and collect to all results map
        for (Path path : pathList) {
            try {
                final String report = JsonParser.parseReader(newBufferedReader(path)).getAsJsonObject().toString();
                final Map<String, String> featureResult = gson.fromJson(report, FeatureResult.class).getResultsAsStringMap();
                featureResult.keySet().forEach(key -> RESULTS.put(key, featureResult.get(key)));
            } catch (IOException e) {
                //todo logger
                e.printStackTrace();
            }
        }
        return RESULTS;
    }
}
