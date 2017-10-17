import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class Main {

    public static void main(String args[]) throws IOException {
        ReadExcel readExcel = new ReadExcel();

//        if (readExcel.read() == true){
//            System.out.println("Read oke");
//        }

        //System.out.println(readExcel.tanulo[1].getNev());

//        for (int i = 0; i < 10; i++){
//            System.out.println(readExcel.tanulo[i].getAzonosito());
//            System.out.println(readExcel.tanulo[i].getNev());
//            System.out.println(readExcel.tanulo[i].getAnyanev());
//            System.out.println(readExcel.tanulo[i].getSzuletes());
//            System.out.println(readExcel.tanulo[i].getHely());
//            System.out.println();
//        }


//        WritePDF writePDF = new WritePDF();
//        if (writePDF.write(readExcel) == true){
//            System.out.println("write okay");
//        }

//        ModifyPDF modifyPDF = new ModifyPDF();
//        if (modifyPDF.modify(readExcel) == true){
//            System.out.println("modify okay");
//        }


        ReadExcelJo readExcelJo = new ReadExcelJo();

//        if (readExcel.read() == true){
//            System.out.println("Read oke");
//        }

        //String melyiken = "SZILVER";

        //String destination = "/Users/istvan/Documents/kir/Telephelyek/";
        //destination += melyiken + "/";


        //String szilverClassic = "/Users/istvan/GitHub/tableController/src/main/java/";
        //szilverClassic += melyiken + ".xls";

        String szilverClassic = "/Users/istvan/GitHub/tableController/src/main/java/SZILVER.xls";
        String destination = "/Users/istvan/Documents/kir/Telephelyek/proba/";

        File path = new File(destination);

        File [] files = path.listFiles();
        for (int i = 0; i < files.length; i++){
            //a mac-es DS storet is kikell szurni...
            if (files[i].isFile() && files[i].toString().indexOf("DS_Store") == -1){ //this line weeds out other directories/folders
                System.out.println("\nFile: " + files[i]);
                if (readExcelJo.readExceljo(files[i].toString(), szilverClassic) == true){
                    System.out.println("Read oke\n\n");
                }
            }
        }

        System.out.println("PROGRAM EXIT");

    }
}
