package unicalApplication.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import unicalApplication.models.Event;
import unicalApplication.models.Notification;
import unicalApplication.models.UserEntity;
import unicalApplication.repositories.INotificationDAO;

@Service
public class NotificationService {

	@Autowired
	INotificationDAO notificationDAO;

	public List<Notification> findByUser(UserEntity user) throws NotFoundException {
		List<Notification> findByUser = notificationDAO.findByUser(user);
		return findByUser;
	}

	public List<Notification> getAll() throws NotFoundException {
		List<Notification> all = notificationDAO.findAll();
		if(all.isEmpty()) {
			throw new NotFoundException("Nenhuma notificação encontrada");
		}
		return all;
	}

	public Notification add(Notification Notification) {
		return notificationDAO.save(Notification);
	}

	public Notification findByID(Long id) throws NotFoundException {
		Optional<Notification> Notification = notificationDAO.findById(id);
		if (!Notification.isPresent())
			throw new NotFoundException("Notificationo não encontrado");
		return Notification.get();
	}

	public Notification delete(Long id) throws NotFoundException {
		Notification Notification = this.findByID(id);
		notificationDAO.delete(Notification);
		return Notification;
	}

	public Notification update(long id, Notification notification) throws NotFoundException {
		Notification notificationInDb = this.findByID(id);
		if (notification.getEvent() != null)
			notificationInDb.setEvent(notification.getEvent());
		if (notification.getTitle() != null)
			notificationInDb.setTitle(notification.getTitle());

		Notification save = notificationDAO.save(notificationInDb);
		return save;
	}
}
