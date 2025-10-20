package controllers.animaciones;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.layout.AnchorPane;

public class animacionLoginRegister {

    public void mostrarRegistro(AnchorPane panelPrincipalLogin, AnchorPane panelPrincipalRegister){
        // Animación para ocultar loginPane (deslizar a la derecha)
        TranslateTransition slideLogin = new TranslateTransition(Duration.seconds(0.5), panelPrincipalLogin);
        slideLogin.setToX(400); // se mueve 400px a la derecha
        slideLogin.play();

        // Animación para mostrar registerPane (entrar desde la izquierda)
        panelPrincipalRegister.setTranslateX(-400); // lo ponemos fuera de la pantalla
        TranslateTransition slideRegister = new TranslateTransition(Duration.seconds(0.5), panelPrincipalRegister);
        slideRegister.setToX(0); // vuelve a su posición normal
        slideRegister.play();

    }


    public void mostrarLogin(AnchorPane panelPrincipalLogin, AnchorPane panelPrincipalRegister) {
        // Regresar loginPane a la posición original
        TranslateTransition slideLogin = new TranslateTransition(Duration.seconds(0.5), panelPrincipalLogin);
        slideLogin.setToX(0);
        slideLogin.play();

        // Mover registerPane fuera de la pantalla
        TranslateTransition slideRegister = new TranslateTransition(Duration.seconds(0.5), panelPrincipalRegister);
        slideRegister.setToX(-400);
        slideRegister.play();
    }

    // 🔹 Slide desde la derecha → entra al centro
    public static void slideLeft(AnchorPane rootPane, AnchorPane newPane, double sceneWidth) {
        newPane.setTranslateX(sceneWidth);
        rootPane.getChildren().setAll(newPane);

        TranslateTransition tt = new TranslateTransition(Duration.millis(500), newPane);
        tt.setFromX(sceneWidth);
        tt.setToX(0);
        tt.play();
    }

    // 🔹 Slide desde la izquierda → entra al centro
    public static void slideRight(AnchorPane rootPane, AnchorPane newPane, double sceneWidth) {
        newPane.setTranslateX(-sceneWidth);
        rootPane.getChildren().setAll(newPane);

        TranslateTransition tt = new TranslateTransition(Duration.millis(500), newPane);
        tt.setFromX(-sceneWidth);
        tt.setToX(0);
        tt.play();
    }

    // 🔹 Slide desde arriba
    public static void slideDown(AnchorPane rootPane, AnchorPane newPane, double sceneHeight) {
        newPane.setTranslateY(-sceneHeight);
        rootPane.getChildren().setAll(newPane);

        TranslateTransition tt = new TranslateTransition(Duration.millis(500), newPane);
        tt.setFromY(-sceneHeight);
        tt.setToY(0);
        tt.play();
    }

    // 🔹 Slide desde abajo
    public static void slideUp(AnchorPane rootPane, AnchorPane newPane, double sceneHeight) {
        newPane.setTranslateY(sceneHeight);
        rootPane.getChildren().setAll(newPane);

        TranslateTransition tt = new TranslateTransition(Duration.millis(500), newPane);
        tt.setFromY(sceneHeight);
        tt.setToY(0);
        tt.play();
    }

    // 🔹 Fade In
    public static void fadeIn(AnchorPane rootPane, AnchorPane newPane) {
        newPane.setOpacity(0);
        rootPane.getChildren().setAll(newPane);

        FadeTransition ft = new FadeTransition(Duration.millis(500), newPane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
}



