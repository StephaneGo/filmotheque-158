package fr.eni.tp.filmotheque.exception;

public class ParticipantNotFound extends RuntimeException {
    public ParticipantNotFound(int id) {
        super("Participant not found with id " + id);
    }
}
