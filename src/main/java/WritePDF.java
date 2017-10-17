import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class WritePDF {

    PdfWriter writer;

    public boolean write(Tanulo tanulo[], int meret, String DESTINATION){




        //System.out.println("ennyi gyerkőc van: " + readExcel.index);
        //eddig kell majd hivatalosan menjen: readExcel.index
        for (int i = 1; i <= meret; i++) {
            String filename = DESTINATION + "/" + i + ". " + tanulo[i].getNev() + ".pdf";
            Document document = new Document(PageSize.A4);
            try {
                writer = PdfWriter.getInstance(document, new FileOutputStream(filename));

                document.open();

                int year = tanulo[i].getSzuletes().getYear();
                int month = tanulo[i].getSzuletes().getMonth();
                int day = tanulo[i].getSzuletes().getDay();

                String datum = String.valueOf(year) + "." + String.valueOf(month) + "." + String.valueOf(day) + ".";


                PlaceChunck(tanulo[i].getNev(), 100, 744);
                PlaceChunck(tanulo[i].getAzonosito(), 350, 746);
                PlaceChunck(tanulo[i].getHely(), 100, 722);
                PlaceChunck(datum, 350, 718);
                PlaceChunck(tanulo[i].getAnyanev(), 100, 698);
                PlaceChunck(tanulo[i].getEvfolyam(), 100, 766);
                PlaceChunck(tanulo[i].getBeirasinaplo(), 240, 766);
                PlaceChunck(tanulo[i].getSornaploszam(), 350, 766);
                PlaceChunck("magyar", 100, 677);

                System.out.println("\n" + tanulo[i].getHely()+"\n");


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





        table.setTotalWidth(PageSize.A4.getWidth());
        table.setLockedWidth(true);
        return table;
    }
}
