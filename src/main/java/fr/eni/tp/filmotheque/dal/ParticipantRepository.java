package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Participant;

import java.util.List;
import java.util.Optional;



public interface ParticipantRepository {
	public List<Participant> findAllParticipants();

	public Participant findParticipantById(int id);
}
