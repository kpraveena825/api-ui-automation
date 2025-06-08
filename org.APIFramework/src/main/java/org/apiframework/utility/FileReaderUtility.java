package org.apiframework.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.path.json.JsonPath;

public class FileReaderUtility {
	private static Properties prop = null;
	static JSONParser parser = new JSONParser();

	// Base path for resources directory
	private static final String RESOURCE_PATH = System.getProperty("user.dir") + "/src/test/resources/";
	private static final String REQUEST_PAYLOADS_FOLDER = "RequestPayloads/";
	private static final String RESPONSE_EVIDENCE_FOLDER = "ResponseEvidance/";
	private static final String STATIC_MAP_FILE = "staticMap.xlsx";

	/**
	 * Converts a raw string into a JsonPath object for easy querying.
	 *
	 * @param response The raw JSON string response from an API.
	 * @return JsonPath object to facilitate JSON data extraction.
	 */
	public static JsonPath rawToJson(String response) {
		return new JsonPath(response);
	}

	/**
	 * Constructs and returns file path.
	 *
	 * @param fileType The filename or file type to locate within the resource
	 *                 folder.
	 * @return Full path string pointing to the requested file.
	 */
	public static String getFiles(String fileType) {
		String path = RESOURCE_PATH + REQUEST_PAYLOADS_FOLDER + fileType;
		return path;
	}

	/**
	 * Loads test data from a JSON file located in the resource folder.
	 *
	 * @param filename The name of the JSON file to load.
	 * @return JSONObject representing the parsed JSON test data.
	 */
	public static JSONObject loadTestData(String filename) {
		try {
			String path = getFiles(filename);
			System.out.print("printing path ******" + path);
			JSONParser parser = new JSONParser();
			return (JSONObject) parser.parse(new FileReader(path));
		} catch (Exception e) {
			throw new RuntimeException("Failed to load test data: " + e.getMessage());
		}
	}

	/**
	 * Reads properties from a .properties file
	 * 
	 * @param fileName : property file name
	 */
	public static Properties readPropertiesFile(String fileName) throws IOException {
		try (FileInputStream fis = new FileInputStream(RESOURCE_PATH + fileName)) {
			prop = new Properties();
			prop.load(fis);
			return prop;
		}
	}

	/**
	 * Reads .json payloads form resource folder
	 * 
	 * @param fileName : Json file name
	 */
	public static Object jsonReader(String fileName) throws FileNotFoundException, IOException, ParseException {
		return parser.parse(new FileReader(RESOURCE_PATH + REQUEST_PAYLOADS_FOLDER + fileName));
	}

	/**
	 * Reads .json payload content and converts to String
	 * 
	 * @param fileName : Json file name
	 */
	public static String getPayload(String fileName) throws IOException, ParseException {
		JSONObject jsonObject = (JSONObject) jsonReader(fileName);
		return jsonObject.toString();
	}

	/**
	 * Updates and reads .json payload content and converts to String
	 * 
	 * @param fileName : Json file name
	 */
	public static Object buildUpdatedLocationPayload(String fileName, Map<String, Object> fieldsToUpdate)
			throws FileNotFoundException, IOException, ParseException {

		JSONObject jsonObject = (JSONObject) jsonReader(fileName);
		for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}
		return jsonObject.toString();
	}

	/**
	 * Reads an Excel file and returns a map of field name → field value
	 * 
	 * @param: takes excel sheetName which is same as json payload filename as
	 *               argument
	 */
	public static LinkedHashMap<String, String> readStaticMapFromExcel(String sheetName, String testCaseId)
			throws IOException {
		LinkedHashMap<String, String> data = new LinkedHashMap<>();

		try (FileInputStream fis = new FileInputStream(RESOURCE_PATH + STATIC_MAP_FILE);
				XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			Row headerRow = sheet.getRow(0);
			int colCount = headerRow.getLastCellNum();

			// Find TestCaseID column index
			int testCaseIdCol = -1;
			for (int i = 0; i < colCount; i++) {
				if ("TestCaseID".equalsIgnoreCase(headerRow.getCell(i).getStringCellValue().trim())) {
					testCaseIdCol = i;
					break;
				}
			}
			if (testCaseIdCol == -1)
				throw new RuntimeException("TestCaseID column not found");

			// Loop through rows to find matching TestCaseID
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				Cell testCaseCell = row.getCell(testCaseIdCol);
				if (testCaseCell == null)
					continue;

				testCaseCell.setCellType(CellType.STRING);
				if (testCaseId.equalsIgnoreCase(testCaseCell.getStringCellValue().trim())) {
					// Extract all column data for this row
					for (int j = 0; j < colCount; j++) {
						String key = headerRow.getCell(j).getStringCellValue();
						Cell cell = row.getCell(j);
						String value = cell == null ? "" : cell.toString();
						data.put(key, value);
					}
					break;
				}
			}
		}

		if (data.isEmpty()) {
			throw new RuntimeException("Test case ID '" + testCaseId + "' not found in sheet '" + sheetName + "'");
		}

		return data;
	}

	/** Writes JsonPath response to a JSON file under ResponseEvidance folder */
	public static void writeJson(JsonPath js, String fileName) throws IOException {

		FileWriter file = new FileWriter(RESOURCE_PATH + RESPONSE_EVIDENCE_FOLDER + fileName + "Response.json", true);
		file.write(js.get().toString());
		file.close();
	}

	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		try {
			FileReaderUtility.prop = prop;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
