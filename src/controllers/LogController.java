package controllers;

import views.MainWindow;
import views.logger_panel.LoggerPanel;

public class LogController {
    private LoggerPanel loggerPanel;

    public LogController(MainWindow mw) {
        this.loggerPanel = mw.getLoggerPanel();
    }

    public void logInfo(String log) {
        loggerPanel.addLog(log);
    }

    public void logError(String log) {
        loggerPanel.addErrorLog(log);
    }


}
