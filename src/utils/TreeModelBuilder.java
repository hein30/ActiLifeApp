package utils;

import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import models.GeneratedFileModel;
import models.ImportedData;
import models.Subject;
import models.Subjects;

public class TreeModelBuilder {

    private TreeModelBuilder() {
        //hide constructor for as this is static util class.
    }


    public static DefaultTreeModel buildTreeModel(ImportedData importedData) {
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        final DefaultTreeModel treeModel = new DefaultTreeModel(root);

        importedData.getFileMap().forEach((k, v) -> addLeaves(k, v, root));
        return treeModel;
    }

    private static void addLeaves(String k, Subjects v, DefaultMutableTreeNode root) {

        GeneratedFileModel topNodeModel = new GeneratedFileModel(new Subject(k, k));

        DefaultMutableTreeNode topNode = new DefaultMutableTreeNode(topNodeModel);
        root.add(topNode);

        addChildren(topNode, v);
    }

    private static void addChildren(DefaultMutableTreeNode root, Subjects v) {
        v.getSubjectList()
                .stream()
                .map(Subject::getGeneratedModels)
                .flatMap(List::stream)
                .forEach(oneGeneratedFile -> root.add(new DefaultMutableTreeNode(oneGeneratedFile)));
    }
}
