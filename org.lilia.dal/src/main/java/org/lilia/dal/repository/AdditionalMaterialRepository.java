package org.lilia.dal.repository;

import org.lilia.dal.model.AdditionalMaterial;
import org.lilia.logger.serialization.FilePath;
import org.lilia.logger.serialization.Serializer;
import org.lilia.logger.utils.ConsoleUtils;
import org.lilia.logger.utils.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class AdditionalMaterialRepository {

    private final Map<Integer, List<AdditionalMaterial>> data = new HashMap<>();

    private static Comparator<AdditionalMaterial> getAdditionalMaterialComparator(AdditionalMaterial.SortField sortField) {
        Comparator<AdditionalMaterial> comparator = null;
        switch (sortField) {
            case ID -> comparator = new AdditionalMaterial.IdComparator();
            case LECTURE_ID -> comparator = new AdditionalMaterial.LectureIdComparator();
            case RESOURCE_TYPE -> comparator = new AdditionalMaterial.ResourceTypeComparator();
            default -> ConsoleUtils.print(Constants.ERROR);
        }
        return comparator;
    }

    public void add(AdditionalMaterial additionalMaterial) {
        List<AdditionalMaterial> value = data.get(additionalMaterial.getLectureId());
        if (value == null) {
            value = new ArrayList<>();
            value.add(additionalMaterial);
            data.put(additionalMaterial.getLectureId(), value);
        } else {
            value.add(additionalMaterial);
        }
    }

    public int size() {
        return data.size();
    }

    public Optional<AdditionalMaterial> getById(int id) {
        Collection<List<AdditionalMaterial>> values = data.values();

        for (List<AdditionalMaterial> additionalMaterials : values) {

            for (AdditionalMaterial additionalMaterial : additionalMaterials) {

                if (additionalMaterial.getId() == id) {
                    return Optional.of(additionalMaterial);
                }
            }
        }
        return Optional.empty();
    }

    public void remove(AdditionalMaterial additionalMaterial) {
        List<AdditionalMaterial> value = data.get(additionalMaterial.getLectureId());
        if (value == null) {
            return;
        }
        value.remove(additionalMaterial);
    }

    public Optional<List<AdditionalMaterial>> getByLectureId(int lectureId) {
        List<AdditionalMaterial> list = data.get(lectureId);
        return Optional.ofNullable(list);
    }

    public List<AdditionalMaterial> getAll() {
        List<AdditionalMaterial> list = new ArrayList<>();
        for (List<AdditionalMaterial> additionalMaterials : data.values()) {
            list.addAll(additionalMaterials);
        }
        return list;
    }

    public void getAll(AdditionalMaterial.SortField sortField, int lectureId) {

        Optional<List<AdditionalMaterial>> list = getByLectureId(lectureId);
        if (list.isEmpty()) {
            System.out.println("in lecture " + lectureId + "additionMaterial not exist");
        } else {
            Comparator<AdditionalMaterial> comparator = getAdditionalMaterialComparator(sortField);
            List<AdditionalMaterial> resList = list.get();
            resList.sort(comparator);
            for (AdditionalMaterial additionalMaterial : resList) {
                System.out.println(additionalMaterial);
            }
        }
    }

    public void serializeMaterial() {
        List<AdditionalMaterial> list = getAll();
        Serializer.serialize(list, FilePath.FILE_PATH_ADDITION_MATERIAL);
        ConsoleUtils.print(Constants.SERIALIZATION_COMPLETED);
    }

    public void deserializeMaterial() {
        String filePath = FilePath.FILE_PATH_ADDITION_MATERIAL.getPath();
        Object deserialize = Serializer.deserialize(filePath);
        List<AdditionalMaterial> additionalMaterials = (List<AdditionalMaterial>) deserialize;
        ConsoleUtils.print(Constants.DESERIALIZATION_COMPLETED);

        for (AdditionalMaterial additionalMaterial : additionalMaterials) {
            saveAdditionMaterial(additionalMaterial);
        }
    }

    private void saveAdditionMaterial(AdditionalMaterial additionalMaterial) {
        if (data.containsKey(additionalMaterial.getLectureId())) {
            List<AdditionalMaterial> list = data.get(additionalMaterial.getLectureId());
            list.add(additionalMaterial);
        } else {
            List<AdditionalMaterial> list = new ArrayList<>();
            list.add(additionalMaterial);
            data.put(additionalMaterial.getLectureId(), list);
        }
    }

    public void printAddMaterialsGroupingByLectureId() {
        List<AdditionalMaterial> materials = getAll();
        Map<Integer, List<AdditionalMaterial>> collect = materials.stream()
                .collect(Collectors.groupingBy(AdditionalMaterial::getLectureId));
        System.out.println(collect);
    }
}
