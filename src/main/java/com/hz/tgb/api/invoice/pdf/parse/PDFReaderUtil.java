package com.hz.tgb.api.invoice.pdf.parse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hz.tgb.api.invoice.pdf.constants.ReceiptTaxFlg;
import com.hz.tgb.api.invoice.pdf.entity.ReceiptPosition;
import com.hz.tgb.api.invoice.pdf.util.PDFNumberValidationUtils;
import com.hz.tgb.api.invoice.pdf.util.PDFSortUtil;
import com.hz.tgb.api.invoice.pdf.util.PDFStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author
 * @d2018-3-23
 * <h1>解析PDF电子发票</h1>
 * <h2>待强化：</h2>
 * 		1、整体布局左右变动，可能出现数据偏差或丢失<br/>
 * 		2、字符跳行像素过大，导致数据丢失<br/>
 * 		3、仅解析发票前两页pdf（先假定销货清单有且只有一页），第二页销货清单已解析，若超过两页，待后续开发<br/>
 */
public class PDFReaderUtil {

	public static final String kpje_key = "(小写)";
	public static final String kpje_key2 = "（小写）";
	public static final String kpje_key3 = "(大写)";
	public static final String kpje_key4 = "（大写）";
	public static final String slv_key = "税率";

	private static final String FPDM = "发票代码";
	private static final String FPHM = "发票号码";
	private static final String KPRQ = "开票日期";
	private static final String JYM = "校验码";
	private static final String MC = "名称";
	private static final String NSRSBH = "纳税人识别号";
	private static final String DZDH = "地址、电话";
	private static final String KHHJZH = "开户行及账号";
	private static final String JQBH = "机器编号";
	private static final String HJ = "合计";
	private static final String HJ_H = "合";
	private static final String HJ_J = "计";
	private static final String JSHJ = "价税合计";
	private static final String HWLWFWMC = "货物或应税劳务、服务名称";
	private static final String HWLWFWMC_P = "货物或应税劳务、服";
	private static final String GGXH = "规格型号";
	private static final String DW = "单位";
	private static final String DW_D = "单";
	private static final String DW_W = "位";
	private static final String SL = "数量";
	private static final String SL_S = "数";
	private static final String SL_L = "量";
	private static final String DJ = "单价";
	private static final String DJ_D = "单";
	private static final String DJ_J = "价";
	private static final String JE = "金额";
	private static final String JE_J = "金";
	private static final String JE_E = "额";
	private static final String SLV = "税率";
	private static final String SLV_S = "税";
	private static final String SLV_LV = "率";
	private static final String SE = "税额";
	private static final String SE_S = "税";
	private static final String SE_E = "额";
	private static final String MMQ = "密码区";
	private static final String BZ = "备注";
	private static final String MMQ_BZ = "密码区备注";

	// 参照关键字
	/**
	 * 机器编号
	 */
	private static ReceiptPosition jqbhR = null;
	/**
	 * 合计
	 */
	private static ReceiptPosition hjR = null;
	/**
	 * 价税合计
	 */
	private static ReceiptPosition jshjR = null;
	/**
	 * 货物或应税劳务、服务名称
	 */
	private static ReceiptPosition hwmcR = null;
	/**
	 * 规格型号
	 */
	private static ReceiptPosition ggxhR = null;
	/**
	 * 单位
	 */
	private static ReceiptPosition danweiR = null;
	/**
	 * 数量
	 */
	private static ReceiptPosition shuliangR = null;
	/**
	 * 单价
	 */
	private static ReceiptPosition danjiaR = null;
	/**
	 * 金额
	 */
	private static ReceiptPosition jineR = null;
	/**
	 * 税率
	 */
	private static ReceiptPosition shuilvR = null;
	/**
	 * 税额
	 */
	private static ReceiptPosition shuieR = null;
	/**
	 * 密码区
	 */
	private static ReceiptPosition mmqR = null;
	/**
	 * 备注
	 */
	private static ReceiptPosition bzR = null;
	//补充：销货清单 2018-05-02---$代表销货清单
	/**
	 * 销货清单-序号
	 */
	private static ReceiptPosition xh$R = null;
	/**
	 * 销货清单-货物（劳务）名称
	 */
	private static ReceiptPosition hwmc$R = null;
	/**
	 * 销货清单-规格型号
	 */
	private static ReceiptPosition ggxh$R = null;
	/**
	 * 销货清单-单位
	 */
	private static ReceiptPosition dw$R = null;
	/**
	 * 销货清单-数量
	 */
	private static ReceiptPosition sl$R = null;
	/**
	 * 销货清单-单价
	 */
	private static ReceiptPosition dj$R = null;
	/**
	 * 销货清单-金额
	 */
	private static ReceiptPosition je$R = null;
	/**
	 * 销货清单-税率
	 */
	private static ReceiptPosition slv$R = null;
	/**
	 * 销货清单-税额
	 */
	private static ReceiptPosition se$R = null;

	/**
	 * 去除
	 * @param str
	 * @return
	 */
	private static String trim(String str) {
		if (str == null) {
			return "";
		}
		str = str.trim();
		str = str.replaceFirst(":", "");
		str = str.replaceFirst("：", "");
		return str.trim();
	}

	public static JSONObject readReceiptPdf(String pdfFileName) throws Exception {

		List<ReceiptPosition> mainList = null;//主页
		List<ReceiptPosition> addendumList = null;//附页
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(pdfFileName));

			PDPageTree pages = document.getPages();
			int pageCount = pages.getCount();

			// 只有一页
			if(pageCount == 1){
				MyPDFTextStripper stripper = new MyPDFTextStripper();
				stripper.setStartPage(1);
				stripper.setEndPage(1);
				stripper.setSortByPosition(true);
				stripper.getText(document);
				mainList = stripper.getPosList();
			}else if(pageCount > 1){
				// 超过一页，只解析前两页
				MyPDFTextStripper stripperMain = new MyPDFTextStripper();
				stripperMain.setStartPage(1);
				stripperMain.setEndPage(1);
				stripperMain.setSortByPosition(true);
				stripperMain.getText(document);
				mainList = stripperMain.getPosList();

				MyPDFTextStripper stripperAddendum = new MyPDFTextStripper();
				stripperAddendum.setStartPage(2);
				stripperAddendum.setEndPage(2);
				stripperAddendum.setSortByPosition(true);
				stripperAddendum.getText(document);
				addendumList = stripperAddendum.getPosList();
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}

		// 记录特殊关键字起始坐标
		specificDW(mainList);

		if(null != addendumList){
			JSONObject json = reorganizationRegulation(mainList);

			try {
				JSONObject xhqdJson = reorganizationRegulationSA(addendumList);
				// 第二页销货清单
				json.put("qdxxs", xhqdJson);
			} catch (Exception e) {
				return json;
			}
			return json;
		}else{
			return reorganizationRegulation(mainList);
		}
	}

	/**
	 * 解析销货清单（先假定销货清单有且只有一页）
	 * @param addendumList
	 * @return
	 */
	private static JSONObject reorganizationRegulationSA(List<ReceiptPosition> addendumList) {

		JSONObject json = new JSONObject();

		// y排序
		PDFSortUtil.sort(addendumList, "posY");

		List<ReceiptPosition> indexList = new ArrayList<ReceiptPosition>();
		//行处理
		List<ReceiptPosition>  lineStrList = new ArrayList<ReceiptPosition>();

		for (int i = 0; i < addendumList.size(); i++) {

			ReceiptPosition rp = addendumList.get(i);

			if(i+1 < addendumList.size()){
				ReceiptPosition rp2 = addendumList.get(i+1);
				if(Math.abs(rp.getPosY() - rp2.getPosY()) < 2){
					indexList.add(rp);
				}else{
					indexList.add(rp);

					PDFSortUtil.sort(indexList, "posX");

					ReceiptPosition receiptPosition = new ReceiptPosition();

					for (ReceiptPosition r : indexList) {
						String text = receiptPosition.getText();
						if(null == text){
							text = "";
						}
						text += r.getText();

						receiptPosition.setPosEndX(r.getPosEndX());
						receiptPosition.setPosEndY(r.getPosEndY());
						receiptPosition.setPosLastX(r.getPosLastX());
						receiptPosition.setPosLastY(r.getPosLastY());
						receiptPosition.setPosX(r.getPosX());
						receiptPosition.setPosY(r.getPosY());
						receiptPosition.setText(text);
					}

					lineStrList.add(receiptPosition);
					indexList = new ArrayList<ReceiptPosition>();
				}

			}else if(i+1 == addendumList.size()){
				PDFSortUtil.sort(indexList, "posX");
				//补全最后一个字
				indexList.add(rp);

				ReceiptPosition receiptPosition = new ReceiptPosition();

				for (ReceiptPosition r : indexList) {
					String text = receiptPosition.getText();
					if(null == text){
						text = "";
					}
					text += r.getText();

					receiptPosition.setPosEndX(r.getPosEndX());
					receiptPosition.setPosEndY(r.getPosEndY());
					receiptPosition.setPosLastX(r.getPosLastX());
					receiptPosition.setPosLastY(r.getPosLastY());
					receiptPosition.setPosX(r.getPosX());
					receiptPosition.setPosY(r.getPosY());
					receiptPosition.setText(text);
				}

				lineStrList.add(receiptPosition);
			}
		}

		if("销售货物或者提供应税劳务清单".equals(lineStrList.get(lineStrList.size() - 1).getText())){
			//记录（关键字）对应Y轴----$代表销货清单
			//销售方(章)
			float xsfY$ = 0f;
			//备注
			float bzY$ = 0f;
			//总计
			float zjY$ = 0f;
			//小计
			float xjY$ = 0f;
			//序号 货物(劳务)名称
			float xhY$ = 0f;
			//所属增值税电子普通发票代码---号码---页码
			float fpdmY$ = 0f;
			//销售方名称
			float xsfmcY$ = 0f;
			//购买方名称
			float gmfmcY$ = 0f;

			for (int j = 0; j < lineStrList.size(); j++) {
				ReceiptPosition receiptPosition = lineStrList.get(j);
				String text = receiptPosition.getText()
						.trim()
						.replaceAll(" ", "")
						.replaceAll("：", ":");
				//填开日期
				if(text.indexOf("销售方") > -1 && text.indexOf("填开日期") > -1){
					int lastIndex = text.lastIndexOf(":");
					text = text.substring(lastIndex+1, text.length())
							.replace("年", "-")
							.replace("月", "-")
							.replace("日", "");

					json.put("xhqd_tkrq", text);
					if(xsfY$ == 0f){
						xsfY$ = receiptPosition.getPosY();
					}
				}
				//备注
				if(text.indexOf("备注") > -1 && bzY$ == 0f){
					bzY$ = receiptPosition.getPosY();
				}
				//总计
				if(text.indexOf("总计") > -1 && zjY$ == 0f){
					zjY$ = receiptPosition.getPosY();
				}
				//小计
				if(text.indexOf("小计") > -1 && xjY$ == 0f){
					xjY$ = receiptPosition.getPosY();
				}
				//序号
				if(text.indexOf("序号") > -1 && xhY$ == 0f){
					xhY$ = receiptPosition.getPosY();
				}
				//发票代码
				if(text.indexOf("所属增值税电子普通发票代码") > -1 && fpdmY$ == 0f){
					//发票代码：
					json.put("xhqd_fpdm", text.substring(text.indexOf(":")+1, text.lastIndexOf("号码")));
					//发票号码：
					json.put("xhqd_fphm", text.substring(text.lastIndexOf(":")+1, text.lastIndexOf("共")));
					fpdmY$ = receiptPosition.getPosY();
				}
				//销售方名称
				if(text.indexOf("销售方名称") > -1 && xsfmcY$ == 0f){
					json.put("xhqd_xsfmc", text.substring(text.indexOf(":")+1, text.length()));
					xsfmcY$ = receiptPosition.getPosY();
				}
				//购买方名称
				if(text.indexOf("购买方名称") > -1 && gmfmcY$ == 0f){
					json.put("xhqd_gmfmc", text.substring(text.indexOf(":")+1, text.length()));
					gmfmcY$ = receiptPosition.getPosY();
				}
			}
			//记录关键字bean（eg:序号，货物名称，数量等）
			ArrayList<ReceiptPosition> beanList = new ArrayList<ReceiptPosition>();
			for (ReceiptPosition rp : addendumList) {
				if(Math.abs(rp.getPosY() - xhY$) < 2){
					beanList.add(rp);
				}
			}
			PDFSortUtil.sort(beanList, "posX");

			if(null == xh$R){
				for (ReceiptPosition rp : beanList) {
					String text = rp.getText().trim();
					if("序号".equals(text)){
						xh$R = rp;
						xh$R.setText("序号");
					}
					if("货物(劳务)名称".equals(text)){
						hwmc$R = rp;
						hwmc$R.setText("货物名称");
					}
					if("规格型号".equals(text)){
						ggxh$R = rp;
						ggxh$R.setText("规格型号");
					}
					if("单位".equals(text)){
						dw$R = rp;
						dw$R.setText("单位");
					}
					if("数量".equals(text.replaceAll(" ", ""))){
						sl$R = rp;
						sl$R.setText("数量");
					}
					if("单价".equals(text.replaceAll(" ", ""))){
						dj$R = rp;
						dj$R.setText("单价");
					}
					if("金额".equals(text.replaceAll(" ", ""))){
						je$R = rp;
						je$R.setText("金额");
					}
					if("税率".equals(text.replaceAll(" ", ""))){
						slv$R = rp;
						slv$R.setText("税率");
					}
					if("税额".equals(text.replaceAll(" ", ""))){
						se$R = rp;
						se$R.setText("税额");
					}
				}
			}
			String xjje = "";//小计金额
			String xjse = "";//小计税额
			String zjje = "";//总计金额
			String zjse = "";//总计税额
			StringBuilder bzText = new StringBuilder();//备注
			//序号
			ArrayList<ReceiptPosition> xh$List = new ArrayList<ReceiptPosition>();
			//货物名称
			ArrayList<ReceiptPosition> hwmc$List = new ArrayList<ReceiptPosition>();
			//规格型号
			ArrayList<ReceiptPosition> ggxh$List = new ArrayList<ReceiptPosition>();
			//单位
			ArrayList<ReceiptPosition> dw$List = new ArrayList<ReceiptPosition>();
			//数量
			ArrayList<ReceiptPosition> sl$List = new ArrayList<ReceiptPosition>();
			//单价
			ArrayList<ReceiptPosition> dj$List = new ArrayList<ReceiptPosition>();
			//金额
			ArrayList<ReceiptPosition> je$List = new ArrayList<ReceiptPosition>();
			//税率
			ArrayList<ReceiptPosition> slv$List = new ArrayList<ReceiptPosition>();
			//税额
			ArrayList<ReceiptPosition> se$List = new ArrayList<ReceiptPosition>();
			//备注
			ArrayList<ReceiptPosition> bz$List = new ArrayList<ReceiptPosition>();

			for (int i = addendumList.size()-1; i >= 0; i--) {

				ReceiptPosition rp = addendumList.get(i);

				String text = rp.getText().trim().replaceAll(" ", "");
				//备注
				if(rp.getPosY() < (zjY$ - 2f)
						&& rp.getPosY() > (bzY$ - 5f)
						&& rp.getPosLastX() > (xh$R.getPosLastX() + 2f)){
					bz$List.add(rp);
				}

				if(rp.getPosY() < (xjY$ + 2f) && rp.getPosY() > (zjY$ - 2f)){
					//小计金额，总计金额
					if(rp.getPosLastX() < sl$R.getPosX() && rp.getPosX() > je$R.getPosX() - 10f){
						if(StringUtils.isBlank(xjje)){
							xjje = text.replace("¥", "").replace("￥", "");
						}else{
							zjje = text.replace("¥", "").replace("￥", "");
						}
					}
					//小计税额，总计税额
					if(rp.getPosX() > je$R.getPosLastX()){
						if(StringUtils.isBlank(xjse)){
							xjse = text.replace("¥", "").replace("￥", "");
						}else{
							zjse = text.replace("¥", "").replace("￥", "");
						}
					}

				}
				if(rp.getPosY() < (xhY$ - 2f) && rp.getPosY() > (xjY$ + 2f)){
					rp.setText(text);
					if(rp.getPosLastX() < (xh$R.getPosLastX() + 2f)){
						xh$List.add(rp);
					}else if(rp.getPosLastX() < (ggxh$R.getPosX() - 8f)){
						hwmc$List.add(rp);
					}else if(rp.getPosLastX() < (dw$R.getPosX() - 3f)){
						ggxh$List.add(rp);
					}else if(rp.getPosLastX() < (sl$R.getPosX() - 10f)){
						dw$List.add(rp);
					}else if(rp.getPosLastX() < (dj$R.getPosX() - 10f)){
						sl$List.add(rp);
					}else if(rp.getPosLastX() < (je$R.getPosX() - 10f)){
						dj$List.add(rp);
					}else if(rp.getPosLastX() < (slv$R.getPosX() - 2f)){
						je$List.add(rp);
					}else if(rp.getPosLastX() < (se$R.getPosX() - 10f)){
						slv$List.add(rp);
					}else{
						se$List.add(rp);
					}
				}
			}

			//备注未做过多处理，存在问题（后续根据需求再开发）
			PDFSortUtil.sort(bz$List, "posX");
			for (ReceiptPosition bzRp : bz$List) {
				bzText.append(bzRp.getText());
			}

			json.put("xhqd_bz", bzText.toString());
			json.put("xhqd_xjje", xjje);
			json.put("xhqd_xjse", xjse);
			json.put("xhqd_zjje", zjje);
			json.put("xhqd_zjse", zjse);

			if(xh$List.size() > 0){
				JSONArray jsonArray = new JSONArray();
				JSONObject jsonObject = new JSONObject();

				for (int i = 0; i < xh$List.size(); i++) {

					ReceiptPosition xhRp = xh$List.get(i);

					String xh = xhRp.getText();
					StringBuilder hwmc = new StringBuilder();
					String ggxh = "";
					String dw = "";
					String sl = "";
					String dj = "";
					String je = "";
					String slv = "";
					String se = "";

					for (ReceiptPosition ggxhRp : ggxh$List) {
						if(Math.abs(xhRp.getPosY() - ggxhRp.getPosY()) < 2){
							ggxh = ggxhRp.getText();
						}
					}
					for (ReceiptPosition dwRp : dw$List) {
						if(Math.abs(xhRp.getPosY() - dwRp.getPosY()) < 2){
							dw = dwRp.getText();
						}
					}
					for (ReceiptPosition slRp : sl$List) {
						if(Math.abs(xhRp.getPosY() - slRp.getPosY()) < 2){
							sl = slRp.getText();
						}
					}
					for (ReceiptPosition djRp : dj$List) {
						if(Math.abs(xhRp.getPosY() - djRp.getPosY()) < 2){
							dj = djRp.getText();
						}
					}
					for (ReceiptPosition jeRp : je$List) {
						if(Math.abs(xhRp.getPosY() - jeRp.getPosY()) < 2){
							je = jeRp.getText();
						}
					}
					for (ReceiptPosition slvRp : slv$List) {
						if(Math.abs(xhRp.getPosY() - slvRp.getPosY()) < 2){
							slv = slvRp.getText();
						}
					}
					for (ReceiptPosition seRp : se$List) {
						if(Math.abs(xhRp.getPosY() - seRp.getPosY()) < 2){
							se = seRp.getText();
						}
					}

					if(i + 1 < xh$List.size()){
						ReceiptPosition xhRp2 = xh$List.get(i+1);
						for (ReceiptPosition hwmcRp : hwmc$List) {
							if(hwmcRp.getPosY() > (xhRp2.getPosY() + 2f)
									&& hwmcRp.getPosY() < (xhRp.getPosY() + 2f)){
								hwmc.append(hwmcRp.getText());
							}
						}
					}else{
						for (ReceiptPosition hwmcRp : hwmc$List) {
							if(hwmcRp.getPosY() < (xhRp.getPosY() + 2f)){
								hwmc.append(hwmcRp.getText());
							}
						}
					}
					// 序号
					jsonObject.put("xh", xh);
					// 货物名称
					jsonObject.put("hwmc", hwmc.toString());
					// 规格型号
					jsonObject.put("ggxh", ggxh);
					// 单位
					jsonObject.put("dw", dw);
					// 数量
					jsonObject.put("sl", PDFStringUtil.parseInt(sl));
					// 单价
					jsonObject.put("dj", parseDouble(dj));
					// 金额
					jsonObject.put("je", parseDouble(je));
					// 税率
					jsonObject.put("slv", PDFStringUtil.parseInt(slv.replace("%", "")));
					// 税额
					jsonObject.put("se", parseDouble(se));

					jsonArray.add(jsonObject);
					//System.out.println("xhqd_je:"+je+"\t\t-xhqd_hwmc:"+hwmc.toString());
					jsonObject = new JSONObject();
				}
				json.put("xhqd_xq", jsonArray);
			}
		}
//		System.out.println(xh$R);
//		System.out.println(hwmc$R);
//		System.out.println(ggxh$R);
//		System.out.println(dw$R);
//		System.out.println(sl$R);
//		System.out.println(dj$R);
//		System.out.println(je$R);
//		System.out.println(slv$R);
//		System.out.println(se$R);

		return json;
	}


	/**
	 * <h3>记录特殊关键字起始坐标</h3>
	 * @param list
	 */
	private static void specificDW(List<ReceiptPosition> list) {

		jqbhR = new ReceiptPosition();
		hjR = new ReceiptPosition();
		jshjR = new ReceiptPosition();
		hwmcR = new ReceiptPosition();
		ggxhR = new ReceiptPosition();
		danweiR = new ReceiptPosition();
		shuliangR = new ReceiptPosition();
		danjiaR = new ReceiptPosition();
		jineR = new ReceiptPosition();
		shuilvR = new ReceiptPosition();
		shuieR = new ReceiptPosition();
		mmqR = new ReceiptPosition();
		bzR = new ReceiptPosition();

		// listT 合计
		List<ReceiptPosition> hjList = new ArrayList<ReceiptPosition>();
		// listT 价税合计
		List<ReceiptPosition> jshjList = new ArrayList<ReceiptPosition>();
		// listT 货物或应税劳务、服务名称
		List<ReceiptPosition> hwmcList = new ArrayList<ReceiptPosition>();
		// listT 规格型号
		List<ReceiptPosition> ggxhList = new ArrayList<ReceiptPosition>();
		// listT 单位
		List<ReceiptPosition> danweiList = new ArrayList<ReceiptPosition>();
		// listT 数量
		List<ReceiptPosition> shuliangList = new ArrayList<ReceiptPosition>();
		// listT 单价
		List<ReceiptPosition> danjiaList = new ArrayList<ReceiptPosition>();
		// listT 金额
		List<ReceiptPosition> jineList = new ArrayList<ReceiptPosition>();
		// listT 税率
		List<ReceiptPosition> shuilvList = new ArrayList<ReceiptPosition>();
		// listT 税额
		List<ReceiptPosition> shuieList = new ArrayList<ReceiptPosition>();
		// listT 机器编号
		List<ReceiptPosition> jqbhList = new ArrayList<ReceiptPosition>();

		// y排序
		PDFSortUtil.sort(list, "posY");
		for (ReceiptPosition item : list) {
			if (trim(PDFStringUtil.removeSpace(item.getText())).startsWith(HJ)) {
				hjList.add(item);
			}
			if (trim(PDFStringUtil.removeSpace(item.getText())).startsWith(JSHJ)) {
				jshjList.add(item);
			}
			if (trim(PDFStringUtil.removeSpace(item.getText())).startsWith(JQBH)) {
				jqbhList.add(item);
			}
		}

		// 机器编号
		PDFSortUtil.sort(jqbhList, "posY");
		jqbhR = jqbhList.get(jqbhList.size() - 1);

		// 若数量二字独立分行
		reInspection(hjList, list, HJ, HJ_H, HJ_J);
		for (ReceiptPosition hj : hjList) {
			for (ReceiptPosition jshj : jshjList) {
				if (Math.abs(hj.getPosY() - jshj.getPosY()) < 25) {
					// 合计
					hjR.setPosX(hj.getPosX());
					hjR.setPosY(hj.getPosY());
					hjR.setText(HJ);
					// 价税合计
					jshjR.setPosX(jshj.getPosX());
					jshjR.setPosY(jshj.getPosY());
					jshjR.setText(JSHJ);
				}
			}
		}

		// x排序
		PDFSortUtil.sort(list, "posX");

		List<List<ReceiptPosition>> baseList = new ArrayList<List<ReceiptPosition>>();

		List<ReceiptPosition> indexList = new ArrayList<ReceiptPosition>();

		ReceiptPosition rp = null;

		for (int i = 1; i < list.size(); i++) {
			ReceiptPosition item = list.get(i);
			float posX = item.getPosX();

			ReceiptPosition item1 = list.get(i - 1);
			float posX1 = item1.getPosX();

			indexList.add(item1);

			if (Math.abs(posX - posX1) > 2) {
				baseList.add(indexList);
				indexList = new ArrayList<ReceiptPosition>();
			}

// TODO 第一种情况，同行情况（仅空格分开字符，并未跳行） :::第二种情况，字符跳行，待处理
			// 货物或应税劳务、服务名称
			if (trim(PDFStringUtil.removeSpace(item.getText())).startsWith(HWLWFWMC_P)) {
				rp = new ReceiptPosition();
				rp.setPosX(item.getPosX());
				rp.setPosY(item.getPosY());
				rp.setText(HWLWFWMC);
				hwmcList.add(rp);
			}
			// 规格型号
			if (trim(PDFStringUtil.removeSpace(item.getText())).startsWith(GGXH)) {
				rp = new ReceiptPosition();
				rp.setPosX(item.getPosX());
				rp.setPosY(item.getPosY());
				rp.setText(GGXH);
				ggxhList.add(rp);
			}
			// 单位
			if (trim(PDFStringUtil.removeSpace(item.getText())).startsWith(DW)) {
				rp = new ReceiptPosition();
				rp.setPosX(item.getPosX());
				rp.setPosY(item.getPosY());
				rp.setText(DW);
				danweiList.add(rp);
			}
			// 数量
			if (trim(PDFStringUtil.removeSpace(item.getText())).startsWith(SL)) {
				rp = new ReceiptPosition();
				rp.setPosX(item.getPosX());
				rp.setPosY(item.getPosY());
				rp.setText(SL);
				shuliangList.add(rp);
			}
			// 单价
			if (trim(PDFStringUtil.removeSpace(item.getText())).startsWith(DJ)) {
				rp = new ReceiptPosition();
				rp.setPosX(item.getPosX());
				rp.setPosY(item.getPosY());
				rp.setText(DJ);
				danjiaList.add(rp);
			}
			// 金额
			if (trim(PDFStringUtil.removeSpace(item.getText())).startsWith(JE)) {
				rp = new ReceiptPosition();
				rp.setPosX(item.getPosX());
				rp.setPosY(item.getPosY());
				rp.setText(JE);
				jineList.add(rp);
			}
			// 税率
			if (trim(PDFStringUtil.removeSpace(item.getText())).startsWith(SLV)) {
				rp = new ReceiptPosition();
				rp.setPosX(item.getPosX());
				rp.setPosY(item.getPosY());
				rp.setText(SLV);
				shuilvList.add(rp);
			}
			// 税额
			if (trim(PDFStringUtil.removeSpace(item.getText())).startsWith(SE)) {
				rp = new ReceiptPosition();
				rp.setPosX(item.getPosX());
				rp.setPosY(item.getPosY());
				rp.setText(SE);
				shuieList.add(rp);
			}
		}

		//抽出货物或应税劳务、服务名称（以Y坐标作为参考依据）
		if(hwmcList.size() > 0){
			hwmcR = hwmcList.get(hwmcList.size() -1);
		}
		// 规格型号
		for (ReceiptPosition ggxh : ggxhList) {
			if (Math.abs(hwmcR.getPosY() - ggxh.getPosY()) < 2) {
				ggxhR = ggxh;
			}
		}
		// 单位
		reInspection(danweiList, list, DW, DW_D, DW_W);
		for (ReceiptPosition dw : danweiList) {
			if (Math.abs(hwmcR.getPosY() - dw.getPosY()) < 2) {
				danweiR = dw;
			}
		}

		// 数量
		reInspection(shuliangList, list, SL, SL_S, SL_L);
		for (ReceiptPosition sl : shuliangList) {
			if (Math.abs(hwmcR.getPosY() - sl.getPosY()) < 2) {
				shuliangR = sl;
			}
		}

		// 单价
		reInspection(danjiaList, list, DJ, DJ_D, DJ_J);
		for (ReceiptPosition dj : danjiaList) {
			if (Math.abs(hwmcR.getPosY() - dj.getPosY()) < 2) {
				danjiaR = dj;
			}
		}

		// 金额
		reInspection(jineList, list, JE, JE_J, JE_E);
		for (ReceiptPosition x : jineList) {
			if (Math.abs(hwmcR.getPosY() - x.getPosY()) < 2) {
				jineR = x;
			}
		}

		// 税率
		reInspection(shuilvList, list, SLV, SLV_S, SLV_LV);
		for (ReceiptPosition x : shuilvList) {
			if (Math.abs(hwmcR.getPosY() - x.getPosY()) < 2) {
				shuilvR = x;
			}
		}
		// 税额
		reInspection(shuieList, list, SE, SE_S, SE_E);
		for (ReceiptPosition x : shuieList) {
			if (Math.abs(hwmcR.getPosY() - x.getPosY()) < 2) {
				shuieR = x;
			}
		}

		for (List<ReceiptPosition> base : baseList) {

			PDFSortUtil.sort(base, "posY");

			String t = "";

			for (int i = base.size() - 1; i >= 0; i--) {
				ReceiptPosition item = base.get(i);

				t += item.getText();
			}

			// 密码区备注（备注和密码区X坐标相同）
			if (trim(PDFStringUtil.removeSpace(t)).startsWith(MMQ)
					|| trim(PDFStringUtil.removeSpace(t)).equals(MMQ_BZ)) {
				// 密码区
				mmqR.setText(MMQ);
				mmqR.setPosX(base.get(0).getPosX());
				mmqR.setPosEndX(base.get(0).getPosLastX());
				mmqR.setPosY(base.get(base.size() - 1).getPosY());
				// 备注
				bzR.setText(BZ);
				bzR.setPosX(base.get(0).getPosX());
				bzR.setPosEndX(base.get(0).getPosLastX());
				bzR.setPosEndY(base.get(0).getPosLastY());
			}
		}
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		System.out.println("[合计：" + hjR + "]");
//		System.out.println("[价税合计：" + jshjR + "]");
//		System.out.println("[货物名称：" + hwmcR + "]");
//		System.out.println("[规格型号：" + ggxhR + "]");
//		System.out.println("[单位：" + danweiR + "]");
//		System.out.println("[数量：" + shuliangR + "]");
//		System.out.println("[单价：" + danjiaR + "]");
//		System.out.println("[金额：" + jineR + "]");
//		System.out.println("[税率：" + shuilvR + "]");
//		System.out.println("[税额：" + shuieR + "]");
//		System.out.println("[密码区：" + mmqR + "]");
//		System.out.println("[备注：" + bzR + "]");
//		System.out.println("[机器编号：" + jqbhR + "]");
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}

	/**
	 * <p>判断相连二字分行--重检</p>
	 * @param cjList
	 * @param list
	 * @param cj
	 * @param cj_c
	 * @param cj_j
	 */
	private static void reInspection(List<ReceiptPosition> cjList,
									 List<ReceiptPosition> list, String cj, String cj_c, String cj_j) {
		if(cjList.size() < 1){
			PDFSortUtil.sort(list, "posY");
			for (int i = 0; i < list.size(); i++) {
				if(trim(list.get(i).getText()).length() == 1
						&& list.get(i).getText().startsWith(cj_c)
						&& i - 1 >= 0
						&& trim(list.get(i - 1).getText()).length() == 1
						&& list.get(i - 1).getText().startsWith(cj_j)
						||
						trim(list.get(i).getText()).length() == 1
								&& list.get(i).getText().startsWith(cj_c)
								&& i + 1 < list.size()
								&& trim(list.get(i + 1).getText()).length() == 1
								&& list.get(i + 1).getText().startsWith(cj_j)){
					list.get(i).setText(cj);
					cjList.add(list.get(i));
				}
			}
		}
	}

	/**
	 * <h2>行化处理，提取数据</h2>
	 * @param list
	 * @return
	 */
	private static JSONObject reorganizationRegulation(List<ReceiptPosition> list) {

		JSONObject jsonObject = new JSONObject();

		// fplx： 发票类型 1:增值税专票，4: 增值税普通发票，10: 增值税普通发票(电子版)，11: 增值税普通发票(卷式发票)
		jsonObject.put("fplx", ReceiptTaxFlg.PUTONG_DIANZI);

		// fpdm： 发票代码 String
		String fpdm = "";
		// fphm： 发票号码 String
		String fphm = "";
		// kprq： 开票日期 String
		String kprq = "";
		// jym： 校验码 String
		String jym = "";
		// spfmc： 收票方名称 String
		String spfmc = "";
		// spfsbh： 收票方识别号 String
		String spfsbh = "";
		// spfdzdh： 收票方地址及电话 String
		String spfdzdh = "";
		// spfyhzh： 收票方银行及账号 String
		String spfyhzh = "";
		// kpfmc： 开票方名称 String
		String kpfmc = "";
		// kpfsbh： 开票方识别号 String
		String kpfsbh = "";
		// kpfdzdh： 开票方地址及电话 String
		String kpfdzdh = "";
		// kpfyhzh： 开票方银行及账号 String
		String kpfyhzh = "";
		// hjje： 合计金额 BigDecimal
		String hjje = "";
		// hjse： 合计税额 BigDecimal
		String hjse = "";
		// kpje： 价税合计 BigDecimal
		String kpje = "";
		// kpjecn： 价税合计(大写) String
		String kpjecn = "";
		// slv： 税率 int
		String slv = "";
		// hwmc： 货物名称 String
		String hwmc = "";
		// jqbh： 机器编号 String
		String jqbh = "";

		// skr： 收款人 String
		String skr = "";
		// fh： 复核 String
		String fh = "";
		// kpr： 开票人 String
		String kpr = "";
		// xsf：销售方String
		String xsf = "";

		//存储收款人，复核，开票人
		List<ReceiptPosition> sfkList = new ArrayList<ReceiptPosition>();

		// 备份同行所有字符集合
		List<List<ReceiptPosition>> baseList = new ArrayList<List<ReceiptPosition>>();

		// 每行数据集合
		List<ReceiptPosition> lineList = new ArrayList<ReceiptPosition>();

		// y排序
		PDFSortUtil.sort(list, "posY");

		// 备份集合
		List<ReceiptPosition> indexList = new ArrayList<ReceiptPosition>();
		for (int i = list.size() - 1; i > 0; i--) {
			ReceiptPosition item = list.get(i);
			float posY = item.getPosY();

			ReceiptPosition item1 = list.get(i - 1);
			float posY1 = item1.getPosY();

			indexList.add(item);
			if (Math.abs(posY - posY1) > 2) {

				baseList.add(indexList);

				indexList = new ArrayList<ReceiptPosition>();
			}
//TODO 不够严谨，待处理。。。
			//收款人，复核，开票人
			if(posY1 < bzR.getPosEndY() - 10f){
				sfkList.add(item1);
			}
		}
		//收款人，复核，开票人集合进行x排序
		PDFSortUtil.sort(sfkList, "posX");
		String sfkText = "";
		for (ReceiptPosition sfk : sfkList) {
			sfkText += sfk.getText();
		}

		String regex = "收款人|复核|开票人|销售方:(章)";
		Pattern pattern = Pattern.compile(regex);
		String[] splitStrs = pattern.split(sfkText.trim());
		if(splitStrs.length > 4){
			skr = splitStrs[1];
			fh = splitStrs[2];
			kpr = splitStrs[3];
			xsf = splitStrs[4];
		}
		for (List<ReceiptPosition> itemList : baseList) {

			PDFSortUtil.sort(itemList, "posX");

			ReceiptPosition r = new ReceiptPosition();

			String text = "";

			for (ReceiptPosition item : itemList) {
				// 排除密码区
				if (item.getPosY() < (jqbhR.getPosY() - 5f)
						&& item.getPosY() > hwmcR.getPosY()
						&& item.getPosX() > mmqR.getPosX()) {
					continue;
				}
				// 排除备注区
				if (item.getPosY() < jshjR.getPosY()
						&& item.getPosX() > bzR.getPosX()) {
					continue;
				}
				// 单独抽出货物名称
				if (item.getPosY() < hwmcR.getPosY()
						&& item.getPosY() > hjR.getPosY()
						&& item.getPosX() > ggxhR.getPosX()) {
					continue;
				}

				text += item.getText();
			}
			text = PDFStringUtil.removeSpace(text);
			r.setText(text);

			// 记录最后拼接字符串的y数据作为endY
			r.setPosY(itemList.get(0).getPosY());
			r.setPosEndY(itemList.get(itemList.size() - 1).getPosY());

			lineList.add(r);

		}

		// 货物名称集合
		List<String> hwmcL = new ArrayList<String>();
		// 数量集合
		List<String> slL = new ArrayList<String>();
		// 单价集合
		List<String> djL = new ArrayList<String>();
		// 单项金额集合
		List<String> dxjeL = new ArrayList<String>();
		// 税率集合
		List<String> slvL = new ArrayList<String>();
		// 单项税额集合
		List<String> dxseL = new ArrayList<String>();
		// 税率Y轴坐标集合（带折扣税率）//作为货物名称多项的判断依据
		List<Float> agioYList = new ArrayList<Float>();

		// 合计金额 税额 税率 单独抽取---补充
		for (ReceiptPosition item : list) {
			// 合计金额
			if (item.getPosY() > (hjR.getPosY() - 5f)
					&& item.getPosY() < jineR.getPosY()
					&& item.getPosX() > (jineR.getPosX() - 20f)
					&& item.getPosX() < shuilvR.getPosX()) {
				String text = PDFStringUtil.removeSpace(item.getText());
				if (StringUtils.isNotBlank(text) && StringUtils.isBlank(hjje)) {
					hjje = trim(text.replaceAll("¥", "").replaceAll("￥", ""));
				}
			}
			// 税率
			if (item.getPosY() > hjR.getPosY()
					&& item.getPosY() < shuilvR.getPosY()
					&& item.getPosX() > (shuilvR.getPosX() - 5f)
					&& item.getPosX() < (shuilvR.getPosX() + 20f)) {
				String text = PDFStringUtil.removeSpace(item.getText());
				if (StringUtils.isNotBlank(text)) {
					slv = text;
				}
				agioYList.add(item.getPosY());
				slvL.add(PDFStringUtil.removeSpace(item.getText()));
			}
			// 合计税额
			if (item.getPosY() > (hjR.getPosY() - 5f)
					&& item.getPosY() < shuieR.getPosY()
					&& item.getPosX() > (shuieR.getPosX() - 20f)) {
				String text = PDFStringUtil.removeSpace(item.getText());
				if (StringUtils.isNotBlank(text) && StringUtils.isBlank(hjse)) {
					hjse = trim(text.replaceAll("¥", "").replaceAll("￥", ""));
				}
			}
		}
		// TODO 存在多项的情况，单项货物名称集合，数量集合，单价 ，金额，税额集合（税率集合作为参照）--list按Y轴升序

		//免税情况判断
		if(agioYList.size() < 1){
			for (ReceiptPosition item : list) {
				// 合计金额
				if (item.getPosY() > (hjR.getPosY() + 10f)
						&& item.getPosY() < jineR.getPosY()
						&& item.getPosX() > (jineR.getPosX() - 20f)
						&& item.getPosX() < shuilvR.getPosX()) {
					agioYList.add(item.getPosY());
				}
			}
		}
		for (int i = 0; i < agioYList.size(); i++) {
			Float indexY = agioYList.get(i);
			Float indexY0 = indexY;
			if (i > 0) {
				indexY0 = agioYList.get(i - 1);
			}
			String dxhwmc$ = "";// 货物名称
			String sl$ = "";// 数量
			String dj$ = "";// 单价
			String dxje$ = "";// 金额
			String dxse$ = "";// 税额
			for (int j = list.size() - 1; j > 0; j--) {
				float posY = list.get(j).getPosY();
				float posX = list.get(j).getPosX();
				String text = PDFStringUtil.removeSpace(list.get(j).getText());
				if (posY < (agioYList.get(agioYList.size() - 1) + 2f)
						&& posY > (hjR.getPosY() + 2f)) {
					// 单项货物名称集合
					if (posX < (ggxhR.getPosX() - 5f)) {
						if (i == 0) {
							if (posY < (indexY + 2f)
									&& posY > (hjR.getPosY() + 2f)) {
								dxhwmc$ += text;
							}
						} else {
							if (posY > indexY0 && posY < (indexY + 2f)) {
								dxhwmc$ += text;
							}
						}
					}
					// 数量集合
					if (Math.abs(posY - indexY) < 2
							&& posX < danjiaR.getPosX() - 20f
							&& posX > shuliangR.getPosX() - 10f) {
						sl$ = text;
					}
					// 单价集合
					if (Math.abs(posY - indexY) < 2
							&& posX < jineR.getPosX() - 20f
							&& posX > danjiaR.getPosX() - 20f) {
						dj$ = text;
					}
					// 单项金额
					if (Math.abs(posY - indexY) < 2
							&& posX < shuilvR.getPosX() - 5f
							&& posX > jineR.getPosX() - 20f) {
						dxje$ = text;
					}
					// 单项税额
					if (Math.abs(posY - indexY) < 2
							&& posX > shuieR.getPosX() - 20f) {
						dxse$ = text;
					}
				}
			}
			hwmcL.add(dxhwmc$);
			slL.add(sl$);
			djL.add(dj$);
			dxjeL.add(dxje$);
			dxseL.add(dxse$);
		}

		for (ReceiptPosition rec : lineList) {

			String text = rec.getText();

			if (rec.getPosY() > hwmcR.getPosY()) {
				// 机器编号
				int jqbhIndex = rec.getText().indexOf(JQBH);
				if (jqbhIndex > -1) {
					jqbh = text.substring(jqbhIndex + 5, jqbhIndex + 17);
				}
				// 发票代码
				int fpdmIndex = rec.getText().indexOf(FPDM);
				if (fpdmIndex > -1) {
					fpdm = text.substring(fpdmIndex + 5, text.length());
				}
				// 发票号码
				int fphmIndex = rec.getText().indexOf(FPHM);
				if (fphmIndex > -1) {
					fphm = text.substring(fphmIndex + 5, text.length());
				}
				// 开票日期
				int kprqIndex = rec.getText().indexOf(KPRQ);
				if (kprqIndex > -1) {
					String rqX = text.substring(kprqIndex + 5, text.length());
					String rq = rqX.replaceAll("年", "").replaceAll("月", "")
							.replaceAll("日", "");
					kprq = rq.substring(0, 4) + "-" + rq.substring(4, 6) + "-"
							+ rq.substring(6, 8);
				}
				// 校验码
				int jymIndex = rec.getText().indexOf(JYM);
				if (jymIndex > -1) {
					jym = text.substring(jymIndex + 4, text.length());
				}
			}
			// 货物名称
			if (rec.getPosY() < hwmcR.getPosY()
					&& rec.getPosY() > hjR.getPosY()
					&& rec.getPosX() < ggxhR.getPosX()) {
				// 判断货物名称中是否有多项？判断依据：税率是否有多条
				if (agioYList.size() > 1
						&& rec.getPosY() <= agioYList.get(agioYList.size() - 2)) {
					continue;
				}
				hwmc += text;
			}
			if (rec.getPosY() > hwmcR.getPosY()
					&& rec.getPosY() < jqbhR.getPosY()) {
				// spfmc：收票方名称 String
				int spfmcIndex = rec.getText().indexOf(MC);
				if (spfmcIndex > -1) {
					spfmc = text.substring(spfmcIndex + 3, text.length());
				}
				// spfsbh： 收票方识别号 String
				int spfsbhIndex = rec.getText().indexOf(NSRSBH);
				if (spfsbhIndex > -1) {
					spfsbh = text.substring(spfsbhIndex + 7, text.length());
				}
				// spfdzdh： 收票方地址及电话 String
				int spfdzdhIndex = rec.getText().indexOf(DZDH);
				if (spfdzdhIndex > -1) {
					spfdzdh = text.substring(spfdzdhIndex + 6, text.length());
				}
				// spfyhzh： 收票方银行及账号 String
				int spfyhzhIndex = rec.getText().indexOf(KHHJZH);
				if (spfyhzhIndex > -1) {
					spfyhzh = text.substring(spfyhzhIndex + 7, text.length());
				}
			}

			if (rec.getPosY() < jshjR.getPosY()) {
				// kpfmc： 开票方名称 String
				int kpfmcIndex = rec.getText().indexOf(MC);
				if (kpfmcIndex > -1) {
					kpfmc = text.substring(kpfmcIndex + 3, text.length());
				}
				// kpfsbh： 开票方识别号 String
				int kpfsbhIndex = rec.getText().indexOf(NSRSBH);
				if (kpfsbhIndex > -1) {
					kpfsbh = text.substring(kpfsbhIndex + 7, text.length());
				}
				// kpfdzdh： 开票方地址及电话 String
				int kpfdzdhIndex = rec.getText().indexOf(DZDH);
				if (kpfdzdhIndex > -1) {
					kpfdzdh = text.substring(kpfdzdhIndex + 6, text.length());
				}
				// kpfyhzh： 开票方银行及账号 String
				int kpfyhzhIndex = rec.getText().indexOf(KHHJZH);
				if (kpfyhzhIndex > -1) {
					kpfyhzh = text.substring(kpfyhzhIndex + 7, text.length());
				}
			}
			// 价税合计
			if (Math.abs(rec.getPosY() - jshjR.getPosY()) < 2) {
				// 价税合计(大写)捌拾捌圆壹角(小写)¥88.10

				kpje = text.split("小写")[1]
						.replace("¥", "")
						.replace("￥", "");
				kpje = kpje.substring(1, kpje.length());

				kpjecn = text.substring(text.indexOf("大写") + 3, text.lastIndexOf("小写") - 1);
				kpjecn = kpjecn.replace("ⓧ", "");
				// System.out.println(kpjecn);
			}
		}

		// 判断价税合计是否跳行
		if (!PDFNumberValidationUtils.isRealNumber(kpje)) {
			for (ReceiptPosition item : list) {
				if (Math.abs(item.getPosY() - jshjR.getPosY()) < 2) {
					kpje += item.getText();
				}
			}
		}
		// 若价税合计金额数字跳行，则抽取价税合计中的金额数字
		if (!PDFNumberValidationUtils.isRealNumber(kpje)) {
			// 肆拾伍圆整45.00价税合计(大写)(小写)
			kpje = extractM(kpje);
		}

		// fpdm： 发票代码 String
		jsonObject.put("fpdm", trim(fpdm));
		// fphm： 发票号码 String
		jsonObject.put("fphm", trim(fphm));
		// kprq： 开票日期 String
		jsonObject.put("kprq", trim(kprq));
		// jym： 校验码 String
		jsonObject.put("jym", trim(jym));
		// spfmc： 收票方名称 String
		jsonObject.put("spfmc", trim(spfmc));
		// spfsbh： 收票方识别号 String
		jsonObject.put("spfsbh", trim(spfsbh));
		// spfdzdh： 收票方地址及电话 String
		jsonObject.put("spfdzdh", trim(spfdzdh));
		// spfyhzh： 收票方银行及账号 String
		jsonObject.put("spfyhzh", trim(spfyhzh));
		// kpfmc： 开票方名称 String
		jsonObject.put("kpfmc", trim(kpfmc));
		// kpfsbh： 开票方识别号 String
		jsonObject.put("kpfsbh", trim(kpfsbh));
		// kpfdzdh： 开票方地址及电话 String
		jsonObject.put("kpfdzdh", trim(kpfdzdh));
		// kpfyhzh： 开票方银行及账号 String
		jsonObject.put("kpfyhzh", trim(kpfyhzh));
		// hjje： 合计金额 BigDecimal
		jsonObject.put("hjje", parseDouble(trim(hjje)));
		// hjse： 合计税额 BigDecimal
		jsonObject.put("hjse", parseDouble(trim(hjse)));
		// kpje： 价税合计 BigDecimal
		jsonObject.put("kpje", parseDouble(trim(kpje)));
		// kpjecn： 价税合计(大写) String
		jsonObject.put("kpjecn", trim(kpjecn));
		// slv： 税率 int
		slv = trim(slv);
		slv = slv.replace("%", "");
		slv = String.valueOf(PDFStringUtil.parseInt(slv));
		jsonObject.put("slv", slv);
		// hwmc： 货物名称 String
		jsonObject.put("hwmc", trim(hwmc));
		// jqbh： 机器编号 String
		jsonObject.put("jqbh", trim(jqbh));
		// skr： 收款人String
		jsonObject.put("skr", trim(skr));
		// fh：复核String
		jsonObject.put("fh", trim(fh));
		// kpr：开票人String
		jsonObject.put("kpr", trim(kpr));
		// xsf：销售方String
		jsonObject.put("xsf", trim(xsf));

		// 逆序：
		Collections.reverse(hwmcL);
		Collections.reverse(slL);
		Collections.reverse(djL);
		Collections.reverse(dxjeL);
		Collections.reverse(slvL);
		Collections.reverse(dxseL);

//		// hwmcList： 单项货物名称集合List<String>;
//		jsonObject.put("hwmcList", hwmcL);
//		// slList： 数量集合List<String>
//		jsonObject.put("slList", slL);
//		// djList： 单价集合List<String>
//		jsonObject.put("djList", djL);
//		// dxjeList： 单项金额集合List<String>
//		jsonObject.put("dxjeList", dxjeL);
//		// slvList： 税率集合List<String>
//		jsonObject.put("slvList", slvL);
//		// dxseList： 单项税额集合List<String>
//		jsonObject.put("dxseList", dxseL);

		JSONArray goodsArray = new JSONArray();
		for (int i = 0; i < hwmcL.size(); i++) {
			int hh = i+1;
			JSONObject goodsObject = new JSONObject();
			//	hh：		行号			Integer
			goodsObject.put("hh", hh);
			//	hwmc：	货物或应税劳务名称	String
			goodsObject.put("hwmc", hwmcL.get(i));
			//	ggxh：	规格型号		String
			goodsObject.put("ggxh", "");
			//	dw：		单位			String
			goodsObject.put("dw", "");
			//	sl：		数量			Double
			goodsObject.put("sl", i > (slL.size()-1) ? "" : PDFStringUtil.parseInt(slL.get(i)));
			//	dj：		单价			BigDecimal
			goodsObject.put("dj", i > (djL.size()-1) ? "" : parseDouble(djL.get(i)));
			//	je：		金额			BigDecimal
			goodsObject.put("je", i > (dxjeL.size()-1) ? "" : parseDouble(dxjeL.get(i)));
			//	slv：	税率			Double
			goodsObject.put("slv", i > (slvL.size()-1) ? "" : PDFStringUtil.parseInt(slvL.get(i).replace("%", "")));
			//	se：		税额			BigDecimal
			goodsObject.put("se", i > (dxseL.size()-1) ? "" : parseDouble(dxseL.get(i)));
			goodsArray.add(goodsObject);
		}
		jsonObject.put("hwmxs", goodsArray);

//		System.out.println(jsonObject.toJSONString());
//		System.out.println(jsonObject.get("hwmcList"));
//		System.out.println(jsonObject.get("slvList"));
//		System.out.println(jsonObject.get("hwmxs"));

		return jsonObject;
	}

	/**
	 * <h4>抽取字符串中的金额数字</h4>
	 * @param parm
	 * @return
	 */
	private static String extractM(String parm) {
		Pattern p = Pattern.compile("[0-9\\.]+");
		Matcher m = p.matcher(parm);

		String str = "";

		while (m.find()) {
			str += m.group();
		}
		return str;
	}

	private static Double parseDouble(String text) {
		try {
			String num = extractM(text);
			if (StringUtils.isNotBlank(num)) {
				return Double.parseDouble(num);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0.0D;
	}

}
