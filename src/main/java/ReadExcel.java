import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

/**
 * Created by lbene on 17.08.2017.
 */
public class ReadExcel {

    public Tanulo tanulo[] = new Tanulo[3400];
    public int index;

    public boolean read() {
        try {
            FileInputStream file = new FileInputStream(new File("/Users/istvan/projektek/excell 2/src/main/java/CLASSIC.xls"));

            HSSFWorkbook workbook = new HSSFWorkbook(file);

            HSSFSheet sheet = workbook.getSheetAt(0);


            for (int i = 0; i <3400; i++){
                tanulo[i] = new Tanulo();
            }



            index = 0;
            int j = 0;

            //////////////
            /////VEGIG MEGYEK AZ EXCELLEN
            ////////////
            for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator.hasNext();) {
                Row row = rowIterator.next();
                    index++;
                    j = 0;
                    for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext(); ) {
                        Cell cellData = cellIterator.next();
                        // todo work with the data
                        j++;
                        switch (j) {
                            case 1: tanulo[index].setAzonosito(cellData.toString());
                                    break;
                            case 2: tanulo[index].setNev(cellData.toString());
                                    break;
                            case 3: tanulo[index].setAnyanev(cellData.toString());
                                    break;
                            case 4: tanulo[index].setSzuletes(cellData.toString());
                                    break;
                            case 9: tanulo[index].setHely(cellData.toString());
                                    break;
                            default: break;
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
}
