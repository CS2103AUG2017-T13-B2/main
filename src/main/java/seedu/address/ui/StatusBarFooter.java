package seedu.address.ui;

import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    //@@author LinusMelb
    public static final String SYNC_PERSONLIST_UPADTED_SIZE = "Total size: %d, ";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";
    //@@author

    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "StatusBarFooter.fxml";

    //@@author LinusMelb
    private ObservableList<ReadOnlyPerson> filteredPersonList;
    //@@author

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;

    public StatusBarFooter(String saveLocation) {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
    }

    //@@author LinusMelb
    public StatusBarFooter(String saveLocation, ObservableList<ReadOnlyPerson> filteredPersonList) {
        super(FXML);
        this.filteredPersonList = filteredPersonList;
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
    }
    //@@author

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    private void setSaveLocation(String location) {
        Platform.runLater(() -> this.saveLocationStatus.setText(location));
    }

    private void setSyncStatus(String status) {
        Platform.runLater(() -> this.syncStatus.setText(status));
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce,
                "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_PERSONLIST_UPADTED_SIZE, filteredPersonList.size())
                + String.format(SYNC_STATUS_UPDATED, lastUpdated));
    }

}
