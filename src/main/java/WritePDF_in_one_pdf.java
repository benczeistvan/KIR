import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WritePDF_in_one_pdf {


    public boolean write(Tanulo tanulo[], int meret, String DESTINATION, String lapnev) throws FileNotFoundException, DocumentException {

        String filename = DESTINATION + "/" + lapnev + "_.pdf";
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();


        //System.out.println("ennyi gyerkőc van: " + readExcel.index);
        //eddig kell majd hivatalosan menjen: readExcel.index
        for (int i = 1; i < meret; i++) {


            try {


                writer.setPageEmpty(false);

                int year = tanulo[i].getSzuletes().getYear();
                int month = tanulo[i].getSzuletes().getMonth();
                int day = tanulo[i].getSzuletes().getDate();
                month++;
                year -= 100;

                //SimpleDateFormat simpleDateFormat = new SimpleDateFormat(tanulo[i].getSzuletes().toString());



                System.out.println(tanulo[i].getSzuletes().getMonth());
                System.out.println(tanulo[i].getSzuletes().getYear());
                System.out.println(day);
                System.out.println(tanulo[i].getSzuletes().toString()+"\n");

                String nap;
                if (day <= 9) {
                    nap = "0";
                    nap += String.valueOf(day);
                }else{
                    nap = String.valueOf(day);
                }

                String honap;
                if (month <= 9){
                    honap = "0";
                    honap += String.valueOf(month);
                }else{
                    honap = String.valueOf(month);
                }

                String ev;
                if (year <= 9){
                    if (year == 0){
                     ev = "2000";
                    }else {
                        ev = "200" + year;
                    }
                }else{
                    ev = "20" + year;
                }

                String date = ev + "." + honap + "." + nap + ".";

                //String datum = String.valueOf(year) + "." + String.valueOf(month) + "." + String.valueOf(day) + ".";


                PlaceChunck(writer, tanulo[i].getNev(), 100, 744);
                PlaceChunck(writer, tanulo[i].getAzonosito(), 350, 746);
                PlaceChunck(writer, tanulo[i].getHely(), 100, 721);
                PlaceChunck(writer, date, 350, 721);
                PlaceChunck(writer, tanulo[i].getAnyanev(), 100, 698);
                PlaceChunck(writer, tanulo[i].getEvfolyam(), 100, 766);
                PlaceChunck(writer, tanulo[i].getBeirasinaplo(), 240, 766);
                PlaceChunck(writer, tanulo[i].getSornaploszam(), 350, 766);
                PlaceChunck(writer, "magyar", 100, 677);

                //System.out.println("\n" + tanulo[i].getHely()+"\n");


                //document.add(createFirstTable());

                document.newPage();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        document.close();

        return true;
    }

    private void PlaceChunck(PdfWriter writer, String text, int x, int y) {
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
