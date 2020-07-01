package SoFT.excel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import SoFT.data.ArendeData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelHandler {

    public Sheet readExcel(String filePath,String fileName,String sheetName) throws IOException{
        //Create a object of File class to open xlsx file
        File file =    new File(filePath+"\\"+fileName);
        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = null;
        //Find the file extension by spliting file name in substing and getting only extension name
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        //Check condition if the file is xlsx file
        if(fileExtensionName.equals(".xlsx")){
            //If it is xlsx file then create object of XSSFWorkbook class
            workbook = new HSSFWorkbook(inputStream);
        }
        //Check condition if the file is xls file
        else if(fileExtensionName.equals(".xls")){
            //If it is xls file then create object of XSSFWorkbook class
            workbook = new HSSFWorkbook(inputStream);
        }
        //Read sheet inside the workbook by its name
        Sheet worksheet = workbook.getSheet(sheetName);
        return worksheet;
    }

    public void writeExcel(String filePath, String fileName, String sheetName, List<ArendeData> arendeData) throws IOException{
        //Create an object of File class to open xlsx file
        File file =    new File(filePath+"\\"+fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = null;

        //Find the file extension by spliting file name in substing and getting only extension name
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        //Check condition if the file is xlsx file
        if(fileExtensionName.equals(".xlsx")){
            //If it is xlsx file then create object of XSSFWorkbook class
            workbook = new HSSFWorkbook(inputStream);
        }
        //Check condition if the file is xls file
        else if(fileExtensionName.equals(".xls")){
            //If it is xls file then create object of XSSFWorkbook class
            workbook = new HSSFWorkbook(inputStream);
        }

        //Read excel sheet by sheet name

        Sheet sheet = workbook.getSheet(sheetName);


        //Get the current count of rows in excel file


        int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

        for(int j = 1; j < rowCount+1; j++) {
            Row row = sheet.getRow(j);
            row.getCell(0).setCellValue(arendeData.get(j-1).Personnummer);
            row.getCell(1).setCellValue(arendeData.get(j-1).skapaFTJTILL);


            if ((row.getCell(3) == null)) {
                row.createCell(3).setCellValue(arendeData.get(j - 1).resenarSkapad);
            } else {
                row.getCell(3).setCellValue(arendeData.get(j - 1).resenarSkapad);
            }


            String arendeID = arendeData.get(j - 1).FTJArendeId;
            if(row.getCell(4) == null) {
                if (!arendeData.get(j - 1).FTJArendeId.equals(""))
                {
                    row.createCell(4).setCellValue(arendeID);

                }
                else
                {
                    row.createCell(4).setCellValue("EMPTY");;
                }
            }
            else
            {
                if (!arendeData.get(j - 1).FTJArendeId.equals(""))
                {
                    row.getCell(4).setCellValue(arendeID);

                }
                else
                {
                    row.getCell(4).setCellValue("EMPTY");
                }
            }


            if(row.getCell(5) == null)
                if(!arendeData.get(j-1).SJRArendeId.equals("")) {
                    row.createCell(5).setCellValue(arendeData.get(j - 1).SJRArendeId);
                }
                else
                    row.createCell(5).setCellValue(arendeData.get(j - 1).SJRArendeId);

            if(row.getCell(6) == null)
            {
                if (arendeData.get(j - 1).Kundnummer == "")
                    row.createCell(6).setCellValue("EMPTY");
                else
                    row.createCell(6).setCellValue(arendeData.get(j - 1).Kundnummer);
            }
            else
            {
                if (arendeData.get(j - 1).Kundnummer != "")
                    row.getCell(6).setCellValue(arendeData.get(j - 1).Kundnummer);
            }

            if(row.getCell(7) == null)
            {
                if (arendeData.get(j - 1).tillstandFTJId == "")
                    row.createCell(7).setCellValue("EMPTY");
                else
                    row.createCell(7).setCellValue(arendeData.get(j - 1).tillstandFTJId);
            }
            else
            {
                if (arendeData.get(j - 1).tillstandFTJId != "")
                    row.getCell(7).setCellValue(arendeData.get(j - 1).tillstandFTJId);
            }

            if(row.getCell(8) == null)
            {
                if (arendeData.get(j - 1).tillstandSJRId == "")
                    row.createCell(8).setCellValue("EMPTY");
                else
                    row.createCell(8).setCellValue(arendeData.get(j - 1).tillstandSJRId);
            }
            else
            {
                if (arendeData.get(j - 1).tillstandSJRId != "")
                    row.getCell(8).setCellValue(arendeData.get(j - 1).tillstandSJRId);
            }

            if(row.getCell(9) == null)
            {
                if (arendeData.get(j - 1).kortnummerFTJ == "")
                    row.createCell(9).setCellValue("EMPTY");
                else
                    row.createCell(9).setCellValue(arendeData.get(j - 1).kortnummerFTJ);
            }
            else
            {
                if (arendeData.get(j - 1).kortnummerFTJ != "")
                    row.getCell(9).setCellValue(arendeData.get(j - 1).kortnummerFTJ);
            }

            if(row.getCell(10) == null)
            {
                if (arendeData.get(j - 1).kortnummerSJR == "")
                    row.createCell(10).setCellValue("EMPTY");
                else
                    row.createCell(10).setCellValue(arendeData.get(j - 1).kortnummerSJR);
            }
            else
            {
                if (arendeData.get(j - 1).kortnummerSJR != "")
                    row.getCell(10).setCellValue(arendeData.get(j - 1).kortnummerSJR);
            }

            if(row.getCell(21) == null)
            {
                if (arendeData.get(j - 1).beslutatKod == "")
                    row.createCell(21).setCellValue("EMPTY");
                else
                    row.createCell(21).setCellValue(arendeData.get(j - 1).beslutatKod);
            }
            else
            {
                if (arendeData.get(j - 1).beslutatKod != "")
                    row.getCell(21).setCellValue(arendeData.get(j - 1).beslutatKod);
                else
                    row.getCell(21).setCellValue("EMPTY");
            }

            if(row.getCell(22) == null)
            {
                if (arendeData.get(j - 1).idemiaData == "")
                    row.createCell(22).setCellValue("EMPTY");
                else
                    row.createCell(22).setCellValue(arendeData.get(j - 1).idemiaData);
            }
            else
            {
                if (arendeData.get(j - 1).idemiaData != "")
                    row.getCell(22).setCellValue(arendeData.get(j - 1).idemiaData);
            }

        }


        //Close input stream

        inputStream.close();

        //Create an object of FileOutputStream class to create write data in excel file

        FileOutputStream outputStream = new FileOutputStream(file);

        //write data in the excel file

        workbook.write(outputStream);

        //close output stream
        outputStream.close();
    }
}