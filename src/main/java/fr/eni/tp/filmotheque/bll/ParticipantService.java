package fr.eni.tp.filmotheque.bll;


import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.exception.ParticipantNotFound;

import java.util.List;

public interface ParticipantService {

	public List<Participant> findAllParticipants();
	
	public Participant findParticipantById(int id);
	
}
