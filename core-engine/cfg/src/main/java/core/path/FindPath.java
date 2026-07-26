package core.path;

import core.ast.additionalNodes.Node;
import core.cfg.CfgBoolExprNode;
import core.cfg.CfgForEachExpressionNode;
import core.cfg.CfgNode;
import core.cfg.CfgReturnStatementNode;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;

import java.util.*;

public class FindPath {

    private List<CfgNode> currentPath = new ArrayList<>();
    private Path path;
    private CfgNode currentDuplicateNode;
    private Set<CfgNode> visited = new HashSet<>();

    private FindPath() {}

    public FindPath(CfgNode beginNode, CfgNode middleNode, CfgNode endNode) {
        findPath(beginNode, middleNode);
        findPath(middleNode, endNode);
    }

    private final Random random = new Random();

    private static final double EXPLORATION_PROB = 0.2;
    private static final double SKIP_FOUND_PATH_PROB = 0.3;

    public FindPath(CfgNode beginNode, CfgNode endNode) {
        findPath(beginNode, endNode);
    }

    /**
     * Chọn nhánh nào thử trước, dựa trên trọng số nghịch đảo penalty,
     * cộng thêm một xác suất khám phá ngẫu nhiên thuần túy.
     */
    private boolean chooseFirst(int firstPenalty, int secondPenalty) {

        if (random.nextDouble() < EXPLORATION_PROB) {
            return random.nextBoolean();
        }

        double firstWeight = 1.0 / (1 + firstPenalty);
        double secondWeight = 1.0 / (1 + secondPenalty);

        double total = firstWeight + secondWeight;

        return random.nextDouble() < firstWeight / total;
    }

    /**
     * Sau khi tìm được path, có một xác suất nhỏ "bỏ qua" nó (reset về null)
     * để buộc thuật toán thử nhánh còn lại, tăng tính đa dạng của đường đi
     * qua các lần gọi khác nhau, thay vì luôn chốt path đầu tiên tìm được.
     */
    private void maybeSkipFoundPath() {
        return ;
        /*
        if (path != null && random.nextDouble() < SKIP_FOUND_PATH_PROB) {
            path = null;
        }

         */
    }

    private void findPath(CfgNode beginNode, CfgNode endNode) {

        if (beginNode == null || path != null)
            return;

        if (visited.contains(beginNode))
            return;

        if (beginNode == endNode) {

            currentPath.add(beginNode);

            path = new Path();
            for (CfgNode node : currentPath)
                path.addLast(node);

            currentPath.remove(currentPath.size() - 1);
            return;
        }

        if (beginNode.getIsEndCfgNode()
                || beginNode.getAst() instanceof ReturnStatement
                || beginNode.getAst() instanceof ThrowStatement)
            return;

        currentPath.add(beginNode);
        visited.add(beginNode);

        if (beginNode instanceof CfgBoolExprNode) {

            CfgBoolExprNode node = (CfgBoolExprNode) beginNode;

            boolean chooseTrueFirst = chooseFirst(node.trueCounting, node.falseCounting);

            CfgNode first = chooseTrueFirst ? node.getTrueNode() : node.getFalseNode();
            CfgNode second = chooseTrueFirst ? node.getFalseNode() : node.getTrueNode();

            if (path == null) {
                findPath(first, endNode);

                if (path == null) {
                    // nhánh đầu thất bại -> tăng penalty để lần sau ít ưu tiên hơn
                    if (chooseTrueFirst) node.trueCounting++;
                    else node.falseCounting++;
                } else {
                    // tìm thấy rồi, nhưng đôi khi cố tình bỏ qua để đa dạng đường đi
                    maybeSkipFoundPath();
                }
            }

            if (path == null)
                findPath(second, endNode);

        } else if (beginNode instanceof CfgForEachExpressionNode) {

            CfgForEachExpressionNode node = (CfgForEachExpressionNode) beginNode;

            boolean chooseHasElement =
                    chooseFirst(node.hasElementCounting, node.noMoreElementCounting);

            CfgNode first = chooseHasElement
                    ? node.getHasElementAfterNode()
                    : node.getNoMoreElementAfterNode();
            CfgNode second = chooseHasElement
                    ? node.getNoMoreElementAfterNode()
                    : node.getHasElementAfterNode();

            if (path == null) {
                findPath(first, endNode);

                if (path == null) {
                    if (chooseHasElement) node.hasElementCounting++;
                    else node.noMoreElementCounting++;
                } else {
                    maybeSkipFoundPath();
                }
            }

            if (path == null)
                findPath(second, endNode);

        } else {
            findPath(beginNode.getAfterStatementNode(), endNode);
        }

        currentPath.remove(currentPath.size() - 1);
        visited.remove(beginNode);
    }

    /**
     * Gọi hàm này từ bên ngoài (caller) sau khi solve/test trên path này thất bại,
     * để tăng thêm penalty cho các CfgBoolExprNode / CfgForEachExpressionNode
     * nằm trên path đó -> lần gọi FindPath tiếp theo sẽ có xu hướng đi đường khác.
     */
    public static void penalizePath(Path failedPath, boolean goTrueOnLastNode, CfgNode lastDecisionNode) {
        if (failedPath == null) return;

        Node current = failedPath.getCurrentFirst(); // giả sử Path có phương thức trả về Node đầu tiên

        while (current != null) {

            CfgNode data = current.getData();
            Node nextNode = current.getNext();
            CfgNode nextData = (nextNode != null) ? nextNode.getData() : null;

            if (data instanceof CfgBoolExprNode) {
                CfgBoolExprNode b = (CfgBoolExprNode) data;

                if (nextData != null) {
                    // biết chính xác path đã rẽ true hay false -> chỉ phạt đúng hướng đã đi
                    if (nextData == b.getTrueNode()) {
                        b.trueCounting++;
                    } else if (nextData == b.getFalseNode()) {
                        b.falseCounting++;
                    }
                    // nếu nextData không khớp cả hai (ví dụ do CFG mutation), bỏ qua, không đoán mò
                }

            } else if (data instanceof CfgForEachExpressionNode) {
                CfgForEachExpressionNode f = (CfgForEachExpressionNode) data;

                if (nextData != null) {
                    if (nextData == f.getHasElementAfterNode()) {
                        f.hasElementCounting++;
                    } else if (nextData == f.getNoMoreElementAfterNode()) {
                        f.noMoreElementCounting++;
                    }
                }
            }

            current = nextNode;
        }

        // node quyết định cuối cùng (branch coverage target, không nằm trong path vì nó được addLast riêng
        // hoặc là node cuối không có "next" để so sánh) -> đã biết chắc hướng từ caller, phạt nặng hơn
        if (lastDecisionNode instanceof CfgBoolExprNode) {
            CfgBoolExprNode b = (CfgBoolExprNode) lastDecisionNode;
            if (goTrueOnLastNode) b.trueCounting += 2;
            else b.falseCounting += 2;
        }
    }

    public Path getPath() {
        return path;
    }
}
