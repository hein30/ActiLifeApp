package controllers;

import java.util.List;
import models.FileModel;
import models.ImportedData;

/**
 * A base controller from which other controllers should extend.
 */
public abstract class BaseController {

    public abstract void importFiles(List<FileModel> files);

    public abstract void deleteFiles(List<String> fileNamesToDelete);

    public abstract void updateGeneratedFilesView(ImportedData defaultDestinationFolder);

}
