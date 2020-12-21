package Bowling;

public class BowlingScore {
	public void setScore(UserDTO userDto) {
		if (userDto.getFrame() < UserDTO.LAST_FRAME) {
			if (userDto.getBall() == UserDTO.FIRST_BALL) {
				if (userDto.getNowStatus("Spare")) {
					userDto.setTotal(userDto.getTotal() + userDto.getnScore());
					userDto.setNowStatus("");
				} else if (userDto.getNowStatus("Strike")) {
					userDto.setTotal(userDto.getTotal() + userDto.getnScore());
				} else if ((userDto.getNowStatus("Turkey") || userDto.getNowStatus("Double"))) {
					userDto.setTotal(userDto.getTotal() + userDto.getnScore() * 2);
				}
			} else {
				if (userDto.isOneOfStrikeTurkeyDouble()) {
					userDto.setTotal(userDto.getTotal() + userDto.getnScore());
					userDto.setNowStatus("");
				}
			}
		} else if (userDto.getFrame() == UserDTO.LAST_FRAME) {
			if (!userDto.isLastBall()) {
				if (userDto.getNowStatus("Spare")) {
					userDto.setTotal(userDto.getTotal() + userDto.getnScore());
					userDto.setNowStatus("");
				} else if (userDto.isOneOfStrikeTurkeyDouble()) {
					if (userDto.getBall() == UserDTO.SECOND_BALL) {
						userDto.setTotal(userDto.getTotal() + userDto.getnScore());
						userDto.setNowStatus("");
					} else {
						if (userDto.getNowStatus("Strike"))
							userDto.setTotal(userDto.getTotal() + userDto.getnScore());
						else
							userDto.setTotal(userDto.getTotal() + userDto.getnScore() * 2);
					}
				}
			}

		}

	}

}
