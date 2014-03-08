package sql;

import java.sql.Timestamp;

public class TableData {

	String transactionId;
	String payoutTransactionId;
	String result;
	double betAmount;
	double payoutAmount;
	int roundNum;

	public int getRoundNum() {
		return roundNum;
	}

	public void setRoundNum(int roundNum) {
		this.roundNum = roundNum;
	}

	Timestamp timestamp;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPayoutTransactionId() {
		return payoutTransactionId;
	}

	public void setPayoutTransactionId(String payoutTransactionId) {
		this.payoutTransactionId = payoutTransactionId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public double getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(double betAmount) {
		this.betAmount = betAmount;
	}

	public double getPayoutAmount() {
		return payoutAmount;
	}

	public void setPayoutAmount(double payoutAmount) {
		this.payoutAmount = payoutAmount;
	}

	@Override
	public String toString() {
		return "TableData [transactionId=" + transactionId
				+ ", payoutTransactionId=" + payoutTransactionId + ", result="
				+ result + ", betAmount=" + betAmount + ", payoutAmount="
				+ payoutAmount + ", roundNum=" + roundNum + ", timestamp="
				+ timestamp + "]";
	}

	

}
