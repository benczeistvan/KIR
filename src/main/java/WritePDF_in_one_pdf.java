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


    public boolean write(Tanulo tanulo[], int meret, String DESTINATION, String lapnev, boolean vanBennehiba) throws FileNotFoundException, DocumentException {

        String filename = DESTINATION + "/" + lapnev + "_.pdf";
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();

        int font_size = 12;
        int font_size_small = 11;
        int font_size_small_extra = 10;
        //System.out.println("ennyi gyerkőc van: " + readExcel.index);
        //eddig kell majd hivatalosan menjen: readExcel.index
        for (int i = 1; i <= meret; i++) {

            if (i == meret)
                if (tanulo[i].getNev().toString().length() == 0){
                break;
            }else{
                System.out.println("ELLENORIZD, utolso tanulo: " + tanulo[i].getNev());
            }

            try {


                writer.setPageEmpty(false);

//                int year = tanulo[i].getSzuletes().getYear();
//                int month = tanulo[i].getSzuletes().getMonth();
//                int day = tanulo[i].getSzuletes().getDate();
//                month++;
//                year -= 100;
//
//                System.out.println(tanulo[i].getSzuletes().getMonth());
//                System.out.println(tanulo[i].getSzuletes().getYear());
//                System.out.println(day);
//                System.out.println(tanulo[i].getSzuletes().toString()+"\n");
//
//                String nap;
//                if (day <= 9) {
//                    nap = "0";
//                    nap += String.valueOf(day);
//                }else{
//                    nap = String.valueOf(day);
//                }
//
//                String honap;
//                if (month <= 9){
//                    honap = "0";
//                    honap += String.valueOf(month);
//                }else{
//                    honap = String.valueOf(month);
//                }
//
//                String ev;
//                if (year <= 9){
//                    if (year == 0){
//                     ev = "2000";
//                    }else {
//                        ev = "200" + year;
//                    }
//                }else{
//                    ev = "20" + year;
//                }
//
//                String date = ev + "." + honap + "." + nap + ".";

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
                }else{

                }


                //////NEVNEK A HELYE
                if (tanulo[i].getNev().toString().length() > 17){
                    if (tanulo[i].getNev().toString().length() > 19){
                        System.out.println("FIGYELEM EXTRA HOSSZÚ NÉV: " + tanulo[i].getNev() + "\n");
                        PlaceChunck(writer, capitalizeString(tanulo[i].getNev().toString().toLowerCase()), nev_x - 4, nev_y, font_size_small_extra);
                        //vanBennehiba = true;
                    }else {
                        PlaceChunck(writer, capitalizeString(tanulo[i].getNev().toString().toLowerCase()), nev_x - 2, nev_y, font_size_small);
                        System.out.println(tanulo[i].getNev() + " Túl hosszú név");
                    }

                }else{
                    PlaceChunck(writer, capitalizeString(tanulo[i].getNev().toString().toLowerCase()), nev_x, nev_y, font_size);
                }


                //ANYANEVNEK A HELYE
                if (tanulo[i].getAnyanev().toString().length() > 17){
                    System.out.println(tanulo[i].getAnyanev() + " Túl hosszú Anya név");
                    PlaceChunck(writer, capitalizeString(tanulo[i].getAnyanev().toString().toLowerCase()), anyanev_x - 2, anyanev_y, font_size_small);
                    if (tanulo[i].getAnyanev().toString().length() > 20){
                        System.out.println("FIGYELEM EXTRA HOSSZÚ ANYA NÉV: " + tanulo[i].getAnyanev() + "\n");
                        //vanBennehiba = true;
                    }
                }else{
                    PlaceChunck(writer, capitalizeString(tanulo[i].getAnyanev().toString().toLowerCase()), anyanev_x, anyanev_y, font_size);
                }



                ////SZULETESI HELYNEK A NEVE
                if (tanulo[i].getHely().toString().length() > 14){
                    PlaceChunck(writer, tanulo[i].getHely(), szulHely_x - 4, szulHely_y, font_size_small);
                    System.out.println(tanulo[i].getHely() + " Túl hosszú város");
                    if (tanulo[i].getHely().toString().length() > 17){
                        System.out.println("FIGYELEM EXTRA HOSSZÚ VÁROS NÉV " + tanulo[i].getHely() + "\n");
                        //vanBennehiba = true;
                    }
                }else{
                    PlaceChunck(writer, tanulo[i].getHely(), szulHely_x, szulHely_y, font_size);
                }


                String datum_check = tanulo[i].getSzuletes_KIR().toString();
                datum_check=datum_check.replaceAll("[*0-9]", "");
                datum_check=datum_check.replaceAll(".", "");
                datum_check=datum_check.replaceAll(" ", "");
                if (datum_check.length() > 1){
                    System.out.println("Hibas datum: " + tanulo[i].getSzuletes_KIR() + " " + tanulo[i].getNev() + " " + tanulo[i].getAzonosito());
                    vanBennehiba = true;
                }



                //PlaceChunck(writer, capitalizeString(tanulo[i].getNev().toString().toLowerCase()), 102, 744);
                PlaceChunck(writer, tanulo[i].getAzonosito(), azonosito_x, azonosito_y, font_size);
                //PlaceChunck(writer, tanulo[i].getHely(), 102, 721);
                //PlaceChunck(writer, date, 350, 721);
                PlaceChunck(writer, tanulo[i].getSzuletes_KIR(), szuletes_ev_x, szuletes_ev_y, font_size);
                //PlaceChunck(writer, capitalizeString(tanulo[i].getAnyanev().toString().toLowerCase()), 102, 698);
                PlaceChunck(writer, tanulo[i].getEvfolyam(), evfolyam_x, evfolyam_y, font_size);
                PlaceChunck(writer, tanulo[i].getBeirasinaplo(), beirasi_naplo_x, beirasi_naplo_y, font_size);
                PlaceChunck(writer, tanulo[i].getSornaploszam(), sornaploszam_x, sornaploszam_y, font_size);
                PlaceChunck(writer, "magyar", magyar_x, magyar_y, font_size);

                if (tanulo[i].getSzuletes_KIR().toString().length() < 2){
                    System.out.println("Nincs meg a szuletesi eve: "+tanulo[i].getAzonosito() + " " + tanulo[i].getNev());
                    vanBennehiba = true;
                }

                if (tanulo[i].getNev().length() < 3){
                    System.out.println("Nincs meg a neve: "+tanulo[i].getAzonosito() + " " + tanulo[i].getNev());
                    vanBennehiba = true;
                }

                if (tanulo[i].getAzonosito().length() < 11){
                    System.out.println("Nincs meg az azonosítója: "+tanulo[i].getAzonosito() + " " + tanulo[i].getNev());
                    vanBennehiba = true;
                }

                if (tanulo[i].getAnyanev().length() < 3){
                    System.out.println("Nincs meg az anya neve: "+tanulo[i].getAzonosito() + " " + tanulo[i].getNev());
                    vanBennehiba = true;
                }

                if (tanulo[i].getHely().length() < 3){
                    System.out.println("Nincs meg a szuletesi helye: "+tanulo[i].getAzonosito() + " " + tanulo[i].getNev());
                    vanBennehiba = true;
                }

                if (tanulo[i].getEvfolyam().length() < 4){
                    System.out.println("Nincs meg az evfolyama: "+tanulo[i].getAzonosito() + " " + tanulo[i].getNev());
                    vanBennehiba = true;
                }

                if (tanulo[i].getSornaploszam().length() < 3){
                    System.out.println("Nincs meg a szuletesi eve: "+tanulo[i].getAzonosito() + " " + tanulo[i].getNev());
                    vanBennehiba = true;
                }

                if (!tanulo[i].getHely().contains("Budapest")) {
                    if (tanulo[i].getHely().toLowerCase().contains(".") || tanulo[i].getHely().toLowerCase().contains("/")) {
                        System.out.println("Külföldi van benne! Neve: " + tanulo[i].getNev() + " " + tanulo[i].getAzonosito());
                        System.out.println(tanulo[i].getHely() + "\n");
                        vanBennehiba = true;
                    }
                }

                if (tanulo[i].getBeirasinaplo().toString().toLowerCase().contains("ány")){
                    System.out.println("Hibás beírási napló sorszám!!:  " + tanulo[i].getBeirasinaplo() + "->" + tanulo[i].getNev() + " " + tanulo[i].getAzonosito());
                    vanBennehiba = true;
                }

                if (tanulo[i].getSornaploszam().toString().toLowerCase().contains(tanulo[i].getNev().toString().toLowerCase())){
                    vanBennehiba = true;
                    System.out.println("Hibás sorszám/naplószám!!  " + tanulo[i].getSornaploszam().toString() + "->" + tanulo[i].getNev() + " " + tanulo[i].getAzonosito());
                }



                //System.out.println("\n" + tanulo[i].getHely()+"\n");


                //document.add(createFirstTable());

                document.newPage();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        document.close();

        if (vanBennehiba){
            return false;
        }else{
            return true;
        }
    }

    private void PlaceChunck(PdfWriter writer, String text, int x, int y, int font_size) {
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





        table.setTotalWidth(PageSize.A4.getWidth());
        table.setLockedWidth(true);
        return table;
    }



    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

}
