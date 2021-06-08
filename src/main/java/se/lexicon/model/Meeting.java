package se.lexicon.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Meeting {
    private int id;
    private String topic;
    private LocalDate meetingDate;
    private LocalTime start;
    private  LocalTime end;
    private Person organizer;
    private List<Person> attendants;


    public Meeting(int id, String topic, LocalDate meetingDate, LocalTime start, LocalTime end, Person organizer) {
        this.id = id;
        this.topic = topic;
        this.meetingDate = meetingDate;
        this.start = start;
        this.end = end;
        this.organizer = organizer;
    }

    Meeting (){}

    public int getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public Person getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Person organizer) {
        this.organizer = organizer;
    }

    public List<Person> getAttendants() {
        if(attendants == null) attendants = new ArrayList<>();
        return attendants;
    }

    public void setAttendants(List<Person> attendants) {
        this.attendants = attendants;
    }

    public void addAttendant(Person person){
        if(person != null){
            if(attendants == null) attendants = new ArrayList<>();
            if(!attendants.contains(person)){
                attendants.add(person);
            }
        }
    }

    public void removeAttendant(Person person){
        if(person != null){
            if(attendants == null) attendants = new ArrayList<>();
            attendants.remove(person);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return getId() == meeting.getId() && Objects.equals(getTopic(), meeting.getTopic()) && Objects.equals(getMeetingDate(), meeting.getMeetingDate()) && Objects.equals(getStart(), meeting.getStart()) && Objects.equals(getEnd(), meeting.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTopic(), getMeetingDate(), getStart(), getEnd());
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", meetingDate=" + meetingDate +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
