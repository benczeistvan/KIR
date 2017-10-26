import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class ModifyPDF {

    PdfWriter writer;
    public String templateInputStream = "/Users/istvan/GitHub/KIR/src/main/java/torzslap_zene.pdf";

    public boolean modify(Tanulo tanulo[], int index, String DESTINATION){

        int font_size = 12;
        int font_size_small = 11;
        int font_size_small_extra = 10;

        //eddig kell majd hivatalosan menjen: readExcel.index
        //FIGYELEM AZ INDEXELES ONNAN KEZDODIK AMIKOR vannak adatok javitas folyamatban
        for (int i = 1; i <= index; i++) {

            if (i == index){
                if ( tanulo[i].getNev().toString().length() == 0)
                break;
            }else{
                //System.out.println("ELLENORIZD " + i);
            }

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

                boolean zene = true;

                int nev_x = 102;
                int nev_y = 744;

                int anyanev_x = 102;
                int anyanev_y = 698;

                int szulHely_x = 102;
                int szulHely_y = 721;

                int azonosito_x = 350;
                int azonosito_y = 746;

                int szuletes_ev_x = 350;
                int szuletes_ev_y = 721;

                int evfolyam_x = 102;
                int evfolyam_y = 766;

                int beirasi_naplo_x = 245;
                int beirasi_naplo_y = 766;

                int sornaploszam_x = 350;
                int sornaploszam_y = 766;

                int magyar_x = 102;
                int magyar_y = 677;


                if (zene) {
                    nev_x = 110;
                    nev_y = 730;

                    anyanev_x = 110;
                    anyanev_y = 680;

                    szulHely_x = 110;
                    szulHely_y = 705;

                    azonosito_x = 358;
                    azonosito_y = 732;

                    szuletes_ev_x = 358;
                    szuletes_ev_y = 708;

                    evfolyam_x = 110;
                    evfolyam_y = 757;

                    beirasi_naplo_x = 272;
                    beirasi_naplo_y = 757;

                    sornaploszam_x = 358;
                    sornaploszam_y = 757;

                    magyar_x = 110;
                    magyar_y = 655;
                }


                if (tanulo[i].getNev().toString().length() > 17){
                    if (tanulo[i].getNev().toString().length() > 19){
                        PlaceChunck(tanulo[i].getNev().toString(), nev_x - 5, nev_y, font_size_small_extra);
                        //vanBennehiba = true;
                    }else {
                        PlaceChunck(tanulo[i].getNev().toString(), nev_x - 3, nev_y, font_size_small);
                    }
                }else{
                    PlaceChunck(tanulo[i].getNev(), nev_x, nev_y, font_size);
                }

                if (tanulo[i].getAnyanev().toString().length() > 24){
                    PlaceChunck(tanulo[i].getAnyanev(), anyanev_x - 3, anyanev_y, font_size_small);
                }else{
                    PlaceChunck(tanulo[i].getAnyanev(), anyanev_x, anyanev_y, font_size);
                }

                if (tanulo[i].getHely().toString().length() > 17){
                    PlaceChunck(tanulo[i].getHely(), szulHely_x - 4, szulHely_y, font_size_small);
                }else{
                    PlaceChunck(tanulo[i].getHely(), szulHely_x, szulHely_y, font_size);
                }


                //PlaceChunck(tanulo[i].getNev(), 102, 744);
                PlaceChunck(tanulo[i].getAzonosito(), azonosito_x, azonosito_y, 12);
                //PlaceChunck(tanulo[i].getHely(), 102, 721);
                PlaceChunck(tanulo[i].getSzuletes().toString(), szuletes_ev_x, szuletes_ev_y, 12);
                //PlaceChunck(tanulo[i].getAnyanev(), 102, 698);
                PlaceChunck(tanulo[i].getEvfolyam(), evfolyam_x, evfolyam_y, 12);
                PlaceChunck(tanulo[i].getBeirasinaplo(), beirasi_naplo_x, beirasi_naplo_y, 12);
                PlaceChunck(tanulo[i].getSornaploszam(), sornaploszam_x, sornaploszam_y, 12);
                PlaceChunck("magyar", magyar_x, magyar_y, 12);


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
