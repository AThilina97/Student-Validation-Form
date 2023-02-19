package lk.ijse.dep10.app.controler;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import lk.ijse.dep10.app.model.Student;
import lk.ijse.dep10.app.util.CourseType;
import lk.ijse.dep10.app.util.Gender;

import java.util.ArrayList;
import java.util.Optional;

public class MainViewControler {

    public TextField txtName;
    public TableView<Student> tblStudent;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnForward;

    @FXML
    private Button btnNewStudent;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnSave;

    @FXML
    private CheckBox chkboxParttime;

    @FXML
    private ComboBox<String> cmbDegree;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblGeneratedId;

    @FXML
    private Label lblSelectedDegree;

    @FXML
    private ListView<String> lstContact;

    @FXML
    private ListView<String> lstModule;

    @FXML
    private ListView<String> lstSelectedModule;

    @FXML
    private RadioButton rdoFemale;

    @FXML
    private RadioButton rdoMale;

    @FXML
    private ToggleGroup set;

    @FXML
    private TextField txtContact;

    @FXML
    void btnNewStudentOnAction(ActionEvent event) {
        lblGeneratedId.setText(generatedId());

        txtName.setDisable(false);
        txtContact.setDisable(false);
        rdoMale.setDisable(false);
        rdoFemale.setDisable(false);
        //btnAdd.setDisable(false);
        lstContact.setDisable(false);
        cmbDegree.setDisable(false);
        lstModule.setDisable(false);
        lstSelectedModule.setDisable(false);
        btnForward.setDisable(false);
        chkboxParttime.setDisable(false);
        btnSave.setDisable(false);
        tblStudent.setDisable(false);
        txtName.requestFocus();
        btnDelete.setDisable(true);

        txtName.clear();
        txtContact.clear();

        cmbDegree.getStyleClass().remove("invalid");
        lstSelectedModule.getStyleClass().remove("invalid");
        txtContact.getStyleClass().remove("invalid");
        txtName.getStyleClass().remove("invalid");
        lblGender.setTextFill(Color.BLACK);


        lstContact.getItems().clear();
        lstModule.getItems().clear();
        lstSelectedModule.getItems().clear();
        cmbDegree.getItems().clear();
        lblSelectedDegree.setText("No Program has been Selected");
        rdoFemale.getToggleGroup().selectToggle(null);
        chkboxParttime.setSelected(false);

        ObservableList<String> degreeCombo =cmbDegree.getItems();
        degreeCombo.addAll("COMPUTER SCIENCE","MECHANICAL ENGINEERING","CIVIL ENGINEERING","ELECTRONIC ENGINEERING",
                "CHEMICAL ENGINEERING");


    }

    private String generatedId(){
        if(tblStudent.getItems().size()==0) return "S001";
        ObservableList<Student> tblStudentList = tblStudent.getItems();
        String lastId =tblStudentList.get(tblStudentList.size()-1).getId();
        return "S"+String.format("%03d",Integer.parseInt(lastId.substring(1))+1);
    }
    public void initialize(){
        txtName.textProperty().addListener((Value, previous,curentText) -> {
            txtName.getStyleClass().remove("invalid");
            for (char c:curentText.toCharArray()) {
                if(Character.isDigit(c)||Character.isSpaceChar(c)){
                    txtName.getStyleClass().add("invalid");
                }

            }
        });
        txtContact.textProperty().addListener((Value, previous,curentContact) ->{
            txtContact.getStyleClass().remove("invalid");
            if (curentContact.isEmpty()) {
                btnAdd.setDisable(true);
                return;
            }
            btnAdd.setDisable(false);
            if(curentContact.length()!=11 || curentContact.charAt(3)!='-' || !isNumber(curentContact.substring(0,3))
                    || !isNumber(curentContact.substring(4))){
                txtContact.getStyleClass().add("invalid");
                btnAdd.setDisable(true);
            }

        } );
        lstContact.getSelectionModel().selectedItemProperty().addListener((Value, previous, current) ->
        {btnRemove.setDisable(current==null);});

        cmbDegree.getSelectionModel().selectedItemProperty().addListener((value, Previous, current) ->{
            cmbDegree.getStyleClass().remove("invalid");
            String selectDegree = cmbDegree.getSelectionModel().getSelectedItem();
            lblSelectedDegree.setText(selectDegree);
            ObservableList<String> moduleList = lstModule.getItems();
            if(selectDegree=="MECHANICAL ENGINEERING"){
                moduleList.clear();
                moduleList.addAll("Manufacturing","Fluid Dynamics","Automation","Automobile","Aerodynamics");
            } else if (selectDegree=="CIVIL ENGINEERING") {
                moduleList.clear();
                moduleList.addAll("soil","cost estimate","Autocad","graph theory","Calculus");

            }else if (selectDegree=="COMPUTER SCIENCE") {
                moduleList.clear();
                moduleList.addAll("Java","Angular","React","Html","Css","Java Script");
            }else if (selectDegree=="CHEMICAL ENGINEERING") {
                moduleList.clear();
                moduleList.addAll("Petrolium","AquaChemistry","Chemistry","Maths","BioChemistry");
            }else if (selectDegree=="ELECTRONIC ENGINEERING") {
                moduleList.clear();
                moduleList.addAll("Basic Electronic","Basic Electrical","AI","Maths","IC-Theory");
            }

        } );
        lstModule.getSelectionModel().selectedItemProperty().addListener((Value, previous, currentModule) -> {
            btnForward.setDisable(currentModule==null);


        });


        lstSelectedModule.getSelectionModel().selectedItemProperty().addListener((Value, previous, currentModule) ->
        {btnBack.setDisable(currentModule==null);});

        tblStudent.getSelectionModel().selectedItemProperty().addListener((Value, previous, currentStudent) ->{
            if (!tblStudent.getSelectionModel().isEmpty()) {
                txtName.setText(currentStudent.getName());
                lblGeneratedId.setText(currentStudent.getId());
                lstContact.getItems().setAll(currentStudent.getContacts());
                lblSelectedDegree.setText(currentStudent.getDepartment());
                lstSelectedModule.getItems().setAll(currentStudent.getSelectModule());
                lstModule.getItems().setAll(currentStudent.getModules());
                cmbDegree.getSelectionModel().select(currentStudent.getDepartment());

                if (currentStudent.getCourseType() == CourseType.PARTTIME) chkboxParttime.setSelected(true);

                if (currentStudent.getGender() == Gender.MALE) rdoMale.getToggleGroup().selectToggle(rdoMale);
                if (currentStudent.getGender() == Gender.FEMALE) rdoFemale.getToggleGroup().selectToggle(rdoFemale);
                if (tblStudent.getSelectionModel().getSelectedItem() != null) btnDelete.setDisable(false);

            }

        });
    }
    private boolean isNumber(String getnum){
        for (char c:getnum.toCharArray()) {
            if(!Character.isDigit(c)) return false;
        }
        return true;

    }


    @FXML
    void btnAddOnAction(ActionEvent event) {

        txtContact.getStyleClass().remove("invalid");

        ObservableList<Student> studentTable = tblStudent.getItems();
        for (Student getStudent:studentTable) {
            ArrayList<String> getStudentContacts = getStudent.getContacts();
            for (String contact:getStudentContacts) {
                if(txtContact.getText().equals(contact)) {
                    txtContact.getStyleClass().add("invalid");
                    return;
                }
            }

        }

        for (String numAvailable:lstContact.getItems()) {
            if (txtContact.getText().equals(numAvailable)) {
                txtContact.getStyleClass().add("invalid");
                txtContact.requestFocus();
                return;
            }
        }
        ObservableList<String> contactList = lstContact.getItems();
        contactList.add(txtContact.getText());
        txtContact.clear();
        txtContact.requestFocus();
    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        ObservableList<String> contactList =lstContact.getItems();
        contactList.remove(lstContact.getSelectionModel().getSelectedItem());
        lstContact.getSelectionModel().clearSelection();
        txtContact.requestFocus();

    }

    @FXML
    void btnForwardOnAction(ActionEvent event) {
        lstSelectedModule.getStyleClass().remove("invalid");
        if(lstModule.getSelectionModel().getSelectedItem()==null){
            return;
        }
        ObservableList<String> molduleList = lstModule.getItems();
        ObservableList<String> selectedMolduleList = lstSelectedModule.getItems();
        String selectedItem = lstModule.getSelectionModel().getSelectedItem();
        selectedMolduleList.add(selectedItem);
        molduleList.remove(selectedItem);
        lstModule.getSelectionModel().clearSelection();


    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        ObservableList<String> molduleList = lstModule.getItems();
        ObservableList<String> selectedMolduleList = lstSelectedModule.getItems();
        String selectedItem = lstSelectedModule.getSelectionModel().getSelectedItem();

        molduleList.add(selectedItem);
        selectedMolduleList.remove(selectedItem);
        lstSelectedModule.getSelectionModel().clearSelection();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isDataValid = true;

        if(lstSelectedModule.getItems().size()<3){
            lstSelectedModule.getStyleClass().add("invalid");
            lstModule.requestFocus();
            isDataValid=false;
        }
        if(cmbDegree.getSelectionModel().getSelectedItem()==null){
            cmbDegree.getStyleClass().add("invalid");
            cmbDegree.requestFocus();
            isDataValid=false;
        }
        if(lstContact.getItems().size()==0) {
            txtContact.getStyleClass().add("invalid");
            txtContact.requestFocus();
            isDataValid = false;
        }
        if(rdoMale.getToggleGroup().getSelectedToggle()==null){
            isDataValid = false;
            rdoMale.requestFocus();
            lblGender.setTextFill(Color.RED);
        }
        if(txtName.getText().isBlank()){
            isDataValid=false;
            if (!txtName.getStyleClass().contains("invalid")) txtName.getStyleClass().add("invalid");
            txtName.requestFocus();
            txtName.selectAll();
        }
        if(!isDataValid) return;


        ArrayList<String> selectedContacts = new ArrayList<>(lstContact.getItems());
        ArrayList<String> nonSelectedModeule = new ArrayList<>(lstModule.getItems());
        ArrayList<String> selectedModule = new ArrayList<>(lstSelectedModule.getItems());

        if(tblStudent.getSelectionModel().getSelectedItem()==null) {
            Student student = new Student();
            student.setId(lblGeneratedId.getText());
            student.setName(txtName.getText());
            student.setGender(rdoMale.isSelected() ? Gender.MALE : Gender.FEMALE);
            student.setContacts(selectedContacts);
            student.setDepartment(lblSelectedDegree.getText());
            student.setModules(nonSelectedModeule);
            student.setSelectModule(selectedModule);
            student.setCourseType(chkboxParttime.isSelected() ? CourseType.PARTTIME : CourseType.FULLTIME);


            ObservableList<Student> studentTable = tblStudent.getItems();
//....................................................................................
//            for (Student getStudent:studentTable) {
//                ArrayList<String> getStudentContacts = getStudent.getContacts();
//                for (String contact:student.getContacts()) {
//                    if(getStudentContacts.contains(contact)) {
//                        lstContact.getStyleClass().add("invalid");
//                        return;
//                    }
//                }
//
//            }
//........................................................................
            studentTable.add(student);

            tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
            tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
            tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contacts"));
            tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("department"));
            // tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("modules"));
            tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("selectModule"));
            tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));
            tblStudent.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("courseType"));
        }



        btnNewStudent.fire();


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Optional<ButtonType> optButton = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Delete this", ButtonType.YES, ButtonType.NO).showAndWait();
        if(optButton.isEmpty() || optButton.get() ==ButtonType.NO) return;
        ObservableList<Student> tblselected = tblStudent.getItems();
        Student selectedItem = tblStudent.getSelectionModel().getSelectedItem();
        tblStudent.getSelectionModel().clearSelection();
        tblselected.remove(selectedItem);
        btnNewStudent.fire();

    }

    @FXML
    void lstSelectedModule(KeyEvent event) {
        if(event.getCode()==KeyCode.DELETE) btnBack.fire();

    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER) btnAdd.fire();

    }


    @FXML
    void lstModuleOnKeyReleased(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER) btnForward.fire();

    }

    @FXML
    void lstContactOnKeyReleased(KeyEvent event) {
        if(event.getCode()==KeyCode.DELETE) btnRemove.fire();

    }

    public void tblStudentOnKeyReleased(KeyEvent keyEvent) {
        if(keyEvent.getCode()== KeyCode.DELETE) btnDelete.fire();
    }

    public void rdoGentOnAction(ActionEvent actionEvent) {
        lblGender.setTextFill(Color.BLACK);
    }
}