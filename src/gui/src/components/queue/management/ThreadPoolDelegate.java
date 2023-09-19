package components.queue.management;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;

public class ThreadPoolDelegate {
    private final SimpleIntegerProperty runningSimulations = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty simulationsInQueue = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty finishedSimulations = new SimpleIntegerProperty(0);

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

    public void increaseFinishedSimulations() {
        Platform.runLater(() -> finishedSimulations.set(finishedSimulations.getValue() + 1));
    }

    public void decreaseFinishedSimulations() {
        Platform.runLater(() -> finishedSimulations.set(finishedSimulations.getValue() - 1));
    }
}
