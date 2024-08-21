package org.java.mentorship.calculation.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GenerateWeeklyCashFlowReportTest {

    private final GenerateWeeklyCashFlowReport generateWeeklyCashFlowReport = new GenerateWeeklyCashFlowReport();

    @Test
    void generateWeeklyCashFlowReportShouldGenerateReport() {
        Assertions.assertDoesNotThrow(generateWeeklyCashFlowReport::generateWeeklyCashFlowReport);
    }

}
