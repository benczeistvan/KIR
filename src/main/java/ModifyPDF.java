import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class ModifyPDF {

    PdfWriter writer;
    public String templateInputStream = "/Users/istvan/projektek/excell 2/src/main/java/torzslap.pdf";

    public boolean modify(ReadExcel readExcel){

        System.out.println("ennyi gyerkőc van: " + readExcel.index);
        //eddig kell majd hivatalosan menjen: readExcel.index
        //FIGYELEM AZ INDEXELES ONNAN KEZDODIK AMIKOR vannak adatok javitas folyamatban
        for (int i = 13; i <= 13; i++) {
            String filename = "Torzslap-Stamped.pdf";
            Document document = new Document(PageSize.A4);
            try {
                writer = PdfWriter.getInstance(document, new FileOutputStream(filename));

                document.open();

                PdfContentByte cb = writer.getDirectContent();
                PdfReader reader = new PdfReader(templateInputStream);
                PdfImportedPage page = writer.getImportedPage(reader, 1);
                document.newPage();
                cb.addTemplate(page, 0, 0);

                //A4 meret: 595x842
                PlaceChunck(readExcel.tanulo[i].getNev(), 100, 744);
                PlaceChunck(readExcel.tanulo[i].getAzonosito(), 350, 746);
                PlaceChunck(readExcel.tanulo[i].getHely(), 100, 722);
                PlaceChunck(readExcel.tanulo[i].getSzuletes(), 350, 718);
                PlaceChunck(readExcel.tanulo[i].getAnyanev(), 100, 698);
                PlaceChunck(readExcel.tanulo[i].getEvfolyam(), 100, 766);
                PlaceChunck(readExcel.tanulo[i].getBeirasinaplo(), 240, 766);
                PlaceChunck(readExcel.tanulo[i].getSornaploszam(), 350, 766);
                System.out.println("\n" + readExcel.tanulo[i].getHely()+"\n");

                //document.add(createFirstTable());


                document.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return true;
    }

    private void PlaceChunck(String text, int x, int y) {
        PdfContentByte cb = writer.getDirectContent();
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cb.saveState();
        cb.beginText();
        cb.moveText(x, y);
        cb.setFontAndSize(bf, 12);
        cb.showText(text);
        cb.endText();
        cb.restoreState();
    }


    public static PdfPTable createFirstTable() {
        // a table with three columns
        PdfPTable table = new PdfPTable(4);
        //table.getDefaultCell().setBorderWidth(0f);

        //table.getDefaultCell().setBorderColor(Color.WHITE);


        //PdfCell cell = new PdfCell("");

//        // a long phrase
//        Phrase p = new Phrase(
//                "Dr. iText or: How I Learned to Stop Worrying and Love PDF.");
//        PdfPCell cell = new PdfPCell(p);
//        // the prhase is wrapped
//        table.addCell("wrap");
//        cell.setNoWrap(false);
//        table.addCell(cell);
//        // the phrase isn't wrapped
//        table.addCell("no wrap");
//        cell.setNoWrap(true);
//        table.addCell(cell);
//        // a long phrase with newlines
//        p = new Phrase(
//                "Dr. iText or:\nHow I Learned to Stop Worrying\nand Love PDF.");
//        cell = new PdfPCell(p);
//        // the phrase fits the fixed height
//        table.addCell("fixed height (more than sufficient)");
//        cell.setFixedHeight(72f);
//
//
//        table.addCell(cell);
//        // the phrase doesn't fit the fixed height
//        table.addCell("fixed height (not sufficient)");
//        cell.setFixedHeight(36f);
//        table.addCell(cell);
//        // The minimum height is exceeded
//        table.addCell("minimum height");
//        cell = new PdfPCell(new Phrase("Dr. iText"));
//        cell.setMinimumHeight(36f);
//        table.addCell(cell);
//        // The last row is extended
//        table.setExtendLastRow(true);
//        table.addCell("extend last row");
//        table.addCell(cell);



        table.setTotalWidth(PageSize.A4.getWidth());
        table.setLockedWidth(true);
        return table;
    }
}
