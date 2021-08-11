package mainpackage;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "statistics")
public class Statistics {

	@XmlElement(name = "number-directors")
	protected int number_directors;
	@XmlElement(name = "number-movies")
	protected int number_movies;
    @XmlElementWrapper(name = "ranking")
    @XmlElement(name = "top-directors")
    protected List<Ranking> bestDirectors;
    
	public int getNumber_directors() {
		return number_directors;
	}

	public void setNumber_directors(int number_directors) {
		this.number_directors = number_directors;
	}
	
	public int getNumber_movies() {
		return number_movies;
	}
	
	public void setNumber_movies(int number_movies) {
		this.number_movies = number_movies;
	}
	
	public List<Ranking> getBestDirectors() {
		if (this.bestDirectors == null)
            this.bestDirectors = new ArrayList<>();
		
        return this.bestDirectors;
	}
	
	public void setBestDirectors(List<Ranking> bestDirectors) {
		this.bestDirectors = bestDirectors;
	}

}