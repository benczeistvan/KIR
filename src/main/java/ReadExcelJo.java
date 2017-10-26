import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lbene on 17.08.2017.
 */
public class ReadExcelJo {

    public Tanulo tanulo[] = new Tanulo[3400];
    public int index;
    //public String DEST = "/Users/istvan/Documents/kir/Telephelyek/CLASSIC/CSONGRÁD  Kossuth tér 6.         2017-2018. tanév.xls";
    //public String DEST_CLASSIC = "/Users/istvan/GitHub/tableController/src/main/java/CLASSIC.xls";
    //public String DEST_SZILVER = "/Users/istvan/GitHub/tableController/src/main/java/SZILVER.xls";
    public int rossz;
    public int nincsMeg;
    public boolean egyoszlop = true;

    public int ismetloIndex;
    public int hibasTanuloIndex = 0;




    public static final int sajatNev = 2;

    int sajatAnya = 8;
    int sajatSzuletes = 7;
    int sajatAzonosito = 5;
    int osztaly = 0;

    public boolean datumcsere = true;

    ///////////////////////////READ METÓDUS///////////////////////////////
    //////////////////////////////////////////////////////////////////////
    public boolean readExceljo(String DEST, String szilverClassic) throws IOException {
        rossz = 0;
        nincsMeg = 0;
        hibasTanuloIndex = 0;

        boolean vanBenneHiba = false;
        boolean vanBenneHibaNagy = false;

        int i = 3; ///AZ i az a lapok indexe
        int vanOsztaly = 0;

        if (egyoszlopos(DEST, i)){
            System.out.println("Egy oszlopos");
            sajatAnya = 7;
            sajatSzuletes = 6;
            sajatAzonosito = 4;
            osztaly = -1;
        }else{
            System.out.println("Ket oszlopos");
            sajatAnya = 8;
            sajatSzuletes = 7;
            sajatAzonosito = 5;
            osztaly = 0;
        }

        WritePDF writePDF = new WritePDF();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {

            //
            FileInputStream file = new FileInputStream(new File(DEST));
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            FileInputStream fileExport = new FileInputStream(new File(szilverClassic));
            HSSFWorkbook workbookExport = new HSSFWorkbook(fileExport);
            HSSFSheet sheetExport = workbookExport.getSheetAt(0);

            //HSSFSheet sheet = workbook.getSheetAt(1);

            //System.out.println(workbook.getNumberOfSheets());




            //outerloop:
            while(!workbook.getSheetName(i).contentEquals("Ö.2017.") && !workbook.getSheetName(i).contentEquals("Ö.2017") &&
                    !workbook.getSheetName(i).contentEquals("Gyv.2017.")){
                start:

                for (int z = 0; z <3400; z++){
                    tanulo[z] = new Tanulo();
                }


                HSSFSheet sheet = workbook.getSheetAt(i);
                //System.out.println(workbook.getSheetName(i).toString());


                index = 0; //tanulo indexe
                // Ez egy lapon beluli sor indexe de 6-ot levag mert onnan kezdodnek a diakok ezert majd a diakok sorszama lesz
                int j = 0; //ez az oszlop indexe
                int kiszur = 5;
                String sorszam = ""; //HA NULLA MARAD AZ HIBA
                //////////////
                /////VEGIG MEGYEK AZ EXCELLEN
                ////////////
                Row row;
                //outerloop:
                for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {
                    row = rowIterator.next();
                    boolean hibasOM = false;
                    index++;
                    j = 0;
                    if (index > kiszur){
                        if (kiszur == 5) {
                            index = 1;
                        }
                        kiszur = 0;


                        if (row.cellIterator().hasNext()) {

                            if (row.cellIterator().next().toString().contentEquals("")) {
                                //System.out.println(row.cellIterator().next().toString());
                                break;
                            }
                            String ellenorzo = row.cellIterator().next().toString();
                            ellenorzo = ellenorzo.replace(".","");
                            if (!isInteger(ellenorzo)){
                                //System.out.println(row.cellIterator().next().toString());
                                //System.out.println("BREAK");
                                break;
                            }

                        }else{

                            break;
                        }




                        vanOsztaly = 0;
                        ///////////VEGIG MEGYEK A SAJAT EXCEL sorain
                        /////////////

                        //WriteExcel writeExcel = new WriteExcel();

                        //writeExcel.write("setAsActiveCell", index + 5, osztaly, i, DEST);


                        for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext(); ) {
                            Cell cellData = cellIterator.next();
                            //                            todo work with the data
                            j++;
                            //System.out.println(cellData.toString());

//                                case 1:
//                                    if (cellData.toString() == ""){
//                                        System.out.println("BREAK " + index);
//                                        break outerloop;
//                                    }
//                                    break;
                            if (j == sajatAzonosito) {
                                String string = cellData.toString();
                                if (string.indexOf('.') != 0) {
                                    string = string.replace(".", "");
                                    string = string.replace("E10", "");
                                    string = string.replace("E9", "");
                                }

                                while (string.length() != 11 && string.length() > 4) {
                                    string += "0";
                                }

                                tanulo[index].setAzonosito(string);
                            }

                            if (j == sajatNev) {
                                tanulo[index].setNev(capitalizeString(cellData.toString().toLowerCase()));
                            }

                            if (j == sajatAnya) {
                                tanulo[index].setAnyanev(capitalizeString(cellData.toString().toLowerCase()));
                            }

                            if (j == sajatSzuletes) {
                                //tanulo[index].setSzuletes(cellData.getDateCellValue());
                                tanulo[index].setSzuletes_KIR(cellData.toString());
                            }

                            if (j == 6 + osztaly){
                                String helyseg = capitalizeString(cellData.toString().toLowerCase());
                                if (helyseg.contains("Budapest")){
                                    helyseg.replaceAll("i", String.valueOf(Character.toUpperCase('i')));
                                }
                                tanulo[index].setHely(helyseg);
                            }

                            //if (j == 15 + )

                            if (j == 16 + osztaly) {
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Előképző 1");
                                    vanOsztaly++;
                                }
                            }

                            if (j == 17 + osztaly) {
                                if (cellData.toString().contains("1")) {
                                    tanulo[index].setEvfolyam("Előképző 2");
                                    vanOsztaly++;
                                }
                            }

                            if (j == 18 + osztaly) {
                                if (cellData.toString().contains("1")) {
                                    tanulo[index].setEvfolyam("Alap 1");
                                    vanOsztaly++;
                                }
                            }

                            if (j == 19 + osztaly) {
                                if (cellData.toString().contains("1")) {
                                    tanulo[index].setEvfolyam("Alap 2");
                                    vanOsztaly++;
                                }
                            }

                            if (j == 20 + osztaly) {
                                if (cellData.toString().contains("1")) {
                                    tanulo[index].setEvfolyam("Alap 3");
                                    vanOsztaly++;
                                }
                            }

                            if (j == 21 + osztaly){
                                if (cellData.toString().contains("1")) {
                                    tanulo[index].setEvfolyam("Alap 4");
                                    vanOsztaly++;
                                }
                            }

                            if (j == 22 + osztaly) {
                                if (cellData.toString().contains("1")) {
                                    tanulo[index].setEvfolyam("Alap 5");
                                    vanOsztaly++;
                                }
                            }

                            if (j == 23 + osztaly) {
                                if (cellData.toString().contains("1")) {
                                    tanulo[index].setEvfolyam("Alap 6");
                                    vanOsztaly++;
                                }
                            }

                            if (j == 24 + osztaly) {
                                if (cellData.toString().contains("1")) {
                                    tanulo[index].setEvfolyam("Továbbképző 7");
                                    vanOsztaly++;
                                }
                            }

                            if (j == 25 + osztaly) {
                                if (cellData.toString().contains("1")) {
                                    tanulo[index].setEvfolyam("Továbbképző 8");
                                    vanOsztaly++;
                                }
                            }

                            if (j == 26 + osztaly) {
                                if (cellData.toString().contains("1")) {
                                    tanulo[index].setEvfolyam("Továbbképző 9");
                                    vanOsztaly++;
                                }
                            }

                            if (j == 27 + osztaly) {
                                if (cellData.toString().contains("1")) {
                                    tanulo[index].setEvfolyam("Továbbképző 10");
                                    vanOsztaly++;
                                }
                            }

                            if (j == 28 + osztaly) {
                                if (cellData.toString().toLowerCase().contains(tanulo[index].getNev().toString().toLowerCase())){
                                    System.out.println("Hibás sorszám/naplószám!!  " + tanulo[index].getSornaploszam().toString() + "->" + tanulo[index].getNev() + " " + tanulo[index].getAzonosito() +
                                    "\nindex: " + index + " lap: " + i);
                                    System.out.println("Kérem az új sornaplószámot: ");
                                    String s = br.readLine();
                                    tanulo[index].setSornaploszam(index + "/" + s);
                                    System.out.println("Rendben, új sornapló szám: " + index + "/" + s);

                                }else {
                                    tanulo[index].setSornaploszam(index + "/" + cellData.toString());
                                }
                            }

                            if (j == 32 + osztaly) {
                                tanulo[index].setBeirasinaplo(cellData.toString());
                                //System.out.println(cellData.toString());
                            }

                        }





                    }

                }

                //System.out.println(i);

                if (i == workbook.getNumberOfSheets() - 1){
                    if (!workbook.getSheetName(i).contentEquals("Ö.2017.")) {
                        System.out.println("Ebben az excelbe nem létezik Ö.2017. lap");
                        System.out.println("Az utolso lap neve: " + workbook.getSheetName(i));
                    }
                    break;
                }

                if (workbook.getSheetName(i).contentEquals("Ki2017.")){
                    break;
                }

                System.out.println(workbook.getSheetName(i) + " osztaly:");

                File dir_proba = new File("/Users/istvan/Documents/kir/torzslapok_proba/" + (i+1) + ". " +workbook.getSheetName(i));
                dir_proba.mkdir();

                ModifyPDF modifyPDF = new ModifyPDF();
                if (modifyPDF.modify(tanulo, index, dir_proba.getPath())){
                    //System.out.println("KESZ\n\n");
                }

                File dir = new File("/Users/istvan/Documents/kir/torzslapok/" + (i+1) + ". " + workbook.getSheetName(i));
                dir.mkdir();


                //EGY PDF-be iras
                WritePDF_in_one_pdf writePDF_in_one_pdf = new WritePDF_in_one_pdf();
                if (writePDF_in_one_pdf.write(tanulo, index, dir.getAbsolutePath(), workbook.getSheetName(i), vanBenneHiba)){
                    System.out.println(workbook.getSheetName(i) + " osztaly kesz\n\n");
                }else{
                    vanBenneHiba = true;
                    System.out.println(workbook.getSheetName(i) + " HIBAS osztaly kesz\n\n");
                    vanBenneHibaNagy = true;
                }


                ////KULON PDF-be etszik
//                if (writePDF.write(tanulo,index, dir.getAbsolutePath())){
//                    System.out.println(workbook.getSheetName(i) + " osztaly kesz");
//                }
                i++; //lapszam
                vanBenneHiba = false;

            }
            //for (int i = 0; i < workbook.getNumberOfSheets(); i++){






            if (vanBenneHibaNagy){
                System.out.println("Vigyázat van hibás Adat!!!");
                vanBenneHiba = false;
            }else{
                System.out.println("MINDEN RENDBEN!");
                vanBenneHiba = false;
            }





            file.close();
            fileExport.close();
            return true;
        }
        catch (Exception exception) {

            System.out.println("PROGRAM HIBA: " + exception);
        }
        return false;
    }
    ///////////////////////////////////////////////////////////////
    ////////////////////////READ VÉGE//////////////////////////////
    ///////////////////////////////////////////////////////////////

    public static String replaceAtTheEnd(String input){
        input = input.replaceAll("\\s+$", "");
        return input;
    }

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    // LETEZIK MAR

    public boolean letezikeMar(String string, int index){
        boolean nemLetezik =  true;

        for (int i = 1; i < index; i++){
            if (tanulo[i].getAzonosito().toString().contentEquals(string) &&
                    !tanulo[index].getNev().toString().contentEquals(tanulo[i].getNev().toString())){
                ismetloIndex = i;
                nemLetezik = false;
            }
        }

        if (!nemLetezik){
            return true;
        }else{
            return false;
        }
    }


    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    ///// ISINTEGER


    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    ////   EGYOSZLOPOS


    public boolean egyoszlopos(String DEST, int honnan) throws IOException {
        FileInputStream file = new FileInputStream(new File(DEST));
        HSSFWorkbook workbook = new HSSFWorkbook(file);
        HSSFSheet sheet = workbook.getSheetAt(honnan);
        boolean az = false;
        int kiszur = 7;
        int sajatIndex= 0;

        overloop:
        for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {
            Row row = rowIterator.next();
            int j = 0;
            sajatIndex++;
            if (sajatIndex > kiszur) {
                if (kiszur == 5) {
                    sajatIndex= 1;
                }
                kiszur = 0;


                for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext(); ) {
                    j++;
                    Cell cellData = cellIterator.next();
                    if (j == 4) {
                        if (cellData.toString().length() > 4) {
                            //System.out.println(cellData.toString());
                            az = true;
                            return az;
                        }
                        return az;
                    }
                }
            }
        }
        file.close();

        if (az){
            return true;
        }else{
            return false;
        }
    }

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    ///// KIRELLENORZES

    public boolean KIRellenorzes(HSSFSheet sheetExport, WriteExcel writeExcel, int i, String DEST) throws IOException {
        boolean megvan = false;
        for (Iterator<Row> rowExportIterator = sheetExport.iterator(); rowExportIterator.hasNext();){
            Row rowExport = rowExportIterator.next();

            String azonositoExport = rowExport.cellIterator().next().toString();

            //string.indexOf('a')

            //////ITT KELL ELLENORIZNI HOGY HIBASE
            if (azonositoExport.contentEquals(tanulo[index].getAzonosito()) && !tanulo[index].isHibas()){
                megvan = true;
                //System.out.println(index + " " + tanulo[index].getAzonosito());
                int k = 0;
                for (Iterator<Cell> cellExportIterator = rowExport.cellIterator(); cellExportIterator.hasNext(); ) {
                    Cell cellExportData = cellExportIterator.next();
                    k++;

                    switch (k) {
                        case 2:

                            if (!cellExportData.toString().contentEquals(tanulo[index].getNev())) {
                                rossz++;
                                String kirAdat = cellExportData.toString();
                                System.out.println("\nHibás név:");
                                System.out.println("KIR: " + kirAdat + "\nGABI: " + tanulo[index].getNev());
                                System.out.println("Nem egyezik: " + tanulo[index].getAzonosito() + "\n");
                                //kirAdat += " JAVITVA";
                                //System.out.println(index);
                                //Az indexhez annyit kell hozzaadni amennyivel csuszik a sorszam az excelhez kepest


                            }
                            break;

                        case 3:
                            if (!cellExportData.toString().contentEquals(tanulo[index].getAnyanev())) {
                                rossz++;
                                String kirAdat = cellExportData.toString();
                                System.out.println("\nHibás Anyja neve:");
                                System.out.println("KIR: " + kirAdat + "\nGABI: " + tanulo[index].getAnyanev());
                                System.out.println("Nem egyezik: " + tanulo[index].getAzonosito() + "\n");
                                //kirAdat += " JAVITVA";
                                //System.out.println(index);
                                //Az indexhez annyit kell hozzaadni amennyivel csuszik a sorszam az excelhez kepest

                            }
                            break;

                        case 4:
//                                                String datum = cellExportData.toString();
//                                                String nap;
//                                                String honap;
//                                                String ev;
//                                                String datum_KIR_jo;
//
//                                                ev = datum.substring(0, datum.indexOf('.'));
//                                                datum = datum.replace(ev + ".", "");
//
//                                                honap = datum.substring(1, datum.indexOf('.'));
//                                                datum = datum.replace(ev + ".", "");
//
//                                                nap = datum.substring(1, datum.indexOf('.'));
//
//                                                datum_KIR_jo = ev + "/" + honap + "/" + nap;
//                                                //System.out.println(datum_KIR_jo + " " + (index+5) + " " + i);
                            //System.out.print(".");
                            if (datumcsere  && cellExportIterator.hasNext()) {

                                //System.out.println(".");
                            }

                            break;

                    }
                }

            }else{
                // System.out.println("");
            }


            //if (cellExportData.toString() != tanulo[index].get)
        }


        if (!megvan && !tanulo[index].isHibas()){
            nincsMeg++;
            tanulo[index].setHibas(true);

            //hibasTanuloIndex++;
            System.out.println("Nem talaltam meg az exportba: \n" + tanulo[index].getNev() +
                    "\n" + tanulo[index].getAzonosito() + "\n");
        }
        return true;
    }



    ////////szojavito
    public static String capitalizeString(String string) {

        if (!string.contains("budapest")) {
            char[] chars = string.toLowerCase().toCharArray();
            boolean found = false;
            for (int i = 0; i < chars.length; i++) {
                if (!found && Character.isLetter(chars[i])) {
                    chars[i] = Character.toUpperCase(chars[i]);
                    found = true;
                } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                    found = false;
                }
            }
            return String.valueOf(chars);
        }else{
            char[] chars = string.toLowerCase().toCharArray();
            boolean found = false;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == 'i'){
                    chars[i] = Character.toUpperCase(chars[i]);
                }
                if (!found && Character.isLetter(chars[i])) {
                    chars[i] = Character.toUpperCase(chars[i]);
                    found = true;
                } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                    found = false;
                }
            }
            return String.valueOf(chars);
        }

    }



}

