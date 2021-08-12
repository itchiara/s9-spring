package it.epicode.be.eccezioni;

public class EntityNotFoundException extends BusinessLogicException{

	public EntityNotFoundException(String message,Class<?> clazz) {
		super(String.format(message, clazz.getSimpleName()));
	}
}
