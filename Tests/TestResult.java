package Tests;

public class TestResult {

    String name;
    Boolean result;

    public TestResult(String name, Boolean result) {
        this.name = name;
        this.result = result;
    }

    public String getTestName() {
        return name;
    }

    public void setTestName(String name) {
        this.name = name;
    }

    public Boolean getTestResult() {
        return result;
    }

    public void setTestResult(Boolean result) {
        this.result = result;
    }
}