package Bowling;

import java.util.HashMap;
import java.util.Map;

public class testScore {
	final static int EMPTY_PIN = 0;
	final static int FIRST_BALL = 1;
	final static int SECOND_BALL = 2;
	final static int FINAL_BALL = 3;
	final static int FULL_PIN = 10;
	final static int FIRST_FRAME = 1;
	final static int LAST_FRAME = 10;

	public int getTestResult(int result[][], int last) {
		int ball = 1;
		int pin = 10;
		int frame = 1;
		int total = 0;
		int nScore = 0;

		Map<String, Boolean> nowStatus = new HashMap<String, Boolean>();

		nowStatus.put("isTurkey", false);
		nowStatus.put("isStrike", false);
		nowStatus.put("isSpare", false);
		nowStatus.put("isDouble", false);

		for (frame = FIRST_FRAME; frame <= LAST_FRAME; frame++) {
			for (ball = FIRST_BALL; ball <= SECOND_BALL; ball++) {

				nScore = result[frame - 1][ball - 1];
				pin -= nScore;
				total += nScore;

				if (frame < LAST_FRAME) {
					if (ball == FIRST_BALL) {
						if (nowStatus.get("isSpare")) {
							total += nScore;
							nowStatus = updateStatus(nowStatus, "");
						} else if (nowStatus.get("isStrike")) {
							total += nScore;
						} else if ((nowStatus.get("isTurkey") || nowStatus.get("isDouble"))) {
							total += nScore * 2;
						}
					} else {
						if (isOneOfStrikeTurkeyDouble(nowStatus)) {
							total += nScore;
							nowStatus = updateStatus(nowStatus, "");
						}
					}

					if (pin == EMPTY_PIN) {
						if (ball == FIRST_BALL) {
							if (nowStatus.get("isTurkey")) {
								nowStatus = updateStatus(nowStatus, "isTurkey");
							} else if (nowStatus.get("isDouble")) {
								nowStatus = updateStatus(nowStatus, "isTurkey");
							} else if (nowStatus.get("isStrike")) {
								nowStatus = updateStatus(nowStatus, "isDouble");
							} else {
								nowStatus = updateStatus(nowStatus, "isStrike");
							}
						} else if (ball == SECOND_BALL) {
							nowStatus = updateStatus(nowStatus, "isSpare");
						}
					}

				} else if (frame == LAST_FRAME) {
					if (nowStatus.get("isSpare")) {
						total += nScore;
						nowStatus = updateStatus(nowStatus, "");
					} else if (isOneOfStrikeTurkeyDouble(nowStatus)) {
						if (ball == SECOND_BALL) {
							total += nScore;
							nowStatus = updateStatus(nowStatus, "");
						} else {
							if (nowStatus.get("isStrike"))
								total += nScore;
							else
								total += nScore * 2;
						}
					}

				}

				if (pin == EMPTY_PIN || ball == SECOND_BALL) {
					pin = FULL_PIN;

					if (frame != LAST_FRAME)
						break;
				}

			}
		}
		if ((result[9][0] + result[9][1]) >= 10)
			total += last;
		return total;

	}

	private static boolean isOneOfStrikeTurkeyDouble(Map<String, Boolean> nowStatus) {
		return nowStatus.get("isStrike") || nowStatus.get("isTurkey") || nowStatus.get("isDouble");
	}

	private static Map<String, Boolean> updateStatus(Map<String, Boolean> nowStatus, String update) {
		nowStatus.replaceAll((key, value) -> value = false);
		if (update != "")
			nowStatus.replace(update, true);
		return nowStatus;

	}

}
