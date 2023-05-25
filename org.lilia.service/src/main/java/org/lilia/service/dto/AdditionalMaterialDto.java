package org.lilia.service.dto;

import org.lilia.dal.model.ResourceType;

public class AdditionalMaterialDto {
    private final int lectureId;
    private final String name;
    private final ResourceType resourceType;

    public AdditionalMaterialDto(int lectureId, String name, ResourceType resourceType) {
        this.lectureId = lectureId;
        this.name = name;
        this.resourceType = resourceType;
    }


    public int getLectureId() {
        return lectureId;
    }

    public String getName() {
        return name;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public String toString() {
        return "lectureId " + lectureId +
                "additionalMaterialName '" + name + "'" +
                "resourceType " + resourceType;
    }
}
