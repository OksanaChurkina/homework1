package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {

        int lineCount = text.split("\n").length; // count of lines
        String[] wordsCount = text.replaceAll("[\\W \\n]", " ").split("\\s+"); //count of words
        long lettersCount = text.toCharArray().length; // count of letters

        return lineCount + ";" + wordsCount + ";" + lettersCount;
    }
}
