package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {

        int linesCount = text.split("\n").length;
        int lettersCount = text.length();
        int wordsCount = 0;
        for(String s: text.split("\\W")){
            if (s.length()>0){
                wordsCount++;
            }
        }
        return linesCount + ";" + wordsCount + ";" + lettersCount + ";";
    }
}
