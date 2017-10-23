package controllers;

import java.util.List;
import models.FileModel;

/**
 * A base controller from which other controllers should extend.
 */
public abstract class BaseController {

    public abstract void importFile(List<FileModel> files);

    public abstract void deleteFile();
}
