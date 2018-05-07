package com.hz.tgb.test.doc;

import com.hz.tgb.data.BaseDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hezhao on 2017-09-20 09:43.
 */
public class SettleAggDaoImpl extends BaseDao {
    public List<SettleAggregateDto> getReleaseInfo(){
        List<SettleAggregateDto> releaseInfo = new ArrayList<>();

        String sql = "select settle_month, sum(price) as price,sum(refund_amount) as refund_amount,sum(real_income) as real_income,sum(not_clear) as not_clear, ";
        sql += "sum(clear_amount) as clear_amount,sum(channel_cost) as channel_cost,sum(ratio_cost) as ratio_cost, ";
        sql += "case (select count(*) from settle_aggregate where sa.settle_month = settle_month and sa.order_type = order_type and status = 0) ";
        sql += "when 0 then 1 ";
        sql += " else 0 ";
        sql += " end as status ";
        sql += " from settle_aggregate sa ";
        sql += " where 1=1 and order_type = 1 ";
        sql += " group by settle_month ";
        sql += " order by settle_month desc ";

        super.executeQuery(sql);

        try {
            while(super.rs.next()){
                SettleAggregateDto dto = new SettleAggregateDto();

                dto.setSettleMonth(rs.getString("settle_month"));
                Byte status_0 = rs.getByte("status");
                if(status_0 != null){
                    //if(status=0,0,1)
                    Byte status = status_0;
                    status = status.equals((byte)0) ? (byte)0 : (byte)1;
                    dto.setStatus(status);
                }
                dto.setPrice(rs.getLong("price"));
                dto.setRefundTotal(rs.getLong("refund_amount"));
                dto.setRealIncome(rs.getBigDecimal("real_income"));
                dto.setNotClear(rs.getBigDecimal("not_clear"));
                dto.setClearAmount(rs.getBigDecimal("clear_amount"));
                dto.setChannelCost(rs.getBigDecimal("channel_cost"));
                dto.setTaxCost(rs.getBigDecimal("ratio_cost"));

                releaseInfo.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            super.closeAll();
        }

        return releaseInfo;
    }
}
