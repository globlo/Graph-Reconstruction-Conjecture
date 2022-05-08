import java.util.ArrayList;

public class IsomorphimTreeNode {

    ArrayList<IsomorphimTreeNode> children = new ArrayList<IsomorphimTreeNode>();
    int vertexIndex = -1;
    int[] adjArr;

    public IsomorphimTreeNode(int vertexIndex, int[] adjacentVertices) {
        this.vertexIndex = vertexIndex;
        adjArr = adjacentVertices;
    }

    public IsomorphimTreeNode(int vertexIndex) {
        this.vertexIndex = vertexIndex;
    }

    

    public static IsomorphimTreeNode createInitialTree(int vertexCount) {
        IsomorphimTreeNode root = new IsomorphimTreeNode(-1);
        for (int i = 0; i < vertexCount; i++) {
            root.children.add(new IsomorphimTreeNode(i));
        }
        return root;
    }

    public static void generateChildren(IsomorphimTreeNode parent, int[] childSequence) {
        for (int i = 0; i < childSequence.length; i++) {
            parent.children.add(new IsomorphimTreeNode(childSequence[i]));
        }
    }
}