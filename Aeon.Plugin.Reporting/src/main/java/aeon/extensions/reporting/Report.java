package aeon.extensions.reporting;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private String suiteName;
    private int total = 0;
    private int passed = 0;
    private int failed = 0;
    private int skipped = 0;
    private int broken = 0;
    private List<Scenario> scenarioBeans;
    private String totalTime;

    public String getSuiteName() {
        return this.suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public int getTotal() {
        return this.total;
    }

    public int getPassed() {
        return this.passed;
    }

    public int getFailed() {
        return this.failed;
    }

    public int getSkipped() {
        return this.skipped;
    }

    public int getBroken() {
        return this.broken;
    }

    public List<Scenario> getScenarios() {
        if (this.scenarioBeans == null) {
            this.scenarioBeans = new ArrayList<>();
        }
        return this.scenarioBeans;
    }

    public void addPass() {
        this.total = this.getTotal() + 1;
        this.passed = this.getPassed() + 1;
    }

    public void addFailed() {
        this.total = this.getTotal() + 1;
        this.failed = this.getFailed() + 1;
    }

    public void addSkipped() {
        this.total = this.getTotal() + 1;
        this.skipped = this.getSkipped() + 1;
    }

    public void addBroken() {
        this.total = this.getTotal() + 1;
        this.broken = this.getBroken() + 1;
    }

    public String toString() {
        return "Report{total=" + this.total + ", passed=" + this.passed + ", failed=" + this.failed + '}';
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getTotalTime() {
        return this.totalTime;
    }

    public boolean isSuiteFailed() {
        return this.failed >= 1;
    }

    public boolean isSuiteBroken() {
        return this.broken >= 1;
    }

    public boolean isSuitePassed() {
        return this.passed >= 1;
    }

    public boolean isSuiteSkipped() {
        return this.skipped >= 1;
    }
    public List<Scenario> getScenarioBeans() {
        if (this.scenarioBeans == null) {
            this.scenarioBeans = new ArrayList<>();
        }
        return this.scenarioBeans;
    }
}


