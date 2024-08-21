package org.java.mentorship.calculation.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GenerateWeeklyCashFlowReport {

    // At 07:30 every 7 days
    @Scheduled(cron = "0 30 7 */7 * *")
    public void generateWeeklyCashFlowReport() {
        System.out.println("Generate weekly cash flow report");
    }

}
