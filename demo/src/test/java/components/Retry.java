package components;

import org.testng.IRetryAnalyzer;

public class Retry implements IRetryAnalyzer {
    private int count = 0;
    private static final int MAX_RETRY = 1;

    @Override
    public boolean retry(org.testng.ITestResult result) {
        if (count < MAX_RETRY) {
            count++;
            return true; // Thử lại test case
        }
        return false; // Không thử lại nữa
    }

}
