package layout.openHourDialog;

@Deprecated
public class Day {

    private String name;
    private boolean checked;

    public Day(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return checked;
    }
}
