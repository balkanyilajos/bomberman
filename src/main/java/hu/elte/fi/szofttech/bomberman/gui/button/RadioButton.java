package hu.elte.fi.szofttech.bomberman.gui.button;

public interface RadioButton {
    void update(boolean isSelected);

    void setButtonGroup(RadioButton[] buttons);
}