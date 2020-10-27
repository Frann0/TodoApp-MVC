package sample.Model;


import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.File;
import java.time.LocalDate;

public class Task {
    private final StringProperty descriptionProperty;
    private final ObjectProperty<LocalDate> duedateProperty;
    private final BooleanProperty isCompletedProperty;

    public Task(){
        this("");
    }

    public Task(String description){
        descriptionProperty = new SimpleStringProperty(description);
        duedateProperty = new SimpleObjectProperty(LocalDate.now().plusDays(2));
        isCompletedProperty = new SimpleBooleanProperty();
    }

    public StringProperty getDescriptionProperty(){
        return descriptionProperty;
    }

    public void setDescription(String description) {
        descriptionProperty.set(description);
    }

    public String getDescription(){
        return descriptionProperty.get();
    }

    public ObjectProperty<LocalDate> getDuedateProperty() {
        return duedateProperty;
    }

    public void setDuedate(LocalDate duedate) {
        duedateProperty.set(duedate);
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDuedate(){
        return duedateProperty.get();
    }

    public BooleanProperty getIsCompletedProperty() {
        return isCompletedProperty;
    }

    public void setIsCompleted(boolean completed) {
        isCompletedProperty.set(completed);
    }

    public boolean getIsCompleted(){
        return isCompletedProperty.get();
    }

    public void postpone(int numberOfDays){
        duedateProperty.set(duedateProperty.get().plusDays(numberOfDays));
    }

    public void complete(){
        isCompletedProperty.set(true);
    }

    public static void saveTasks(ObservableList<Task> tasks) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TaskListWrapper.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        TaskListWrapper wrapper = new TaskListWrapper();
        wrapper.setTasks(tasks);

        m.marshal(wrapper,new File("Tasks.xml"));
    }

    public static ObservableList<Task> loadTasks() throws JAXBException{
        File file = new File("Tasks.xml");
        if (!file.exists()){
            return FXCollections.observableArrayList();
        }

        JAXBContext context = JAXBContext.newInstance(TaskListWrapper.class);
        Unmarshaller um = context.createUnmarshaller();

        TaskListWrapper wrapper = (TaskListWrapper) um.unmarshal(file);
        System.out.println(file);
        return FXCollections.observableArrayList(wrapper.getTasks());
    }
}
