package components.queue.management;

import app.Toast;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.Stage;

public class ThreadPoolDelegate {
    private final SimpleIntegerProperty runningSimulations = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty simulationsInQueue = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty finishedSimulations = new SimpleIntegerProperty(0);
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public int getRunningSimulations() {
        return runningSimulations.get();
    }

    public SimpleIntegerProperty runningSimulationsProperty() {
        return runningSimulations;
    }

    public int getSimulationsInQueue() {
        return simulationsInQueue.get();
    }

    public SimpleIntegerProperty simulationsInQueueProperty() {
        return simulationsInQueue;
    }

    public int getFinishedSimulations() {
        return finishedSimulations.get();
    }

    public SimpleIntegerProperty finishedSimulationsProperty() {
        return finishedSimulations;
    }

    public void increaseRunningSimulations() {
        Platform.runLater(() -> runningSimulations.set(runningSimulations.getValue() + 1));
    }

    public void decreaseRunningSimulations() {
        Platform.runLater(() -> runningSimulations.set(runningSimulations.getValue() - 1));
    }

    public void increaseSimulationsInQueue() {
        Platform.runLater(() -> simulationsInQueue.set(simulationsInQueue.getValue() + 1));
    }

    public void decreaseSimulationsInQueue() {
        Platform.runLater(() -> simulationsInQueue.set(simulationsInQueue.getValue() - 1));
    }

    public void increaseFinishedSimulations(Integer id) {
        Platform.runLater(() -> {
            finishedSimulations.set(finishedSimulations.getValue() + 1);
            Toast toast = new Toast(primaryStage);
            toast.makeToast("Simulation " + id + " has finished running!\nYou can view its analysis in the Results screen");
        });
    }

    public void decreaseFinishedSimulations() {
        Platform.runLater(() -> finishedSimulations.set(finishedSimulations.getValue() - 1));
    }
}
