package mainpackage;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import generated.Person;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "director-catalog")
public class DirectorsList {

    @XmlElement(name = "director")
    protected ArrayList<Person> directors;
    protected Statistics statistics;

    public Statistics getStatistics() {
        if (this.statistics == null)
            this.statistics = new Statistics();
        return this.statistics;
    }
  
    public ArrayList<Person> getDirectors() {
    	if (directors == null) 
    		directors = new ArrayList<>();
		return this.directors;
	}
	public void setDirector(ArrayList<Person> directors) {
		this.directors = directors;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

}
