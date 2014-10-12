package crawler.fcu;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class CrawlerTest {

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
    
    @Test
    public void test2() {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.intechopen.com/books/essentials-and-controversies-in-bariatric-surgery").get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Elements content = doc.getElementsByClass("main-content");
        for (Element element : content) {
            String h1 = element.getElementsByTag("h1").text();
            System.out.println("h1:"+h1);
            Elements p =element.getElementsByTag("p");
            for (Element element2 : p) {
                System.out.println("element2:"+element2.text());
            }
            String p1 = element.getElementsByTag("p").text();
            //System.out.println("p1:"+p1);
        }
        Elements subtitle = doc.getElementsByClass("subtitle");
        //System.out.println(subtitle.text());
    }
}
