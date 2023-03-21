package seedu.rainyDay.command;

import seedu.rainyDay.data.FinancialStatement;
import seedu.rainyDay.data.FlowDirection;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class EditCommand extends Command {
    private static final Logger logger = Logger.getLogger(EditCommand.class.getName());
    private int index;
    private String description;
    private String flowDirection;
    private double value;
    private String category;
    private String flag = "";
    private String fieldToChange;
    private Double valueToChange;

    public EditCommand(int index, String description, String flowDirection, double value, String category) {
        this.index = index;
        this.description = description;
        this.flowDirection = flowDirection;
        this.value = value;
        this.category = category;
    }

    public EditCommand(int index, String flag, String field) {
        this.index = index;
        this.flag = flag;
        this.fieldToChange = field;
    }

    public EditCommand(int index, String flag, Double field) {
        this.index = index;
        this.flag = flag;
        this.valueToChange = field;
    }

    public EditCommand(int index, String flag) {
        this.index = index;
        this.flag = flag;
    }

    @Override
    protected void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.INFO);
        try {
            FileHandler fileHandler = new FileHandler("EditCommand.log", true);
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            System.out.println("unable to log EditCommand class");
            logger.log(Level.SEVERE, "File logger not working.", e);
        }
    }

    @Override
    public CommandResult execute() {
        setupLogger();
        logger.log(Level.INFO, "starting EditCommand.execute()");

        index -= 1;

        int previousStatementCount = financialReport.getStatementCount();
        assert (index < financialReport.getStatementCount() && index >= 0) : "invalid index provided for edit";

        if (flag == "") {
            financialReport.deleteStatement(index);
            financialReport.addStatementAtIndex(new FinancialStatement(description, flowDirection, value, category), index);
        } else if (flag.equals("-d")) {
            financialReport.getFinancialStatement(index).setDescription(fieldToChange);
        } else if (flag.equals("-c")) {
            financialReport.getFinancialStatement(index).setCategory(fieldToChange);
        } else if (flag.equals("-v")) {
            financialReport.getFinancialStatement(index).setValue(valueToChange);
        } else if (flag.equals("-out")) {
            financialReport.getFinancialStatement(index).setFlowDirection(FlowDirection.OUTFLOW);
        } else if (flag.equals("-in")) {
            financialReport.getFinancialStatement(index).setFlowDirection(FlowDirection.INFLOW);
        }

        String output = "Done, edited entry " + (index + 1)
                + " from the financial report";

        assert previousStatementCount == financialReport.getStatementCount() : "statement count mismatch";

        logger.log(Level.INFO, "deleted from financial report");

        return new CommandResult(output);
    }
}
