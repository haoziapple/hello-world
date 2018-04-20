package com.example.demo.itext;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wanghao
 * @Description
 * @date 2018-04-20 10:28
 */

@Service
public class WordService {
    public void createRTFContext(String path) throws DocumentException,
            IOException {
        Document document = new Document(PageSize.A4);
        RtfWriter2.getInstance(document, new FileOutputStream(path));
        createDoc(document);
    }

    public void createRTFContext(OutputStream os) throws DocumentException,
            IOException {
        Document document = new Document(PageSize.A4);
        RtfWriter2.getInstance(document, os);
        createDoc(document);
    }

    private void createDoc(Document document) throws DocumentException, IOException {
        document.open();
// 设置中文字体
        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
                "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
// 标题字体风格
        Font titleFont = new Font(bfChinese, 12, Font.BOLD);
// 正文字体风格
        Font contextFont = new Font(bfChinese, 10, Font.NORMAL);
        Paragraph title = new Paragraph("标题");
// 设置标题格式对齐方式
        title.setAlignment(Element.ALIGN_CENTER);
        title.setFont(titleFont);
        document.add(title);
        String contextString = "iText是一个能够快速产生PDF文件的java类库。iText的java类对于那些要产生包含文本，表格，图形的只读文档是很有用的。它的类库尤其与java Servlet有很好的给合。使用iText与PDF能够使你正确的控制Servlet的输出。";
        Paragraph context = new Paragraph(contextString);
// 正文格式左对齐
        context.setAlignment(Element.ALIGN_LEFT);
        context.setFont(contextFont);
// 离上一段落（标题）空的行数
        context.setSpacingBefore(20);
// 设置第一行空的列数
        context.setFirstLineIndent(20);
        document.add(context);
// //在表格末尾添加图片
        Image png = Image.getInstance("C:\\Users\\Administrator\\Pictures\\standmeeting.jpg");
        document.add(png);
        document.close();
    }

    public static void main(String[] args) {

        try {

            WordService rtfMain = new WordService();

            rtfMain.createRTFContext("C:\\Users\\Administrator\\Pictures\\test.doc");


        } catch (FileNotFoundException e) {


            e.printStackTrace();

        } catch (DocumentException e) {


            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
