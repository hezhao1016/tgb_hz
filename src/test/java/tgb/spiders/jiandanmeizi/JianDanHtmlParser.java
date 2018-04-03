package tgb.spiders.jiandanmeizi;

import java.util.ArrayList;
import java.util.List;

public class JianDanHtmlParser implements Runnable {
	private String html;
	private int page;

	public JianDanHtmlParser(String html, int page) {
		this.html = html;
		this.page = page;
	}

	@Override
	public void run() {
		System.out.println("==========第" + page + "页============");
		List<String> list = new ArrayList<String>();
		html = html.substring(html.indexOf("commentlist"));
		String[] images = html.split("li>");
		for (String image : images) {
			String[] ss = image.split("br");
			for (String s : ss) {
				if (s.indexOf("<img src=") > 0) {
					try {
						int i = s.indexOf("<img src=\"")
								+ "<img src=\"".length();

						String imageUrl = s
								.substring(i, s.indexOf("\"", i + 1));
						if (!"http".equalsIgnoreCase(imageUrl.substring(0, 4))) {
							if ("//".equalsIgnoreCase(imageUrl.substring(0, 2))) {
								imageUrl = "http:" + imageUrl;
							} else {
								imageUrl = "http://" + imageUrl;
							}
						}

						list.add(imageUrl);
					} catch (Exception e) {
						System.out.println(s);
					}

				}
			}
		}
		for (String imageUrl : list) {
			if (imageUrl.indexOf("sina") > 0) {
				new Thread(new JianDanImageCreator(imageUrl, page)).start();
			}
		}
	}
}
