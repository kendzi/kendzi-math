package kendzi.math.geometry.skeleton.path;

import kendzi.math.geometry.skeleton.utils.ValidateUtil;

/**
 * Util for face queue.
 * 
 * @author Tomasz Kedziora (Kendzi)
 * 
 */
public class FaceQueueUtil {

    private FaceQueueUtil() {
        //
    }

    /**
     * Connect two nodes queue. Id both nodes comes from the same queue, queue
     * is closed. If nodes are from different queues nodes are moved to one of
     * them.
     * 
     * @param firstFace
     *            first face queue
     * @param secondFace
     *            second face queue
     */
    public static void connectQueues(FaceNode firstFace, FaceNode secondFace) {

        ValidateUtil.validateNotNull(firstFace.list(), "firstFace.list");
        ValidateUtil.validateNotNull(secondFace.list(), "secondFace.list");

        if (firstFace.list() == secondFace.list()) {
            if (!firstFace.isEnd() || !secondFace.isEnd()) {
                throw new IllegalStateException("try to connect the same list not on end nodes");
            }
            if (firstFace.isQueueUnconnected() || secondFace.isQueueUnconnected()) {
                throw new IllegalStateException("can't close node queue not conected with edges");
            }

            firstFace.queueClose();
            return;
        }
        if (!firstFace.isQueueUnconnected() && !secondFace.isQueueUnconnected()) {
            throw new IllegalStateException("can't connect two diffrent queues if each of them is connected to edge");
        }

        if (!firstFace.isQueueUnconnected()) {
            FaceQueue qLeft = secondFace.getFaceQueue();
            moveNodes(firstFace, secondFace);
            qLeft.close();

        } else {
            FaceQueue qRight = firstFace.getFaceQueue();
            moveNodes(secondFace, firstFace);
            qRight.close();
        }
    }

    private static void moveNodes(FaceNode firstFace, FaceNode secondFace) {
        firstFace.addQueue(secondFace);
    }
}
