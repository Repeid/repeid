package org.repeid.models.sessions.infinispan.initializer;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.repeid.models.sessions.infinispan.entities.SessionEntity;

public class InitializerState extends SessionEntity {

	private static final Logger log = Logger.getLogger(InitializerState.class);

	private int sessionsCount;
	private List<Boolean> segments = new ArrayList<>();
	private int lowestUnfinishedSegment = 0;

	public void init(int sessionsCount, int sessionsPerSegment) {
		this.sessionsCount = sessionsCount;

		int segmentsCount = sessionsCount / sessionsPerSegment;
		if (sessionsPerSegment * segmentsCount < sessionsCount) {
			segmentsCount = segmentsCount + 1;
		}

		log.debugf("sessionsCount: %d, sessionsPerSegment: %d, segmentsCount: %d", sessionsCount, sessionsPerSegment,
				segmentsCount);

		for (int i = 0; i < segmentsCount; i++) {
			segments.add(false);
		}

		updateLowestUnfinishedSegment();
	}

	// Return true just if computation is entirely finished (all segments are
	// true)
	public boolean isFinished() {
		return lowestUnfinishedSegment == -1;
	}

	// Return next un-finished segments. It can return "segmentCount" segments
	// or less
	public List<Integer> getUnfinishedSegments(int segmentCount) {
		List<Integer> result = new ArrayList<>();
		int next = lowestUnfinishedSegment;
		boolean remaining = lowestUnfinishedSegment != -1;

		while (remaining && result.size() < segmentCount) {
			next = getNextUnfinishedSegmentFromIndex(next);
			if (next == -1) {
				remaining = false;
			} else {
				result.add(next);
				next++;
			}
		}

		return result;
	}

	public void markSegmentFinished(int index) {
		segments.set(index, true);
		updateLowestUnfinishedSegment();
	}

	private void updateLowestUnfinishedSegment() {
		this.lowestUnfinishedSegment = getNextUnfinishedSegmentFromIndex(lowestUnfinishedSegment);
	}

	private int getNextUnfinishedSegmentFromIndex(int index) {
		int segmentsSize = segments.size();
		for (int i = index; i < segmentsSize; i++) {
			Boolean entry = segments.get(i);
			if (!entry) {
				return i;
			}
		}

		return -1;
	}

	public String printState() {
		int finished = 0;
		int nonFinished = 0;

		int size = segments.size();
		for (int i = 0; i < size; i++) {
			Boolean done = segments.get(i);
			if (done) {
				finished++;
			} else {
				nonFinished++;
			}
		}

		StringBuilder strBuilder = new StringBuilder("sessionsCount: " + sessionsCount)
				.append(", finished segments count: " + finished)
				.append(", non-finished segments count: " + nonFinished);

		return strBuilder.toString();
	}
}
