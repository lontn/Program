package org.fcu.green.crawler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fcu.green.crawler.domain.TextBookInfo;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IntechopenCrawler implements Crawler {
    private static final Logger L = LogManager.getLogger(IntechopenCrawler.class);
    private static final String WEBSITE = "http://www.intechopen.com";
    private static final String FILEOUT = "D:/tmp/CrawlerTextBook_"+new DateTime().toString("yyyy-MM-dd_HHmmssmmm")+".xlsx";
    private volatile boolean start;

    private void crawlerWebSite() {
        int pages = getPageCount();
        List<TextBookInfo> bookInfoList = new ArrayList<TextBookInfo>();
        for (int i = 1; i <= pages; i++) {
            L.info("Page Count:{}", i);
            if (i % 30 == 0){
                sleep(1 * 60 * 1000);
            }
            List<String> booksList = getBooksList(i);
            for (String url : booksList) {
                try {
                    bookInfoList.add(getTextBookInfo(url));
                } catch (Exception e) {
                    L.warn("URL Error:{}",url);
                    L.error("crawlerWebSite Err", e);
                }
            }
        }
        //寫入Excel
        exportExcel(bookInfoList);
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
            L.error("PageCount Err!!", e);
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
            String contentInfo = p.get(0).text();
            int a = contentInfo.indexOf("Edited by");
            int b = contentInfo.indexOf(", ISBN");
            String creator = (String) contentInfo.subSequence(a, b);
            creator = creator.replaceAll("Edited by", "");
            int a1 = contentInfo.indexOf("ISBN");
            int b1 = contentInfo.indexOf(", Publisher:");
            String isbn = (String) contentInfo.subSequence(a1, b1);
            isbn = isbn.replaceAll("ISBN", "");
            String[] isbnSplit = isbn.split(",");
            int a2 = contentInfo.indexOf("Publisher:");
            int b2 = contentInfo.indexOf(", Chapters");
            String publisher = (String) contentInfo.subSequence(a2, b2);
            publisher = publisher.replaceAll("Publisher:", "");
            int a3 = contentInfo.indexOf("Chapters published");
            int b3 = contentInfo.indexOf("under");
            String year = (String) contentInfo.subSequence(a3, b3);
            year = year.replaceAll("Chapters published", "").trim();
            String[] yearSplit = year.split(",");
            int a4 = contentInfo.indexOf("under");
            int b4 = contentInfo.indexOf("DOI:");
            String right = contentInfo.substring(a4, b4);
            right = right.replaceAll("under", "");
            int a5 = contentInfo.indexOf("DOI:");
            int b5 = contentInfo.length();
            String doi = contentInfo.substring(a5, b5);
            doi = doi.replaceAll("DOI:", "");
            
            book.setCreator(creator.trim());
            book.setIdentifier(isbnSplit[0].trim());
            book.setPublisher(publisher.trim());
            book.setYear(yearSplit[1].trim());
            book.setRightUri(right.trim());
            book.setSyshyperlink(url);
            book.setDoi(doi.trim());
            if (p.size() > 1) {
                book.setIntroduction(p.get(1).text());
            }
            
//            String[] contentInfo = p.get(0).text().split(",");
//            int atag = p.get(0).getElementsByTag("a").size();
//            String creator = p.get(0).getElementsByTag("a").get(0).text().trim();
//            book.setCreator(creator);
//            book.setIdentifier(contentInfo[1].replaceAll("ISBN", "").trim());
//            book.setPublisher(contentInfo[3].replaceAll("Publisher:", "").trim());
//            String[] word = contentInfo[5].split(" ");
//            book.setYear(word[1]);
//            if (atag > 1) {
//             String rightUri = p.get(0).getElementsByTag("a").get(1).text().trim();
//             book.setRightUri(rightUri);
//            }
//            book.setSyshyperlink(url);
//            book.setDoi(word[8]);
//            book.setIntroduction(p.get(1).text());
       }
        return book;
    }
    
    private List<String> getBooksList(int page){
        List<String> detailUrlList = new ArrayList<>();
        Response response = null;
        Document doc = null;
        try {
            response = Jsoup.connect("http://www.intechopen.com/books/latest/"+page+"/list").execute();
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                doc = response.parse();
            } else {
                return detailUrlList;
            }
        } catch (IOException e) {
            L.warn("No. Page:{}", page);
            L.error("BooksList Err:{}", e);
            return detailUrlList;
        }
        Elements content = doc.getElementsByClass("main-content");
        for (Element element : content) {
            Elements dtTag = element.getElementsByTag("dt");
            for (Element element2 : dtTag) {
                Elements url = element2.getElementsByTag("a");
                detailUrlList.add(WEBSITE+url.attr("href"));
                L.info("BooksList:{}",url.attr("href"));
            }
        }
        return detailUrlList;
    }
    
    private void exportExcel(List<TextBookInfo> bookInfoList){
        XSSFWorkbook wb; //產XSSFWorkbook如果使用XSSFWorkbook需要範本是Office2007以上的版本
        try {
            OPCPackage opcPackage = OPCPackage.open(IntechopenCrawler.class.getResourceAsStream("CrawlerTextBook.xlsx"));
            wb = new XSSFWorkbook(opcPackage);
        } catch (IOException e) {
            L.error("CrawlerTextBook.xlsx loading error.", e);
            return;
        } catch (InvalidFormatException e) {
            L.error("CrawlerTextBook.xlsx loading error.", e);
            return;
        }
        //CrawlerTextBook Sheet
        XSSFSheet crawlerSheet = wb.getSheet("CrawlerTextBook");
        
        //字型設定
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);//顏色
        font.setFontHeightInPoints((short)8);
        font.setFontName("Arial");//字型樣式
        font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗體
        
        //設定儲存格格式(內容)
        XSSFCellStyle styleRow = wb.createCellStyle();
        styleRow.setFont(font);
        //設定框線2
        styleRow.setBorderBottom((short)1);
        styleRow.setBorderTop((short)1);
        styleRow.setBorderLeft((short)1);
        styleRow.setBorderRight((short)1);
        int rownum = 2;
        for (TextBookInfo row : bookInfoList) {
            XSSFRow dataRow = crawlerSheet.createRow(rownum);
            XSSFCell cellShowA = dataRow.createCell(0);
            XSSFCell cellShowB = dataRow.createCell(1);
            XSSFCell cellShowC = dataRow.createCell(2);
            XSSFCell cellShowD = dataRow.createCell(3);
            XSSFCell cellShowE = dataRow.createCell(4);
            XSSFCell cellShowF = dataRow.createCell(5);
            XSSFCell cellShowG = dataRow.createCell(6);
            XSSFCell cellShowH = dataRow.createCell(7);
            XSSFCell cellShowI = dataRow.createCell(8);
            XSSFCell cellShowJ = dataRow.createCell(9);
            XSSFCell cellShowK = dataRow.createCell(10);
            XSSFCell cellShowL = dataRow.createCell(11);
            XSSFCell cellShowM = dataRow.createCell(12);
            
            cellShowA.setCellStyle(styleRow);
            cellShowB.setCellStyle(styleRow); 
            cellShowC.setCellStyle(styleRow); 
            cellShowD.setCellStyle(styleRow); 
            cellShowE.setCellStyle(styleRow); 
            cellShowF.setCellStyle(styleRow); 
            cellShowG.setCellStyle(styleRow); 
            cellShowH.setCellStyle(styleRow); 
            cellShowI.setCellStyle(styleRow); 
            cellShowJ.setCellStyle(styleRow); 
            cellShowK.setCellStyle(styleRow); 
            cellShowL.setCellStyle(styleRow); 
            cellShowM.setCellStyle(styleRow); 
            
            cellShowA.setCellValue("");
            cellShowB.setCellValue(row.getTitle());
            cellShowC.setCellValue(row.getIdentifier());
            cellShowD.setCellValue(row.getCreator());
            cellShowE.setCellValue(row.getPublisher());
            cellShowF.setCellValue("");
            cellShowG.setCellValue(row.getSubjectClassification());
            cellShowH.setCellValue("");
            cellShowI.setCellValue(row.getRightUri());
            cellShowJ.setCellValue(row.getSyshyperlink());
            cellShowK.setCellValue(row.getYear());
            cellShowL.setCellValue(row.getIntroduction());
            cellShowM.setCellValue(row.getDoi());
            rownum++;
        }
        wb.setForceFormulaRecalculation(true);
        // Write the output to a file
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(FILEOUT);
            wb.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        L.info("Crawler Process..Start");
        crawlerWebSite();
        L.info("Crawler Process..END");
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

}
