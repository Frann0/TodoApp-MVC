package sample.Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class TaskListWrapper {
    private List<Task> tasks;

    @XmlElement(name = "Task")
    public List<Task> getTasks(){
        return tasks;
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
    }
}
