package views.data_panel.tabbedPane;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
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

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                    if (!node.children().asIterator().hasNext()) {
                        try {
                            Desktop.getDesktop().open(((CreateChildNodes.FileNode) node.getUserObject()).getFile());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        super.mouseClicked(e);
                    }
                } else {
                    super.mouseClicked(e);
                }
            }
        });

        setViewportView(tree);

        CreateChildNodes createChildNodes = new CreateChildNodes(rootDirectory, root);

        createChildNodes.run();

    }
}
