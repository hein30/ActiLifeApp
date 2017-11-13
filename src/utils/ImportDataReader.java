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

    public static final String FILENAME = "Filename";
    public static final String SUBJECT = "Subject";
    public static final String SEDENTARY = "Sedentary";
    public static final String LIGHT = "Light";
    public static final String MODERATE = "Moderate";
    public static final String VIGOROUS = "Vigorous";
    public static final String DAY_OF_WEEK = "Day of Week";

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

        int colIdOfFileName = getColIdOfHeader(FILENAME, headerRow);
        int colIdOfSubject = getColIdOfHeader(SUBJECT, headerRow);
        int colIdOfSedentary = getColIdOfHeader(SEDENTARY, headerRow);
        int colIdOfLight = getColIdOfHeader(LIGHT, headerRow);
        int colIdOfModerate = getColIdOfHeader(MODERATE, headerRow);
        int colIdOfVigorous = getColIdOfHeader(VIGOROUS, headerRow);
        int colIdOfOrderOfDays = getColIdOfHeader(DAY_OF_WEEK, headerRow);

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
