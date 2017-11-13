package utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.StringJoiner;
import models.ThreeDimensionalModels;

/**
 * Load 3D models.
 */
public class ModelLoader {

    public static final String BUILT_IN_MODELS_DIRECTORY = "3dmodels";
    private static final File CUSTOM_MODELS_FOLDER;

    static {
        StringJoiner sj = new StringJoiner(File.separator);
        sj.add(System.getProperty("user.home"));
        sj.add("Documents");
        sj.add("ActiLife Automation");
        sj.add("3d_models");

        CUSTOM_MODELS_FOLDER = new File(sj.toString());
        CUSTOM_MODELS_FOLDER.mkdirs();
    }

    private ModelLoader() {
    }

    public static void loadAllModels(ThreeDimensionalModels models) {

        //built in models.
        URL url = getBuiltInModelsDirectory();
        addModelsFromDirectory(url, models);

        //custom models.
        models.add3DModels(CUSTOM_MODELS_FOLDER.listFiles(), true);
    }

    private static URL getBuiltInModelsDirectory() {
        return ClassLoader.getSystemResource(BUILT_IN_MODELS_DIRECTORY);
    }

    private static void addModelsFromDirectory(URL url, ThreeDimensionalModels models) {
        try {
            String path = URLDecoder.decode(url.getPath(), System.getProperty("file.encoding"));
            File[] files = new File(path).listFiles();
            models.add3DModels(files, false);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //todo throw exception.
        }
    }

    public static File getCustomModelsFolder() {
        return CUSTOM_MODELS_FOLDER;
    }
}
