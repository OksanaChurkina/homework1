package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {

        List<String> wordsList = new ArrayList<String>();
        String regex = "(\\b[a-zA-Z][-/a-zA-Z.0-9]*\\b)";
        String resultText = text.replaceAll("\\\\n", "\n").toLowerCase().trim();

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(resultText);

        while (matcher.find()){
            wordsList.add(matcher.group());
        }

        Map<String, Long> frequencyMap = wordsList.stream()
                .collect(Collectors.groupingBy((Function.identity()),
                        Collectors.counting()));
        Map<String, Long> treeMap = new TreeMap<>(frequencyMap);

        return treeMap.entrySet()
                .stream().
                        map(e-> e.getKey() + " " + e.getValue())
                .collect(Collectors.joining("\n"));
    }
}
