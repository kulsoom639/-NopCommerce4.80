package Utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException {
    	String filePath = System.getProperty("user.dir") + "/src/test/testData/Login_DDT_TestData_Cleaned.xlsx";
    	ExcelUtility xlutil = new ExcelUtility(filePath);



        int totalRows = xlutil.getRowCount("Sheet1");
        int totalCols = xlutil.getCellCount("Sheet1", 1);

        String[][] loginData = new String[totalRows][totalCols];

        for (int i = 1; i <= totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                loginData[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
            }
        }

        return loginData;
    }
}
