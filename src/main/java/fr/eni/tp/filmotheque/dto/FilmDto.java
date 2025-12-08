package fr.eni.tp.filmotheque.dto;

import fr.eni.tp.filmotheque.bo.Avis;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilmDto implements Serializable {
	/**
	 * Numéro de sérialisation
	 */
	private static final long serialVersionUID = 1L;
    @NotBlank
    @Size(min = 1, max = 100)
    private String titre;
    @Min(1895)
	private int annee;
    @Min(0)
	private int duree;
	private String synopsis;

    @NotNull
    private int genreId;

    @NotNull
    private Integer realisateurId;

    private List<Integer> acteursIds;

	public FilmDto() {
	}

	public FilmDto(String titre, int annee, int duree, String synopsis) {
		this();
		this.titre = titre;
		this.annee = annee;
		this.duree = duree;
		this.synopsis = synopsis;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public Integer getRealisateurId() {
        return realisateurId;
    }

    public void setRealisateurId(Integer realisateurId) {
        this.realisateurId = realisateurId;
    }

    public List<Integer> getActeursIds() {
        return acteursIds;
    }

    public void setActeursIds(List<Integer> acteursIds) {
        this.acteursIds = acteursIds;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FilmDto filmDto)) return false;
        return annee == filmDto.annee && duree == filmDto.duree && Objects.equals(titre, filmDto.titre) && Objects.equals(synopsis, filmDto.synopsis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titre, annee, duree, synopsis);
    }
}
