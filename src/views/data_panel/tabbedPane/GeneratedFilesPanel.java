package views.data_panel.tabbedPane;

import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import utils.CreateChildNodes;

public class GeneratedFilesPanel extends JScrollPane {

    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JTree tree;

    public GeneratedFilesPanel() {
        setBorder(BorderFactory.createTitledBorder("Generate Files"));
    }

    public void updateGeneratedFilesView(File rootDirectory) {

        root = new DefaultMutableTreeNode();
        treeModel = new DefaultTreeModel(root);

        tree = new JTree(treeModel);
        tree.expandPath(new TreePath(root.getPath()));

        setViewportView(tree);

        CreateChildNodes createChildNodes = new CreateChildNodes(rootDirectory, root);

        createChildNodes.run();

    }
}
