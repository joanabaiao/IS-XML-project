package mainpackage;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Ranking {
	
	@XmlElement(name = "name")
    protected String directorName;
	@XmlElement(name = "best-rank")
    protected Integer bestRank;

    public Ranking() {
    }

    public Ranking(String directorName, Integer bestRank) {
        this.directorName = directorName;
        this.bestRank = bestRank;
    }
   
    public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

    public Integer getBestRank() {
        return bestRank;
    }

    public void setBestRank(Integer bestRank) {
        this.bestRank = bestRank;
    }
}
