package seedu.rainyDay.modules;

import seedu.rainyDay.data.FinancialReport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Storage {
    public static void writeToFile(FinancialReport statements, String filePath) {
        try {
            FileOutputStream writeData = new FileOutputStream(filePath);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(statements);

            writeStream.flush();
            writeStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FinancialReport loadFromFile(String filePath)
            throws IOException, ClassNotFoundException, ClassCastException {
        FileInputStream readData = new FileInputStream(filePath);
        ObjectInputStream readStream = new ObjectInputStream(readData);

        FinancialReport statements = (FinancialReport) readStream.readObject();
        assert statements.getReportOwner() != null : "Loaded username should not be null";

        readStream.close();
        readData.close();
        return statements;
    }
}
