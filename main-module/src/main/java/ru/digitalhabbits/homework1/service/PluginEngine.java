package ru.digitalhabbits.homework1.service;

import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;

public class PluginEngine {

    @Nonnull
    public  <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text){

        String result = "";
        PluginInterface pluginInterface = null;
        try {
            pluginInterface = cls.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        result = pluginInterface.apply(text);
        return result;
    }
}
