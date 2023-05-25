package org.lilia.dal.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class AdditionalMaterial implements Serializable {
    private static int counter = 0;

    private final int id;

    private String name;

    private int lectureId;
    private ResourceType resourceType;

    public AdditionalMaterial(String name, int lectureId) {
        this.name = name;
        this.lectureId = lectureId;
        counter++;
        id = counter;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdditionalMaterial that = (AdditionalMaterial) o;

        if (id != that.id) return false;
        if (lectureId != that.lectureId) return false;
        if (!Objects.equals(name, that.name)) return false;
        return resourceType == that.resourceType;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + lectureId;
        result = 31 * result + (resourceType != null ? resourceType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\n AdditionalMaterial{" +
                " id=" + id +
                " name='" + name + '\'' +
                " lectureId=" + lectureId +
                " resourceType=" + resourceType +
                '}';
    }

    public enum SortField {
        ID(1),
        LECTURE_ID(2),
        RESOURCE_TYPE(3);

        private final int id;

        SortField(int id) {
            this.id = id;
        }

        public static SortField getById(int id) {
            SortField[] values = values();
            for (SortField sortField : values) {
                if (sortField.id == id) {
                    return sortField;
                }
            }
            throw new NoSuchElementException("SortField by id " + id + " was not found");
        }
    }

    public static class IdComparator implements Comparator<AdditionalMaterial> {

        @Override
        public int compare(AdditionalMaterial o1, AdditionalMaterial o2) {
            return Integer.compare(o1.id, o2.id);
        }
    }

    public static class LectureIdComparator implements Comparator<AdditionalMaterial> {

        @Override
        public int compare(AdditionalMaterial o1, AdditionalMaterial o2) {
            return Integer.compare(o1.lectureId, o2.lectureId);
        }
    }

    public static class ResourceTypeComparator implements Comparator<AdditionalMaterial> {

        @Override
        public int compare(AdditionalMaterial o1, AdditionalMaterial o2) {

            return Integer.compare(o1.resourceType.getParameter(), o2.resourceType.getParameter());
        }
    }
}


