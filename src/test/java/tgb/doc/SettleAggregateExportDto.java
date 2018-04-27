package tgb.doc;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用于导出结算管理
 * Created by hezhao on 2017-08-09.
 */
public class SettleAggregateExportDto implements Serializable {

	public SettleAggregateExportDto(String settleMonth, String userId, String userName, String userTypeName, String orderTypeName,
									String isFreezeName, String applyTime, String checkTime, String statusName, BigDecimal priceYuan,
									BigDecimal notClearYuan, BigDecimal realIncomeYuan) {
		this.settleMonth = settleMonth;
		this.userId = userId;
		this.userName = userName;
		this.userTypeName = userTypeName;
		this.orderTypeName = orderTypeName;
		this.isFreezeName = isFreezeName;
		this.applyTime = applyTime;
		this.checkTime = checkTime;
		this.statusName = statusName;
		this.priceYuan = priceYuan;
		this.notClearYuan = notClearYuan;
		this.realIncomeYuan = realIncomeYuan;
	}

	public SettleAggregateExportDto() {
		super();
	}

	/**
	 * 结算月份
	 */
	private String settleMonth;
	/**
	 *开发者Id
	 */
	private String userId;
	/**
	 *企业名称
	 */
	private String userName;
	/**
	 *纳税人类型
	 */
	private String userTypeName;
	/**
	 *业务类型
	 */
	private String orderTypeName;
	/**
	 *账号状态
	 */
	private String isFreezeName;
	/**
	 * 提交结算请求的时间
	 */
	private String applyTime;
	/**
	 * 审核通过时间
	 */
	private String checkTime;
	/**
	 *结算状态
	 */
	private String statusName;
	/**
	 *订单流水
	 */
	private BigDecimal priceYuan;
	/**
	 *不分成可币券流水(元)
	 */
	private BigDecimal notClearYuan;
	/**
	 *合作方分成金额(元)
	 */
	private BigDecimal realIncomeYuan;
	public String getSettleMonth() {
		return settleMonth;
	}

	public void setSettleMonth(String settleMonth) {
		this.settleMonth = settleMonth;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getIsFreezeName() {
		return isFreezeName;
	}

	public void setIsFreezeName(String isFreezeName) {
		this.isFreezeName = isFreezeName;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public BigDecimal getPriceYuan() {
		return priceYuan;
	}

	public void setPriceYuan(BigDecimal priceYuan) {
		this.priceYuan = priceYuan;
	}

	public BigDecimal getNotClearYuan() {
		return notClearYuan;
	}

	public void setNotClearYuan(BigDecimal notClearYuan) {
		this.notClearYuan = notClearYuan;
	}

	public BigDecimal getRealIncomeYuan() {
		return realIncomeYuan;
	}

	public void setRealIncomeYuan(BigDecimal realIncomeYuan) {
		this.realIncomeYuan = realIncomeYuan;
	}
}
