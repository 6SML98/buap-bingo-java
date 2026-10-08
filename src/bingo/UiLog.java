package bingo;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

final class UiLog {
    private UiLog() {}

    static void append(JTextArea area, String message) {
        if (SwingUtilities.isEventDispatchThread()) area.append(message);
        else SwingUtilities.invokeLater(() -> area.append(message));
    }
}
