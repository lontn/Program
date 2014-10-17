package org.fcu.green.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fcu.green.crawler.domain.TextBookInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IntechopenCrawler implements Crawler {
    private static final Logger L = LogManager.getLogger(IntechopenCrawler.class);
    private static final String WEBSITE = "http://www.intechopen.com";
    private volatile boolean start;

    private void crawlerWebSite() {
        int pages = getPageCount();
        for (int i = 1; i <= pages; i++) {
            List<String> booksList = getBooksList(i);
            List<TextBookInfo> bookInfoList = new ArrayList<TextBookInfo>();
            for (String url : booksList) {
                bookInfoList.add(getTextBookInfo(url));
                //寫入Excel
            }
        }
    }
    
    /**
     * 撈頁數
     * @return
     */
    private int getPageCount(){
        int page = 0;
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.intechopen.com/books/latest/1/list").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements pagination = doc.getElementsByClass("pagination");
        for (Element element : pagination) {
            Elements li = element.getElementsByTag("li");
            page = Integer.parseInt(li.get(li.size()-2).text());
        }
        return page;
    }
    
    private TextBookInfo getTextBookInfo(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            L.error("TextBookInfo Err:{}", e);
        }
        Elements content = doc.getElementsByClass("main-content");
        TextBookInfo book = new TextBookInfo();
        for (Element element : content) {
            Elements hh = element.getElementsByTag("h1");
            book.setSubjectClassification(hh.get(0).text());
            book.setTitle(hh.get(1).text());
            Elements p =element.getElementsByTag("p");
            String[] contentInfo = p.get(0).text().split(",");
            book.setCreator(contentInfo[0].replaceAll("Edited by", "").trim());
            book.setIdentifier(contentInfo[1].replaceAll("ISBN", "").trim());
            book.setPublisher(contentInfo[3].replaceAll("Publisher:", "").trim());
            String[] word = contentInfo[5].split(" ");
            book.setYear(word[1]);
            book.setRightUri(word[3]+ " " + word[4] + " " + word[5] + " " + word[6]);
            book.setSyshyperlink(url);
            book.setDoi(word[8]);
            book.setIntroduction(p.get(1).text());
        }
        return book;
    }
    
    private List<String> getBooksList(int page){
        List<String> detailUrlList = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.intechopen.com/books/latest/"+page+"/list").get();
        } catch (IOException e) {
            L.error("BooksList Err:{}", e);
        }
        Elements content = doc.getElementsByClass("main-content");
        for (Element element : content) {
            Elements dtTag = element.getElementsByTag("dt");
            for (Element element2 : dtTag) {
                Elements url = element2.getElementsByTag("a");
                detailUrlList.add(WEBSITE+url.attr("href"));
                System.out.println(url.attr("href"));
            }
        }
        return detailUrlList;
    }
    @Override
    public void start() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

}
