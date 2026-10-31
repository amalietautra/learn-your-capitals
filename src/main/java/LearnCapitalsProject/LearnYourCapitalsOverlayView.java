package LearnCapitalsProject;

import java.time.Duration;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class LearnYourCapitalsOverlayView {

    @FXML //markerer at feltet er koblet til FXML
    private StackPane overlay;
    private String continent;
    private String username;

    private LearnYourCapitalsComparator comparator;
    private LearnYourCapitalsFileHandler filehandler;

    private LearnYourCapitalsOverlayView(StackPane overlay, String continent, ) {
        this.continent = continent;
        this.showOverlay();
    }

    public void setContinent(String continent) {
        
    }

    @FXML
    private void showOverlay(){
        overlay.setVisible(true);
        overlay.
    }

    @FXML
    private void hideOverlay(){ 
        overlay.setVisible(false);
    }

    @FXML
    private void 
}
