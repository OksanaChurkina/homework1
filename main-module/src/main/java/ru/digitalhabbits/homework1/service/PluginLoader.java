package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = ".jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) throws IOException {
        List<Class<? extends PluginInterface>> plugins = new ArrayList<>();

        File pluginDirectory = new File(pluginDirName);
        File[] files = pluginDirectory.listFiles(((dir, name) -> name.endsWith(PLUGIN_EXT)));

        if(files != null && files.length > 0){
            ArrayList<String> classList = new ArrayList<>();
            ArrayList<URL> urls = new ArrayList<>(files.length);
            for (File file: files
            ) {

                    JarFile jarFile = new JarFile(file);
                    jarFile.stream().forEach(jarEntry -> {
                        if(jarEntry.getName().endsWith(".class")){
                            classList.add(jarEntry.getName());
                        }
                    });
                    URL url = file.toURI().toURL();
                    urls.add(url);


                URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
                classList.forEach(className -> {
                    try {
                        Class cls = urlClassLoader.loadClass(className.replaceAll("/", ".")
                                .replace(".class", ""));
                        Class[] interfaces = cls.getInterfaces();
                        for (Class i: interfaces
                        ) {
                            if(i.equals(PluginInterface.class)){
                                plugins.add(cls);
                                break;
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        return plugins;
    }
}
