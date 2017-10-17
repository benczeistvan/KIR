import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by lbene on 17.08.2017.
 */
public class ReadExcel {

    public Tanulo tanulo[] = new Tanulo[3400];
    public int index;
    public String DEST = "/Users/istvan/GitHub/KIR/BAKS.xls";


    public static final int sajatNev = 2;

    int sajatAnya = 8;
    int sajatSzuletes = 7;
    int sajatAzonosito = 5;

    public boolean read() {
        try {
            FileInputStream file = new FileInputStream(new File(DEST));

            HSSFWorkbook workbook = new HSSFWorkbook(file);

            HSSFSheet sheet = workbook.getSheetAt(0);


            for (int i = 0; i <3400; i++){
                tanulo[i] = new Tanulo();
            }


            if (egyoszlopos(DEST)){
                System.out.println("Egy oszlopos");
                sajatAnya = 7;
                sajatSzuletes = 6;
                sajatAzonosito = 4;
            }else{
                System.out.println("Ket oszlopos");
                sajatAnya = 8;
                sajatSzuletes = 7;
                sajatAzonosito = 5;
            }



            index = 0;
            int j = 0;
            int kiszur = 5;
            String sorszam = ""; //HA NULLA MARAD AZ HIBA
            //////////////
            /////VEGIG MEGYEK AZ EXCELLEN
            ////////////
            for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {
                Row row = rowIterator.next();
                    index++;
                    j = 0;
                    if (index > kiszur){
                        if (kiszur == 5) {
                            index = 1;
                        }
                        kiszur = 0;
                    for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext(); ) {
                        Cell cellData = cellIterator.next();
                        // todo work with the data
                        j++;
                        switch (j) {
                            case 1:
                                sorszam = cellData.toString();
                                break;
                            case 5:
                                tanulo[index].setAzonosito(cellData.toString());
                                break;
                            case 2:
                                tanulo[index].setNev(cellData.toString());
                                break;
                            case 8:
                                tanulo[index].setAnyanev(cellData.toString());
                                break;
                            case 7:
                                //tanulo[index].setSzuletes(cellData.toString());
                                break;
                            case 6:
                                tanulo[index].setHely(cellData.toString());
                                break;


                                //ITT MOST VAGY 1.0 vagy csak siman 1
                            case 16:
                                //System.out.println("\n" + cellData.toString());
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Előképző 1");
                                }
                                break;
                            case 17:
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Előképző 2");
                                }
                                break;
                            case 18:
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Alap 1");
                                }
                                break;
                            case 19:
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Alap 2");
                                }
                                break;
                            case 20:
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Alap 3");
                                }
                                break;
                            case 21:
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Alap 4");
                                }
                                break;
                            case 22:
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Alap 5");
                                }
                                break;
                            case 23:
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Alap 6");
                                }
                                break;
                            case 24:
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Továbbképző 7");
                                }
                                break;
                            case 25:
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Továbbképző 8");
                                }
                                break;
                            case 26:
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Továbbképző 9");
                                }
                                break;
                            case 27:
                                if (cellData.toString() != "") {
                                    tanulo[index].setEvfolyam("Továbbképző 10");
                                }
                                break;

                            case 28:
                                tanulo[index].setSornaploszam(sorszam + "/" + cellData.toString());
                                sorszam = "";

                            case 32:
                                tanulo[index].setBeirasinaplo(cellData.toString());
                                //System.out.println(cellData.toString());

                            default:
                                break;
                        }
                    }

                       // System.out.println(tanulo[index].getNev());
                    }

            }

            //System.out.println("na: " + tanulo[1].getNev());

            return true;
        }
        catch (Exception exception) {

            System.out.println("WTF");
        }

        return false;
    }



    public boolean egyoszlopos(String DEST) throws IOException {
        FileInputStream file = new FileInputStream(new File(DEST));
        HSSFWorkbook workbook = new HSSFWorkbook(file);
        HSSFSheet sheet = workbook.getSheetAt(0);
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

}
