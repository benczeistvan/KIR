import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class ModifyPDF {

    PdfWriter writer;
    public String templateInputStream = "/Users/istvan/projektek/excell 2/src/main/java/torzslap.pdf";

    public boolean modify(Tanulo tanulo[], int index, String DESTINATION){

        int font_size = 12;

        //eddig kell majd hivatalosan menjen: readExcel.index
        //FIGYELEM AZ INDEXELES ONNAN KEZDODIK AMIKOR vannak adatok javitas folyamatban
        for (int i = 1; i <= index; i++) {
            String filename = DESTINATION + "/" + i + ". " + tanulo[i].getNev() + ".pdf";
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


                if (tanulo[i].getNev().toString().length() > 25){
                    PlaceChunck(tanulo[i].getNev(), 96, 744, 10);
                }else{
                    PlaceChunck(tanulo[i].getNev(), 102, 744, 12);
                }

                if (tanulo[i].getAnyanev().toString().length() > 25){
                    PlaceChunck(tanulo[i].getAnyanev(), 96, 698, 10);
                }else{
                    PlaceChunck(tanulo[i].getAnyanev(), 102, 698, 12);
                }

                if (tanulo[i].getHely().toString().length() > 17){
                    PlaceChunck(tanulo[i].getHely(), 96, 721, 10);
                }else{
                    PlaceChunck(tanulo[i].getHely(), 102, 721, 12);
                }


                //PlaceChunck(tanulo[i].getNev(), 102, 744);
                PlaceChunck(tanulo[i].getAzonosito(), 350, 746, 12);
                //PlaceChunck(tanulo[i].getHely(), 102, 721);
                PlaceChunck(tanulo[i].getSzuletes().toString(), 350, 721, 12);
                //PlaceChunck(tanulo[i].getAnyanev(), 102, 698);
                PlaceChunck(tanulo[i].getEvfolyam(), 102, 766, 12);
                PlaceChunck(tanulo[i].getBeirasinaplo(), 245, 766, 12);
                PlaceChunck(tanulo[i].getSornaploszam(), 350, 766, 12);
                PlaceChunck("magyar", 102, 677, 12);


                //document.add(createFirstTable());


                document.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return true;
    }

    private void PlaceChunck(String text, int x, int y, int font_size) {
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
        cb.setFontAndSize(bf, font_size);
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
