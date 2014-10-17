package crawler.fcu;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class CrawlerTest {
    private static final Logger L = LogManager.getLogger(CrawlerTest.class);

    @Test
    public void test() {
        String html = "<li><dl>"+
                      "<dt><a href=\"/books/essentials-and-controversies-in-bariatric-surgery\" title=\"Essentials and Controversies in Bariatric Surgery\">Essentials and Controversies in Bariatric Surgery</a></dt>"+
                      "<dd class=\"thumbnail\">"+
                      "<a href=\"/books/essentials-and-controversies-in-bariatric-surgery\"><img src=\"http://cdn.intechopen.com/books/images/3417.jpg\" alt=\"Essentials and Controversies in Bariatric Surgery\" title=\"Essentials and Controversies in Bariatric Surgery\" width=\"130\" height=\"185\" /></a><strong class=\"impact-factor\">NEW</strong>"+
                      "</dd>"+
                      "<dd class=\"meta\">Editor  Chih-Kun Huang <br/>" +
                      "ISBN 978-953-51-1726-1<br/>"+
                      "152 pages, October, 2014<br/>"+
                      "<img src=\"http://cdn.intechopen.com/public/images/oa/OA_Book-button.png\" style=\"float: right; margin-top: 6px;\">"+
                      "</dd>"+
                      "</dl>"+
                      "<dd class=\"abstract\">Bariatric surgery has been proved to be clinically effective and economically viable for obese people when compared to non-surgical interventions. Advancement of minimally invasive surgery in the last 20 years has made the safety and reliability wide ...</dd>"+
                      "</li>"+
                      "<li><dl>"+
                      "<dt><a href=\"/books/essentials-and-controversies-in-bariatric-surgery\" title=\"Essentials and Controversies in Bariatric Surgery\">Essentials and Controversies in Bariatric Surgery</a></dt>"+
                      "<dd class=\"thumbnail\">"+
                      "<a href=\"/books/essentials-and-controversies-in-bariatric-surgery\"><img src=\"http://cdn.intechopen.com/books/images/3417.jpg\" alt=\"Essentials and Controversies in Bariatric Surgery\" title=\"Essentials and Controversies in Bariatric Surgery\" width=\"130\" height=\"185\" /></a><strong class=\"impact-factor\">NEW</strong>"+
                      "</dd>"+
                      "<dd class=\"meta\">Editor  Chih-Kun Huang <br/>" +
                      "ISBN 978-953-51-1726-1<br/>"+
                      "152 pages, October, 2014<br/>"+
                      "<img src=\"http://cdn.intechopen.com/public/images/oa/OA_Book-button.png\" style=\"float: right; margin-top: 6px;\">"+
                      "</dd>"+
                      "</dl>"+
                      "<dd class=\"abstract\">Bariatric surgery has been proved to be clinically effective and economically viable for obese people when compared to non-surgical interventions. Advancement of minimally invasive surgery in the last 20 years has made the safety and reliability wide ...</dd>"+
                      "</li>";
        Document doc = Jsoup.parse(html);
        Elements dtTag = doc.getElementsByTag("dt");
        for (Element element : dtTag) {
            String tag = element.tagName();
            if(tag.equals("dt")){
                Elements href = element.getElementsByTag("a");
                //取href url
                System.out.println(href.attr("href"));
                Elements urlselect = href.select("a[href]");
                System.out.println(urlselect.text());
                Elements ss = element.getElementsByAttribute("href");
                System.out.println(ss.text());
            }
        }
    }
    
    //Detail Page
    @Test
    public void test2() {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.intechopen.com/books/matlab-applications-for-the-practical-engineer").get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Elements content = doc.getElementsByClass("main-content");
        for (Element element : content) {
            Elements hh = element.getElementsByTag("h1");
            System.out.println("subject:classification:"+hh.get(0).text());
            System.out.println("title:"+hh.get(1).text());
            Elements p =element.getElementsByTag("p");
            String[] contentInfo = p.get(0).text().split(",");
            System.out.println("Creater:"+contentInfo[0].replaceAll("Edited by", "").trim());
            System.out.println("ISBN:"+contentInfo[1].replaceAll("ISBN", "").trim());
            System.out.println("Publisher:"+contentInfo[3].replaceAll("Publisher:", "").trim());
            String[] word = contentInfo[5].split(" ");
            System.out.println("year:"+word[1]);
            System.out.println("right:uri:"+word[3]+ " " + word[4] + " " + word[5] + " " + word[6]);
            System.out.println("DOI:"+word[8]);
            System.out.println("abstract:"+p.get(1).text());
//            for (Element element2 : p) {
//                System.out.println("content:"+element2.text());
//            }
            String p1 = element.getElementsByTag("p").text();
            //System.out.println("p1:"+p1);
        }
    }
    
    @Test
    public void test3() {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.intechopen.com/books/latest/1/list").get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Elements content = doc.getElementsByClass("main-content");
        System.out.println("AAaaa:"+content.text());
        for (Element element : content) {
            Elements dtTag = element.getElementsByTag("dt");
            System.out.println("AA");
            for (Element element2 : dtTag) {
                Elements url = element2.getElementsByTag("a");
                L.info("http://www.intechopen.com"+url.attr("href"));
            }
        }
    }
    
    @Test
    public void page() {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.intechopen.com/books/latest/1/list").get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Elements pagination = doc.getElementsByClass("pagination");
        for (Element element : pagination) {
            Elements li = element.getElementsByTag("li");
            System.out.println(li.get(li.size()-2).text());
            for (Element element2 : li) {
                System.out.println(element2.text());
            }
        }
    }
}
