package com.fsd.spring.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

@Component
public class Subject implements  Comparable, Serializable {

    private static final long serialVersionUID =1l;

    private Long subjectId;
    private String subtitle;
    private int durationInHours;
    private Set<Book> references;

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(int durationInHours) {
        this.durationInHours = durationInHours;
    }

    public Set<Book> getReferences() {
        return references;
    }

    public void setReferences(Set<Book> references) {
        this.references = references;
    }

    @Override
    public int compareTo(Object o) {
        return this.subjectId.compareTo(((Subject)o).getSubjectId());
    }
}
