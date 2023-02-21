package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    private static final String DECIMAL_TO_DECIMAL = "([0-9]+\\.[0-9]+) To ([0-9]+\\.[0-9]+)";
    private static final String DECIMAL_TO_DECIMAL_Das = "([0-9]+\\.[0-9]+)-([0-9]+\\.[0-9]+)";
    private static final String STRING_DASH_DECIMAL_DASH_DECIMAL = "^[A-Z]+-[0-9]\\.[0-9]+-[0-9]\\.[0-9]+\\s?$";

    private static final String STRING_DECIMAL = "^[A-Za-z]+[0-9]";

    //private static final String DATE_STRING = "[0-9]+[0-9]+-+[0-9]+[0-9]+-+[0-9]+[0-9]+[0-9]+[0-9]";
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MMM-yyyy");

    public static void main(String[] args) {
        String fileName = args[0];
        String extension = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf("."));
        String table = args[1];
        excelToCsvForXlsx(fileName, table, extension);
    }

    private static void parseDataForXlsx(List<XSSFSheet> list, String table, String extension)throws ParseException  {
        List<Map<String, List<Map<String, String>>>> sheetTable = new ArrayList<>();
        List<String[]> data = new ArrayList<>();
        for (Sheet sheet : list) {
            List<Map<String, String>> sheetItems = new ArrayList<>();
            List<String> header = new ArrayList<>();
            String pointer = "";
            Date date = new Date();

            if (table.equals("1")) {
                parseTableOne(header, pointer, sheet, sheetItems, date, extension);
            } else if (table.equals("2")) {
                try {
                    parseTableTwo(header, pointer, sheet, sheetItems, extension);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (table.equals("3")) {
                try{
                    parseTableThree(header, pointer, sheet, sheetItems, date, extension);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
//            else if (table.equals("4")) {
//                parseTableFour(header, pointer, sheet, sheetItems, extension);
//            }
                
//            } else if (table.equals("5") || table.equals("6")) {
//            	parseTableFive(data, sheet, extension);
//            }

            Map<String, List<Map<String, String>>> sheetMap = new HashMap<>();
            sheetMap.put(sheet.getSheetName(), sheetItems);
            sheetTable.add(sheetMap);
        }
        
        if(Integer.parseInt(table) >= 4) {
        	parseTableFive(data, list.get(0),Integer.parseInt(table), extension);
        	writeToCSV(data,Integer.parseInt(table),extension);
        }else {
        	convertToCsv(sheetTable, table,extension);
        }
        
    }

    private static void convertToCsv(List<Map<String, List<Map<String, String>>>> sheetTable, String table,String extension) throws ParseException {
        List<List<String>> csvTable = new ArrayList<>();
        if (table.equals("1")) {
            List<String> header = Arrays.asList("Sheet", "Pointer", "Clarity", "Color", "Price", "Font", "Date");
            for (Map<String, List<Map<String, String>>> sheet : sheetTable) {
                for (Map.Entry<String, List<Map<String, String>>> entry : sheet.entrySet()) {
                    for (Map<String, String> item : entry.getValue()) {
                        List<String> row = new ArrayList<>();
                        row.add(item.get("Sheet"));
                        row.add(item.get("Pointer"));
                        row.add(item.get("Clarity"));
                        row.add(item.get("Color"));
                        row.add(item.get("Price"));
                        row.add(item.get("Font"));
                        row.add(item.get("Date"));
                        csvTable.add(row);
                    }
                }
            }
            saveCsv(csvTable, header, table,extension);
        } else if (table.equals("2")) {
            List<String> header = Arrays.asList("rap_date", "shape", "pointer", "clarity", "cut", "color", "fls", "st_price", "st_back", "cert_cost", "font_price", "font_color_price", "font_back", "font_color_back");
            for (Map<String, List<Map<String, String>>> sheet : sheetTable) {
                for (Map.Entry<String, List<Map<String, String>>> entry : sheet.entrySet()) {
                    for (Map<String, String> item : entry.getValue()) {
                        List<String> row = new ArrayList<>();
                        row.add(sdf2.format(sdf.parse(item.get("rap_date"))));
                        row.add(item.get("shape"));
                        row.add(item.get("pointer"));
                        row.add(item.get("clarity"));
                        row.add(item.get("cut"));
                        row.add(item.get("color"));
                        row.add(item.get("fls"));
                        row.add(item.get("st_price"));
                        row.add(item.get("st_back"));
                        row.add(item.get("cert_cost"));
                        row.add(item.get("font_price"));
                        row.add(item.get("font_color_price"));
                        row.add(item.get("font_back"));
                        row.add(item.get("font_color_back"));
                        csvTable.add(row);
                    }
                }
            }
            saveCsv(csvTable, header, table, extension);
        } else if (table.equals("3")) {
            List<String> header = Arrays.asList("Sheet", "Pointer", "Clarity", "Cut", "Color", "Florescence", "Font", "Value", "Value_Color", "Date");
            for (Map<String, List<Map<String, String>>> sheet : sheetTable) {
                for (Map.Entry<String, List<Map<String, String>>> entry : sheet.entrySet()) {
                    for (Map<String, String> item : entry.getValue()) {
                        List<String> row = new ArrayList<>();
                        row.add(item.get("Sheet"));
                        row.add(item.get("Pointer"));
                        row.add(item.get("Clarity"));
                        row.add(item.get("Cut"));
                        row.add(item.get("Color"));
                        row.add(item.get("Florescence"));
                        row.add(item.get("Font"));
                        row.add(item.get("Value"));
                        row.add(item.get("Value_Color"));
                        row.add(item.get("Date"));
                        csvTable.add(row);
                    }
                }
            }
            saveCsv(csvTable, header, table,extension);
        } 
    }

    private static void writeToCSV(List<String[]> csvTable, int table,String extension) {
        try {
        	String fileName="";
        	if(table == 4)
        		fileName="lab_data";
        	else if(table==5)
        		fileName="mfg_data";
        	else if(table==6)
        		fileName="tsr_data";
        	else if (table==7)
        		fileName="purchase_data";
        	else if (table==8)
        		fileName="sale_data";
        	File targetDir = new File(extension);
        	if(!targetDir.exists()) {
        		targetDir.mkdirs();
        		System.out.println("created dir");
        	}
        	File targetFile = new File(targetDir,fileName+".csv");
        	 FileWriter csvWriter = new FileWriter(targetFile);
           // FileWriter csvWriter = new FileWriter(String.format(fileName+".csv", table));
           // csvWriter.append(String.join(",", header));
            //csvWriter.append("\n");
            for (String[] row : csvTable) {
                csvWriter.append(String.join(",", row));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
            System.out.println("Data inserted successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void saveCsv(List<List<String>> csvTable, List<String> header, String table,String extension) {
        try {
            FileWriter csvWriter = new FileWriter(String.format(extension+".csv", table));
            csvWriter.append(String.join(",", header));
            csvWriter.append("\n");
            for (List<String> row : csvTable) {
                csvWriter.append(String.join(",", row));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
            System.out.println("Data inserted successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseTableOne(List<String> header, String pointer, Sheet sheet, List<Map<String, String>> sheetItems, Date date, String extension) {
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell != null) {
                    header.add(cell.getStringCellValue());
                }
            }
            pointer = header.get(0);
            header = header.subList(1, header.size());
            break;
        }
        for (Row row : sheet) {
            if (row.getCell(0) == null) {
                break;
            }
            for (Cell cell : row) {
                if (cell.getCellType() == CellType.BLANK) {
                    continue;
                }
                if (cell.getRowIndex() == 0 || cell.getColumnIndex() == 0) {
                    continue;
                }
                CellStyle style = cell.getCellStyle();
                Font font = sheet.getWorkbook().getFontAt(style.getFontIndexAsInt());
                String fontStyle = "";
                if (font.getBold()) {
                    fontStyle = "bold".toUpperCase();
                } else if (font.getItalic()) {
                    fontStyle = "italic".toUpperCase();
                } else {
                    fontStyle = "normal".toUpperCase();
                }
                String price = cell.getCellType() == CellType.NUMERIC ? String.valueOf(cell.getNumericCellValue()) : cell.getStringCellValue();

                Map<String, String> rowMap = new HashMap<>();
                rowMap.put("rap_date", extension);
                rowMap.put("Sheet", sheet.getSheetName());
                rowMap.put("Pointer", pointer);
                rowMap.put("Clarity", sheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue());
                rowMap.put("Color", row.getCell(0).getStringCellValue());
                rowMap.put("Price", price);
                rowMap.put("Font", fontStyle);
                rowMap.put("Date", String.valueOf(date));
                sheetItems.add(rowMap);
            }
        }
    }

    private static boolean isRowEmpty(Row row) {
		boolean isEmpty = true;
		DataFormatter dataFormatter = new DataFormatter();

		if (row != null) {
			for (Cell cell : row) {
				if (dataFormatter.formatCellValue(cell).trim().length() > 0) {
					isEmpty = false;
					break;
				}
			}
		}

		return isEmpty;
	}
    
    private static boolean isCellDateFormatted(Cell cell) {
       // int formatIndex = cell.getCellStyle().getDataFormat();
        String format = cell.getCellStyle().getDataFormatString();
        return format.contains("yyyy") || format.contains("dd");
    }
    
    private static void parseTableFive(List<String[]> data,Sheet sheet, int table,String extension) throws ParseException {
    	int startPoint = 0,k=0;
    	for (Row row : sheet) {
            if(row.getCell(0).toString().equals("Sr")) {
                for (Cell cell : row) {
                    if(cell.getCellType() == CellType.BLANK) {
                        continue;
                    }
                    startPoint = cell.getRow().getRowNum();
                }
            }
        }
    	if(table == 5) {
    		k=2;
    	}else if(table == 6) {
    		k=3;
    		}
    	int numCells = sheet.getRow(startPoint).getPhysicalNumberOfCells();
    	String[] headerNames = new String[numCells + k];
        for (int i = 0; i < numCells; i++) {
            headerNames[i] = sheet.getRow(startPoint).getCell(i).getStringCellValue();
        }
        if(table == 5 || table == 6) {
        headerNames[numCells] = "last_modifiedby";
        headerNames[numCells + 1] = "last_modified_date";
        }
        if(table == 6) {
        	headerNames[numCells+2] = "ason";
        }
        
        data.add(headerNames);
        
        for(int i=startPoint+1;i<=sheet.getLastRowNum();i++) {
        	Row row = sheet.getRow(i);
        	if(isRowEmpty(row))
    			continue;
        	String[] rowData = new String[numCells + k];
        	 for (int j = 0; j < numCells; j++) {
                 Cell cell = row.getCell(j);
                 if (cell != null) {
                     if (cell.getCellType() == CellType.NUMERIC) {
                    	 if(isCellDateFormatted(cell)) {
                    		 Date date = cell.getDateCellValue();
                    		 rowData[j] = sdf2.format(date);
                    	 }else {
                         rowData[j] = cell.toString();
                    	 }
                    	 if(j==0)
                    		 rowData[j] = Integer.toString((int)cell.getNumericCellValue());
                     } else if (cell.getCellType() == CellType.STRING) {
                    	 try {
                    	 rowData[j] = sdf2.format(sdf3.parse(cell.toString()));
                    	 }catch(Exception e) {
                    		 if(!cell.getStringCellValue().isEmpty())
                    		     rowData[j] = "\""+cell.getStringCellValue()+"\"";
                    		 else
                    			 rowData[j] = cell.toString();
                    	 }
                    	 
                        // rowData[j] = "\""+cell.getStringCellValue()+"\"";
                     } else {
                    	 rowData[j] = cell.toString();
                     }
                 } else {
                     rowData[j] = "";
                 }
             }
        	 if(table == 5 || table == 6) {
        	 rowData[numCells] = Integer.toString(1);
        	 rowData[numCells+1] = sdf2.format(sdf.parse(extension));
        	 }if(table == 6) {
        		 rowData[numCells+2] = sdf2.format(sdf.parse(extension));
             }
        	 data.add(rowData);
        }
    }

    private static String getPointerIndex(Cell cell, List<Integer> pointerHeaderIndex, Sheet sheet) {
        Row sheetPointerHeaderRow = sheet.getRow(pointerHeaderIndex.get(0));
        int cellColumnIndex = cell.getColumnIndex();
        if (sheetPointerHeaderRow.getCell(cellColumnIndex).getCellType() == CellType.BLANK) {
            while (sheetPointerHeaderRow.getCell(cellColumnIndex).getCellType() == CellType.BLANK) {
                cellColumnIndex -= 1;
            }
        }
        return sheetPointerHeaderRow.getCell(cellColumnIndex).toString();
    }

    private static String getClarityIndex(Cell cell, int clarityHeaderRowIndex, Sheet sheet) {
        Row sheetClarityHeaderRow = sheet.getRow(clarityHeaderRowIndex);
        int cellColumnIndex = cell.getColumnIndex();
        if (sheetClarityHeaderRow.getCell(cellColumnIndex).getCellType() == CellType.BLANK) {
            while (sheetClarityHeaderRow.getCell(cellColumnIndex).getCellType() == CellType.BLANK) {
                cellColumnIndex -= 1;
            }
        }
        return sheetClarityHeaderRow.getCell(cellColumnIndex).toString();
    }

    private static String getCutIndex(Cell cell, int cutHeaderRowIndex, Sheet sheet) {
        Row sheetCutHeaderRow = sheet.getRow(cutHeaderRowIndex);
        int cellColumnIndex = cell.getColumnIndex();
        return sheetCutHeaderRow.getCell(cellColumnIndex).toString();
    }

//    private static String getHeaderIndex(Cell cell, int headerRowIndex, Sheet sheet) {
//        Row sheetHeaderRow = sheet.getRow(headerRowIndex);
//        int cellColumnIndex = cell.getColumnIndex();
//        return sheetHeaderRow.getCell(cellColumnIndex).toString();
//    }

    private static String getFlorescenceIndex(Cell cell, List<Integer> florescenceHeaderIndex, Sheet sheet) {
        int cellRowIndex = cell.getRowIndex();
        String sheetFlorescenceValue = sheet.getRow(cellRowIndex).getCell(florescenceHeaderIndex.get(1)).toString();
		
		if (sheetFlorescenceValue.equalsIgnoreCase("None"))
			sheetFlorescenceValue = "NON";
		else if (sheetFlorescenceValue.equalsIgnoreCase("Medium"))
			sheetFlorescenceValue = "MED";
		else if (sheetFlorescenceValue.equalsIgnoreCase("Faint"))
			sheetFlorescenceValue = "FNT";
		else if (sheetFlorescenceValue.equalsIgnoreCase("Strong"))
			sheetFlorescenceValue = "STG";
		 
        return sheetFlorescenceValue.toUpperCase();
    }

    private static String getColorIndex(Cell cell, List<Integer> colorHeaderIndex, Sheet sheet) {
        int cellRowIndex = cell.getRowIndex();
        boolean isSheetColorBlank = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).getCellType() == CellType.BLANK;
        boolean isSheetColorString = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).getCellType() == CellType.STRING;
        String sheetFlorescenceValue = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)+1).toString();
        String sheetColorValue = "";
        if (isSheetColorBlank && sheetFlorescenceValue.equals("None")) {
            while (sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).getCellType() == CellType.BLANK) {
                cellRowIndex += 1;
                sheetColorValue = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).toString();
            }
        } else if (isSheetColorBlank && sheetFlorescenceValue.equals("Faint")) {
            while (sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).getCellType() == CellType.BLANK) {
                cellRowIndex -= 1;
                sheetColorValue = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).toString();
            }
        } else if (isSheetColorBlank && sheetFlorescenceValue.equals("Medium")) {
            if (sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).getCellType() == CellType.BLANK) {
                cellRowIndex -= 2;
                if (sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).getCellType() == CellType.BLANK) {
                    cellRowIndex += 1;
                    sheetColorValue = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).toString();
                }
                sheetColorValue = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).toString();
            }
            sheetColorValue = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).toString();
        } else if (isSheetColorBlank && sheetFlorescenceValue.equals("Strong")) {
            if (sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).getCellType() == CellType.BLANK) {
                cellRowIndex -= 3;
                sheetColorValue = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).toString();
            }
            sheetColorValue = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).toString();
        } else if (isSheetColorString && sheetFlorescenceValue.equals("Faint")) {
            if (sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).getCellType() == CellType.STRING) {
                cellRowIndex -= 1;
                if (sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).getCellType() == CellType.BLANK) {
                    cellRowIndex += 1;
                    sheetColorValue = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).toString();
                }
                sheetColorValue = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).toString();
            }
            sheetColorValue = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).toString();
        } else if (isSheetColorString){
            sheetColorValue = sheet.getRow(cellRowIndex).getCell(colorHeaderIndex.get(1)).toString();
        }
        return sheetColorValue;
    }

    private static String getCellColor(Cell cell, Sheet sheet) {
        CellStyle style = cell.getCellStyle();
        if (style.getFillBackgroundColorColor() != null) {
            XSSFColor argbColor = XSSFColor.toXSSFColor(style.getFillForegroundColorColor());
            if (argbColor.getARGBHex().equals("FF000000")) {
                return "black".toUpperCase();
            } else if (argbColor.getARGBHex().equals("FF008000")) {
                return "green".toUpperCase();
            } else if (argbColor.getARGBHex().equals("FFFF0000")) {
                return "red".toUpperCase();
            } else if (argbColor.getARGBHex().equals("FF0000FF")) {
                return "blue".toUpperCase();
            } else {
                return "white".toUpperCase();
            }
        } else {
            return "white".toUpperCase();
        }

    }

    private static String getFontStyle(Cell cell, Sheet sheet) {
        CellStyle style = cell.getCellStyle();
        Font font = sheet.getWorkbook().getFontAt(style.getFontIndexAsInt());
        String fontStyle = "";
        if (font.getBold()) {
            fontStyle = "bold".toUpperCase();
        } else if (font.getItalic()) {
            fontStyle = "italic".toUpperCase();
        } else {
            fontStyle = "normal".toUpperCase();
        }
        return fontStyle;
    }

    private static void parseTableTwo(List<String> header, String pointer, Sheet sheet, List<Map<String, String>> sheetItems, String extension) {
        // clarityHeader is set of clarity headers
        List<Integer> pointerHeaderIndex = new ArrayList<>();
        String pointerHeader = "";
        Set<String> clarityHeader = new HashSet<>();
        int clarityHeaderRowIndex = 0;
        Set<String> cutHeader = new HashSet<>();
        int cutHeaderRowIndex = 0;
        List<Integer> colorHeaderIndex = new ArrayList<>();
        List<Integer> florescenceHeaderIndex = new ArrayList<>();
        List<String> florescenceHeader = new ArrayList<>();
        florescenceHeader.add("None");
        florescenceHeader.add("Faint");
        florescenceHeader.add("Medium");
        florescenceHeader.add("Strong");

        String value = null;
        for (int l = 0; l <= sheet.getLastRowNum(); l++) {
            int row = 0;
            try {
                if(sheet.getRow(l).getCell(1).toString() == null) {
                    continue;
                }
                if(sheet.getRow(l).getCell(1).toString().contains("CERTIFICATE COST:")) {
                    row = sheet.getRow(l).getRowNum();
                }
            } catch (Exception e) {
                continue;
            }
            if(l == row) {
                for(int k = sheet.getRow(l).getFirstCellNum(); k < sheet.getRow(l).getLastCellNum(); k++) {
                    Cell cell = sheet.getRow(l).getCell(k);
                    try {
                    	if(cell.getCellType() == CellType.STRING) {
                    		value = cell.toString().substring(cell.toString().lastIndexOf(':')+1).replaceAll("[^0-9]", "");
                    	}
                        if(cell.getCellType() == CellType.NUMERIC) {
                            value = cell.toString();
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
            }
        }
    //    System.out.println("Cert" + value);


        for (Row row : sheet) {
            if (row.getCell(0) == null) {
                continue;
            }
            if (row.getCell(0).toString().equals("Range =>")) {
                for (Cell cell : row) {
                    if (cell.toString().isEmpty()) {
                        continue;
                    }
                    pointerHeaderIndex.add(cell.getRow().getRowNum());
                    pointerHeader = sheet.getRow(cell.getRow().getRowNum()).getCell(2).toString();
                }
            } else {
                continue;
            }
        }

        // Loop through the sheet and get the data
        for (Row row : sheet) {
            if (row.getCell(0) == null) {
                continue;
            }
            if (row.getCell(0).toString().equals("Clarity =>")) {
                for (Cell cell : row) {
                    if (cell.toString().isEmpty()) {
                        continue;
                    }
                    clarityHeader.add(cell.toString());
                    clarityHeaderRowIndex = cell.getRow().getRowNum();
                }
            } else if (row.getCell(0).toString().equals("Cut =>")) {
                for (Cell cell : row) {
                    if (cell.toString().isEmpty()) {
                        continue;
                    }
                    cutHeader.add(cell.toString());
                    cutHeaderRowIndex = cell.getRow().getRowNum();
                }
            } else if (row.getCell(0).toString().equals("Color")) {
                for (Cell cell : row) {
                    if (cell.toString().isEmpty()) {
                        continue;
                    }
                    if (cell.toString().equals("Color")) {
                        colorHeaderIndex.add(cell.getRow().getRowNum());
                        colorHeaderIndex.add(cell.getColumnIndex());
                    } else if (cell.toString().equals("Florescence")) {
                        florescenceHeaderIndex.add(cell.getRow().getRowNum());
                        florescenceHeaderIndex.add(cell.getColumnIndex());
                    }
                }
            } else {
                continue;
            }
        }

        for (int k = colorHeaderIndex.get(2) + 1; k < sheet.getLastRowNum(); k++) {
            int row = sheet.getRow(k).getRowNum();
            try {
                if (sheet.getRow(k).getCell(1).getCellType() == CellType.BLANK) {
                    row -= 1;
                    break;
                }
            } catch (Exception e) {
                break;
            }
            int i = k - ((colorHeaderIndex.get(2) + 1) - (colorHeaderIndex.get(0) + 1));
            try {
                for (int j = 2; j < sheet.getRow(i).getLastCellNum(); j++) {
                    Cell cell = sheet.getRow(i).getCell(j);
                    try {
                        if (cell == null) {
                            continue;
                        }
                        if (cell.getCellType() == CellType.STRING) {
                            continue;
                        }



                        String pointerIndex = getPointerIndex(cell, pointerHeaderIndex, sheet);
                        String clarityIndex = getClarityIndex(cell, clarityHeaderRowIndex, sheet);
                        String cutIndex = getCutIndex(cell, cutHeaderRowIndex, sheet);
                        String florescenceIndex = getFlorescenceIndex(cell, florescenceHeaderIndex, sheet);
                        String colorIndex = getColorIndex(cell, colorHeaderIndex, sheet);
                        String cellColor = getCellColor(cell, sheet);
                        String fontStyle = getFontStyle(cell, sheet);

                        String shapeName = null;
                        String replaceName = null;


                        Cell cells = sheet.getRow(k).getCell(j);
                        String cellColors = getCellColor(cells, sheet);
                        String fontStyles = getFontStyle(cells, sheet);
                        replaceName = pointerIndex.replace("TO", "-").replace("To", "-").replace("(", "").replace(")", "").replaceAll("\\s", "");

                        Map<String, String> rowDict = new HashMap<>();
                        rowDict.put("rap_date", extension);
                        if (sheet.getSheetName().matches(DECIMAL_TO_DECIMAL)) {
                            shapeName = "ROUND";
                            rowDict.put("shape", shapeName);
                            rowDict.put("pointer", replaceName);
                        } else if (sheet.getSheetName().matches(STRING_DASH_DECIMAL_DASH_DECIMAL)) {
                            shapeName = sheet.getSheetName().substring(0, sheet.getSheetName().indexOf("-"));
                            rowDict.put("shape", shapeName);
                            rowDict.put("pointer", replaceName);
                        } else if (sheet.getSheetName().matches(DECIMAL_TO_DECIMAL_Das)) {
                            shapeName = "ROUND";
                            rowDict.put("shape", shapeName);
                            rowDict.put("pointer", pointerIndex.replace("(", "").replace(")", "").replaceAll("\\s", ""));
                        }
                        if(!colorIndex.equalsIgnoreCase("Range =>")) {
                        rowDict.put("clarity", clarityIndex);
                        rowDict.put("cut", cutIndex);
                        rowDict.put("color", colorIndex);
                        rowDict.put("fls", florescenceIndex);
                        rowDict.put("st_back", cell.toString());
                        rowDict.put("cert_cost", value);
                        rowDict.put("font_back", fontStyle);
                        rowDict.put("font_color_back", cellColor);
                        rowDict.put("st_price", cells.toString());
                        rowDict.put("font_price", fontStyles);
                        rowDict.put("font_color_price", cellColors);
                        sheetItems.add(rowDict);
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
    }

    private static void parseTableThree(List<String> header, String pointer, Sheet sheet, List<Map<String, String>> sheetItems, Date date, String extension) {
        // clarityHeader is set of clarity headers
        List<Integer> pointerHeaderIndex = new ArrayList<>();
        String pointerHeader = "";
        Set<String> clarityHeader = new HashSet<>();
        int clarityHeaderRowIndex = 0;
        Set<String> cutHeader = new HashSet<>();
        int cutHeaderRowIndex = 0;
        List<Integer> colorHeaderIndex = new ArrayList<>();
        List<Integer> florescenceHeaderIndex = new ArrayList<>();
        List<String> florescenceHeader = new ArrayList<>();
        florescenceHeader.add("None");
        florescenceHeader.add("Faint");
        florescenceHeader.add("Medium");
        florescenceHeader.add("Strong");
        for (Row row : sheet) {
            if (row.getCell(0) == null) {
                continue;
            }
            if (row.getCell(0).toString().equals("Range =>")) {
                for (Cell cell : row) {
                    if (cell.toString().isEmpty()) {
                        continue;
                    }
                    pointerHeaderIndex.add(cell.getRow().getRowNum());
                    pointerHeader = sheet.getRow(cell.getRow().getRowNum()).getCell(2).toString();
                }
            }
        }

        // Loop through the sheet and get the data
        for (Row row : sheet) {
            if (row.getCell(0) == null) {
                continue;
            }
            if (row.getCell(0).toString().equals("Clarity =>")) {
                for (Cell cell : row) {
                    if (cell.toString().isEmpty()) {
                        continue;
                    }
                    clarityHeader.add(cell.toString());
                    clarityHeaderRowIndex = cell.getRow().getRowNum();
                }
            } else if (row.getCell(0).toString().equals("Cut =>")) {
                for (Cell cell : row) {
                    if (cell.toString().isEmpty()) {
                        continue;
                    }
                    cutHeader.add(cell.toString());
                    cutHeaderRowIndex = cell.getRow().getRowNum();
                }
            } else if (row.getCell(0).toString().equals("Color")) {
                for (Cell cell : row) {
                    if (cell.toString().isEmpty()) {
                        continue;
                    }
                    if (cell.toString().equals("Color")) {
                        colorHeaderIndex.add(cell.getRow().getRowNum());
                        colorHeaderIndex.add(cell.getColumnIndex());
                    } else if (cell.toString().equals("Florescence")) {
                        florescenceHeaderIndex.add(cell.getRow().getRowNum());
                        florescenceHeaderIndex.add(cell.getColumnIndex());
                    }
                }
            } else {
                continue;
            }
        }

        for (int i = colorHeaderIndex.get(2) + 1; i < sheet.getLastRowNum(); i++) {
            int row = sheet.getRow(i).getRowNum();
            try {
                if (sheet.getRow(i).getCell(1).getCellType() == CellType.BLANK) {
                    row -= 1;
                    break;
                }
            } catch (Exception e) {
                break;
            }
            try {
                for (int j = 2; j < sheet.getRow(i).getLastCellNum(); j++) {
                    Cell cell = sheet.getRow(i).getCell(j);
                    try {
                        if (cell == null) {
                            continue;
                        }
                        String pointerIndex = getPointerIndex(cell, pointerHeaderIndex, sheet);
                        String clarityIndex = getClarityIndex(cell, clarityHeaderRowIndex, sheet);
                        String cutIndex = getCutIndex(cell, cutHeaderRowIndex, sheet);
                        String florescenceIndex = getFlorescenceIndex(cell, florescenceHeaderIndex, sheet);
                        String colorIndex = getColorIndex(cell, colorHeaderIndex, sheet);
                        String cellColor = getCellColor(cell, sheet);
                        String fontStyle = getFontStyle(cell, sheet);
                        Map<String, String> rowDict = new HashMap<>();
                        rowDict.put("rap_date", extension);
                        rowDict.put("Sheet", sheet.getSheetName());
                        rowDict.put("Pointer", pointerIndex);
                        rowDict.put("Clarity", clarityIndex);
                        rowDict.put("Cut", cutIndex);
                        rowDict.put("Color", colorIndex);
                        rowDict.put("Florescence", florescenceIndex);
                        rowDict.put("Font", fontStyle);
                        rowDict.put("Value", (cell.getCellType() == CellType.BLANK || cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.STRING) && cell.toString().isEmpty() ? "NONE" : cell.toString());
                        rowDict.put("Value_Color", cellColor);
                        sheetItems.add(rowDict);
                    } catch (Exception e) {
                        break;
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
    }

    private static void excelToCsvForXlsx(String filePath, String table, String extension) {
        try {
           // ClassLoader classLoader = Main.class.getClassLoader();
            //System.out.println("class path" + classLoader.getResource(filePath).getFile());
           // File file = new File(Objects.requireNonNull(classLoader.getResource(filePath)).getFile());
        	File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            List<XSSFSheet> sheets = new ArrayList<>();
            if(Integer.parseInt(table) < 4) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                String sheetName = workbook.getSheetName(i);
                System.out.println("sheetname:: "+sheetName);
                if (Pattern.matches(DECIMAL_TO_DECIMAL, sheetName) || Pattern.matches(STRING_DASH_DECIMAL_DASH_DECIMAL, sheetName) || Pattern.matches(STRING_DECIMAL, sheetName) || Pattern.matches(DECIMAL_TO_DECIMAL_Das, sheetName)) {
                    sheets.add(workbook.getSheet(sheetName));
                }
            }
        }
            if(Integer.parseInt(table) >= 4) {
            	sheets.add(workbook.getSheet(workbook.getSheetName(0)));
            }
            System.out.println("Total number of sheets: " + sheets.size());
            parseDataForXlsx(sheets, table, extension);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

