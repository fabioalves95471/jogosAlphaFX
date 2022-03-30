package com.fabiomalves.jogosAlphaFX.util;

import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Eventos {

    public static void addEventKey_PRESSED (Button... bts) {
        for (Button bt:bts) {
            bt.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent> () {
                @Override
                public void handle (KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        bt.pseudoClassStateChanged(PseudoClass.getPseudoClass("armed"), true);
                    }
                }
            });
        }
    }
    public static void addEventKey_RELEASED (Button... bts) {
        for (Button bt:bts) {
            bt.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent> () {
                @Override
                public void handle (KeyEvent ke) {
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        bt.pseudoClassStateChanged(PseudoClass.getPseudoClass("armed"), false);
                    }
                }
            });
        }
    }

}
