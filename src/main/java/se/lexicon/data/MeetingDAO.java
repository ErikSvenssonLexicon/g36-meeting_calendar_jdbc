package se.lexicon.data;

import se.lexicon.model.Meeting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface MeetingDAO {
    Meeting persist(Meeting meeting);
    Optional<Meeting> findById(Integer id);
    Collection<Meeting> findAll();
    Collection<Meeting> findByTopic(String topic);
    Collection<Meeting> findByAttendeePersonId(Integer personId);
    Collection<Meeting> findByOrganizerPersonId(Integer personId);
    Collection<Meeting> findByMeetingsBetween(LocalDateTime start, LocalDateTime end);
    Collection<Meeting> findByMeetingDate(LocalDate date);
    Meeting update(Meeting meeting);
    boolean remove(Integer id);
}


