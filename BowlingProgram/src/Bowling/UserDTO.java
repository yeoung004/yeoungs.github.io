package Bowling;
import java.util.HashMap;
import java.util.Map;

public class UserDTO {
	final static int EMPTY_PIN = 0;
	final static int FIRST_BALL = 1;
	final static int SECOND_BALL = 2;
	final static int FINAL_BALL = 3;
	final static int FULL_PIN = 10;
	final static int FIRST_FRAME = 1;
	final static int LAST_FRAME = 10;
	final static int STRIKE_SPARE = 10;
	 
	private String score = "";
	private int[][] result = { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 },
			{ 0, 0 } };

	private Map<String, Boolean> nowStatus = new HashMap<String, Boolean>();
	
	private int ball = 1;
	private int pin = 10;
	private int frame = 1;
	private int nScore = 0;
	private boolean lastBall = false;
	private String printTemp = "";
	private int ballCnt = 1;
	private int total = 0;
	private String totalTemp = "";
	private int playerNumber = 0;
	

	public UserDTO(int playerNumber) {
		this.playerNumber = playerNumber;
		
		nowStatus.put("Turkey", false);
		nowStatus.put("Strike", false);
		nowStatus.put("Spare", false);
		nowStatus.put("Double", false);
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setLastBall(boolean lastBall) {
		this.lastBall = lastBall;
	}

	public int getResultLength(){
		return this.result.length;
	}
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public int getResult(int frame, int ball) {
		return this.result[frame][ball];
	}
	public int[][] getResult() {
		return this.result;
	}

	public void setResult(int frame, int ball, int nScore) {
		this.result[frame][ball] = nScore;
	}

	public int getBallCnt() {
		return ballCnt;
	}

	public void setBallCnt(int ballCnt) {
		this.ballCnt = ballCnt;
	}

	public int getBall() {
		return ball;
	}

	public void setBall(int ball) {
		this.ball = ball;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getnScore() {
		return nScore;
	}

	public void setnScore(int nScore) {
		this.nScore = nScore;
	}

	public boolean isLastBall() {
		return lastBall;
	}

	public void userDto(boolean lastBall) {
		this.lastBall = lastBall;
	}

	public boolean getNowStatus(String nowStatus) {
		return this.nowStatus.get(nowStatus);
	}

	public void setNowStatus(String update) {
		this.nowStatus.replaceAll((key, value) -> value = false);
		if (update != "")
			this.nowStatus.replace(update, true);
	}
	
	public boolean isOneOfStrikeTurkeyDouble() {
		return getNowStatus("Strike") || getNowStatus("Turkey") || getNowStatus("Double");
	}

	public String getPrintTemp() {
		return printTemp;
	}

	public void setPrintTemp(String printTemp) {
		this.printTemp = printTemp;
	}

	public String getTotalTemp() {
		return totalTemp;
	}

	public void setTotalTemp(String totalTemp) {
		this.totalTemp = totalTemp;
	}
}
