package crawler.fcu;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fcu.green.crawler.IntechopenCrawler;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
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
            //doc = Jsoup.connect("http://www.intechopen.com/books/mitigation-of-ionospheric-threats-to-gnss-an-appraisal-of-the-scientific-and-technological-outputs-of-the-transmit-project").get();
            doc = Jsoup.connect("http://www.intechopen.com/books/development_and_implementation_of_rfid_technology").get();
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
            String contentTest = p.get(0).text();
            int a = contentTest.indexOf("Edited by");
            int b = contentTest.indexOf(", ISBN");
            String creater = (String) contentTest.subSequence(a, b);
            creater = creater.replaceAll("Edited by", "");
            int a1 = contentTest.indexOf("ISBN");
            int b1 = contentTest.indexOf(", Publisher:");
            String isbn = (String) contentTest.subSequence(a1, b1);
            isbn = isbn.replaceAll("ISBN", "");
            String[] isbnSplit = isbn.split(",");
            int a2 = contentTest.indexOf("Publisher:");
            int b2 = contentTest.indexOf(", Chapters");
            String publisher = (String) contentTest.subSequence(a2, b2);
            publisher = publisher.replaceAll("Publisher:", "");
            int a3 = contentTest.indexOf("Chapters published");
            int b3 = contentTest.indexOf("under");
            String year = (String) contentTest.subSequence(a3, b3);
            year = year.replaceAll("Chapters published", "").trim();
            String[] yearSplit = year.split(",");
            System.out.println("year:" + yearSplit[1].trim());
            int a4 = contentTest.indexOf("under");
            int b4 = contentTest.indexOf("DOI:");
            String right = contentTest.substring(a4, b4);
            right = right.replaceAll("under", "");
            int a5 = contentTest.indexOf("DOI:");
            int b5 = contentTest.length();
            String doi = contentTest.substring(a5, b5);
            doi = doi.replaceAll("DOI:", "");
            System.out.println("creater:"+creater.trim());
            System.out.println("isbn:"+isbnSplit[0].trim());
            System.out.println("publisher:"+publisher);
            System.out.println("right:"+right.trim());
            System.out.println("doi:"+doi.trim());
            if (p.size() > 1){
                System.out.println("abstract:"+p.get(1).text());
            }
            
            
//            String[] contentInfo = p.get(0).text().split(",");
//            System.out.println("contentInfo:"+contentInfo.length);
//            for (String string : contentInfo) {
//                System.out.println("string:"+string);
//            }
//            System.out.println("Creater:"+p.get(0).getElementsByTag("a").get(0).text().trim());
//            if (p.get(0).getElementsByTag("a").size() > 1) {
//                System.out.println("right:uri:"+p.get(0).getElementsByTag("a").get(1).text().trim());
//            }
//            System.out.println("ISBN:"+contentInfo[1].replaceAll("ISBN", "").trim());
//            System.out.println("Publisher:"+contentInfo[3].replaceAll("Publisher:", "").trim());
//            String[] word = contentInfo[5].split(" ");
//            for (String string : word) {
//                System.out.println("DOI:"+string);
//            }
//            System.out.println("year:"+word[1]);
//            System.out.println("DOIxxxx:"+word[8]);
//            System.out.println("abstract:"+p.get(1).text());
////            for (Element element2 : p) {
////                System.out.println("content:"+element2.text());
////            }
//            String p1 = element.getElementsByTag("p").text();
            //System.out.println("p1:"+p1);
        }
    }
    
    @Test
    public void test2_1(){
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.intechopen.com/books/mitigation-of-ionospheric-threats-to-gnss-an-appraisal-of-the-scientific-and-technological-outputs-of-the-transmit-project").get();
            //doc = Jsoup.connect("http://www.intechopen.com/books/iron-metabolism").get();
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
            System.out.println("contentInfo:"+contentInfo.length);
            for (String string : contentInfo) {
                System.out.println("string:"+string);
            }
            System.out.println("Creater:"+p.get(0).getElementsByTag("a").get(0).text().trim());
            if (p.get(0).getElementsByTag("a").size() > 1) {
                System.out.println("right:uri:"+p.get(0).getElementsByTag("a").get(1).text().trim());
            }
            System.out.println("ISBN:"+contentInfo[1].replaceAll("ISBN", "").trim());
            System.out.println("Publisher:"+contentInfo[3].replaceAll("Publisher:", "").trim());
            String[] word = contentInfo[5].split(" ");
            for (String string : word) {
                System.out.println("DOI:"+string);
            }
            System.out.println("year:"+word[1]);
            System.out.println("DOIxxxx:"+word[8]);
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
        Response response = null;
        Document doc = null;
        try {
            response = Jsoup.connect("http://www.intechopen.com/books/latest/82/list").execute();
            System.out.println(response.statusCode());
            if (response.statusCode() == 200){
                doc = response.parse();
            } else {
                return;
            }
        } catch (IOException e) {
            System.out.println("ddd:"+e);
            return;
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
    
    @Test
    public void excel() {
        //寫入Excel
        XSSFWorkbook wb; //產XSSFWorkbook如果使用XSSFWorkbook需要範本是Office2007以上的版本
        try {
            //String filePath = IntechopenCrawler.class.getResourceAsStream("CrawlerTextBook.xlsx").toString();
            //System.out.println("filePath:"+filePath);
            OPCPackage opcPackage = OPCPackage.open(IntechopenCrawler.class.getResourceAsStream("CrawlerTextBook.xlsx"));
            wb = new XSSFWorkbook(opcPackage);
        } catch (IOException e) {
            L.error("product_Selection_report.xlsx loading error.", e);
            return;
        } catch (InvalidFormatException e) {
            L.error("product_Selection_report.xlsx loading error.", e);
            return;
        }
    }
    
    @Test
    public void word(){
        String word = "Edited by Riccardo Notarpietro, Fabio Dovis"
                + ", Giorgiana De Franceschi and Marcio Aquino, ISBN 978-953-51-1642-4"
                + ", 236 pages, Publisher: InTech, Chapters published July 17"
                + ", 2014 under CC BY 3.0 license"
                + "DOI: 10.5772/58550";
        int a = word.indexOf("ISBN");
        int b = word.indexOf(", Publisher:");
        String creator = word.substring(a, b);
        System.out.println(creator);
    }
    
    @Test
    public void test4() {
        List<Integer> list = new ArrayList<>();
        list.add(6);
        list.add(6);
        list.add(-5);
        list.add(-1);
        list.add(-2);
        list.add(-3);
        list.add(-3);
        list.add(3);
        list.add(3);
        list.add(3);
    }
}
