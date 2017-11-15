package utils;

import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;

public class OldCreateChildNodes implements Runnable {

    private DefaultMutableTreeNode root;
    private File fileRoot;

    public OldCreateChildNodes(File fileRoot, DefaultMutableTreeNode root) {
        this.fileRoot = fileRoot;
        this.root = root;
    }

    @Override
    public void run() {
        createChildren(fileRoot, root);
    }

    private void createChildren(File fileRoot,
                                DefaultMutableTreeNode node) {
        File[] files = fileRoot.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            DefaultMutableTreeNode childNode =
                    new DefaultMutableTreeNode(new FileNode(file));
            node.add(childNode);
            if (file.isDirectory()) {
                createChildren(file, childNode);
            }
        }
    }

    public class FileNode {

        private File file;

        public FileNode(File file) {
            this.file = file;
        }

        public File getFile() {
            return file;
        }

        @Override
        public String toString() {
            String name = file.getName();
            if (name.equals("")) {
                return file.getAbsolutePath();
            } else {
                return name;
            }
        }
    }
}