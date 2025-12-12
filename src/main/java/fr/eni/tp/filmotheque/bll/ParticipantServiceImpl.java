package fr.eni.tp.filmotheque.bll;


import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dal.ParticipantRepository;
import fr.eni.tp.filmotheque.exception.ParticipantNotFound;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("participantService")
public class ParticipantServiceImpl implements ParticipantService{

	private ParticipantRepository participantRepo;
	
	
	public ParticipantServiceImpl(ParticipantRepository participantRepo) {
		this.participantRepo = participantRepo;
	}

	@Override
	public List<Participant> findAllParticipants() {
		return participantRepo.findAllParticipants();
	}

	@Override
	public Participant findParticipantById(int id)  {
		
		Participant participant =  participantRepo.findParticipantById(id);

		return participant;
	}

}
