package org.repeid.models.sessions.infinispan.initializer;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class InitializerStateTest {

    @Test
    public void testComputationState() {
        InitializerState state = new InitializerState();
        state.init(28, 5);

        Assert.assertFalse(state.isFinished());
        List<Integer> segments = state.getUnfinishedSegments(3);
        assertContains(segments, 3, 0, 1, 2);

        state.markSegmentFinished(1);
        state.markSegmentFinished(2);
        segments = state.getUnfinishedSegments(4);
        assertContains(segments, 4, 0, 3, 4, 5);

        state.markSegmentFinished(0);
        state.markSegmentFinished(3);
        segments = state.getUnfinishedSegments(4);
        assertContains(segments, 2, 4, 5);

        state.markSegmentFinished(4);
        state.markSegmentFinished(5);
        segments = state.getUnfinishedSegments(4);
        Assert.assertTrue(segments.isEmpty());
        Assert.assertTrue(state.isFinished());
    }

    private void assertContains(List<Integer> segments, int expectedLength, int... expected) {
        Assert.assertEquals(segments.size(), expectedLength);
        for (int i : expected) {
            Assert.assertTrue(segments.contains(i));
        }
    }
}
