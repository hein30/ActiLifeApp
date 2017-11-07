package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import models.Subject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Static class that reads excel/csv file and return List of subjects in the file.
 */
public class ImportDataReader {

    private ImportDataReader() {
        //hiding constructor.
    }

    public static List<Subject> readImportedFile(File importFile) throws IOException {

        Map<String, Subject> filenameMap = new TreeMap<>();

        FileInputStream excelFile = new FileInputStream(importFile);
        Workbook workbook = new XSSFWorkbook(excelFile);
        XSSFSheet dataTypeSheet = (XSSFSheet) workbook.getSheet("Daily");

        Iterator<Row> rows = dataTypeSheet.rowIterator();

        Row headerRow = rows.next();

        int colIdOfFileName = getColIdOfHeader("Filename", headerRow);
        int colIdOfSubject = getColIdOfHeader("Subject", headerRow);
        int colIdOfSedentary = getColIdOfHeader("Sedentary", headerRow);
        int colIdOfLight = getColIdOfHeader("Light", headerRow);
        int colIdOfModerate = getColIdOfHeader("Moderate", headerRow);
        int colIdOfVigorous = getColIdOfHeader("Vigorous", headerRow);
        int colIdOfOrderOfDays = getColIdOfHeader("Day of Week", headerRow);

        while (rows.hasNext()) {
            Row currentRow = rows.next();
            String subjectId = currentRow.getCell(colIdOfSubject).getStringCellValue();
            String fileName = currentRow.getCell(colIdOfFileName).getStringCellValue();

            //add new Subject if it is not already in the list.
            filenameMap.computeIfAbsent(fileName, s -> new Subject(fileName, subjectId));
            Subject currentSubject = filenameMap.get(fileName);

            currentSubject.addSendentary(roundedValue(currentRow.getCell(colIdOfSedentary).getNumericCellValue()));
            currentSubject.addLight(roundedValue(currentRow.getCell(colIdOfLight).getNumericCellValue()));
            currentSubject.addModerate(roundedValue(currentRow.getCell(colIdOfModerate).getNumericCellValue()));
            currentSubject.addVigorous(roundedValue(currentRow.getCell(colIdOfVigorous).getNumericCellValue()));
            currentSubject.addDay(currentRow.getCell(colIdOfOrderOfDays).getStringCellValue());
        }
        
        workbook.close();

        return new ArrayList<>(filenameMap.values());
    }

    private static double roundedValue(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private static int getColIdOfHeader(String header, Row headerRow) {

        Iterator<Cell> cellIterator = headerRow.cellIterator();

        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();
            if (currentCell.getStringCellValue().contentEquals(header)) {
                return currentCell.getColumnIndex();
            }
        }

        //todo throw exception.
        return 0;
    }


}
