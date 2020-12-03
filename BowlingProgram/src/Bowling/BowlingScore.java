package Bowling;

public class BowlingScore {
	private BowlingDTO dto;

	public BowlingScore(BowlingDTO dto) {
		this.dto = dto;
	}

	public void setScore() {
		if (dto.getFrame() < BowlingDTO.LAST_FRAME) {
			if (dto.getBall() == BowlingDTO.FIRST_BALL) {
				if (dto.getNowStatus("Spare")) {
					dto.setTotal(dto.getTotal() + dto.getnScore());
					dto.setNowStatus("");
				} else if (dto.getNowStatus("Strike")) {
					dto.setTotal(dto.getTotal() + dto.getnScore());
				} else if ((dto.getNowStatus("Turkey") || dto.getNowStatus("Double"))) {
					dto.setTotal(dto.getTotal() + dto.getnScore() * 2);
				}
			} else {
				if (dto.isOneOfStrikeTurkeyDouble()) {
					dto.setTotal(dto.getTotal() + dto.getnScore());
					dto.setNowStatus("");
				}
			}
		} else if (dto.getFrame() == BowlingDTO.LAST_FRAME) {
			if (!dto.isLastBall()) {
				if (dto.getNowStatus("Spare")) {
					dto.setTotal(dto.getTotal() + dto.getnScore());
					dto.setNowStatus("");
				} else if (dto.isOneOfStrikeTurkeyDouble()) {
					if (dto.getBall() == BowlingDTO.SECOND_BALL) {
						dto.setTotal(dto.getTotal() + dto.getnScore());
						dto.setNowStatus("");
					} else {
						if (dto.getNowStatus("Strike"))
							dto.setTotal(dto.getTotal() + dto.getnScore());
						else
							dto.setTotal(dto.getTotal() + dto.getnScore() * 2);
					}
				}
			}

		}

	}

}
