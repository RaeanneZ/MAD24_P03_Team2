package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

public class DietPlanClass {
    private int id;
    private String name;
    private int reccCarbIntake;
    private int reccProteinIntake;
    private int reccFatsIntake;
    private String gender;

    public DietPlanClass() { }
    public DietPlanClass(int id, String name, int reccCarbIntake, int reccProteinIntake, int reccFatsIntake, String gender){
        this.id = id;
        this.name = name;
        this.reccCarbIntake = reccCarbIntake;
        this.reccProteinIntake = reccProteinIntake;
        this.reccFatsIntake = reccFatsIntake;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getReccCarbIntake() {
        return reccCarbIntake;
    }
    public void setReccCarbIntake(int reccCarbIntake) {
        this.reccCarbIntake = reccCarbIntake;
    }

    public int getReccProteinIntake() {
        return reccProteinIntake;
    }
    public void setReccProteinIntake(int reccProteinIntake) {
        this.reccProteinIntake = reccProteinIntake;
    }

    public int getReccFatsIntake() {
        return reccFatsIntake;
    }
    public void setReccFatsIntake(int reccFatsIntake) {
        this.reccFatsIntake = reccFatsIntake;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
}
