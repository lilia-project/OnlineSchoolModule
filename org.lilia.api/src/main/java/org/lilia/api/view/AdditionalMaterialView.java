package org.lilia.api.view;

import org.lilia.dal.model.AdditionalMaterial;
import org.lilia.dal.model.ResourceType;
import org.lilia.logger.utils.ConsoleUtils;
import org.lilia.logger.utils.Constants;
import org.lilia.service.dto.AdditionalMaterialDto;
import org.lilia.service.service.AdditionalMaterialService;
import org.lilia.service.service.LectureService;

import java.util.List;

public class AdditionalMaterialView {

    private final LectureService lectureService;
    String userChoice = "Y";

    public AdditionalMaterialView(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    public void workWithAdditionalMaterials(AdditionalMaterialService additionalMaterialService) {

        while (userChoice.equalsIgnoreCase("Y")) {

            switch (ConsoleUtils.choiceActionForAddMaterial()) {
                case 1:
                    while (userChoice.equalsIgnoreCase("Y")) {

                        createNew(additionalMaterialService);

                        ConsoleUtils.print(Constants.CREATE_NEW);
                        userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);
                    }
                    break;
                case 2:
                    AdditionalMaterial additionalMaterial = getAdditionalMaterial(additionalMaterialService);

                    ConsoleUtils.print(Constants.ELEMENT_EDIT);
                    userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);

                    editAdditionMaterial(additionalMaterialService, additionalMaterial);
                    break;
                case 3:
                    int lectureId = getAllByLectureId(additionalMaterialService);

                    ConsoleUtils.print(Constants.ACTION);
                    int action = ConsoleUtils.workWithListAddMaterial();
                    switch (action) {
                        case 1:
                            while (userChoice.equalsIgnoreCase("Y")) {
                                addNewMaterialToLecture(additionalMaterialService, lectureId);
                            }
                            break;
                        case 2:
                            deleteAdditionalMaterial(additionalMaterialService);
                            break;
                        case 3:
                            sortMaterial(additionalMaterialService, lectureId);
                            break;
                        case 4:
                            break;
                        default:
                            ConsoleUtils.print(Constants.ERROR);
                            break;
                    }
                    break;
                case 4:
                    deleteAdditionalMaterial(additionalMaterialService);
                    break;
                case 5:
                    additionalMaterialService.backupMaterial();
                    System.out.println("Backup created");
                    break;
                case 6:
                    additionalMaterialService.deserialize();
                    break;
                case 7:
                    additionalMaterialService.printAllWithGroupingByLectureId();
                    break;
                case 8:
                    ConsoleUtils.print(Constants.EXIT);
                    break;
                default:
                    ConsoleUtils.print(Constants.ERROR);
                    break;
            }
            ConsoleUtils.print(Constants.STAY_IN);
            userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);
        }
    }

    private int getAllByLectureId(AdditionalMaterialService additionalMaterialService) {
        ConsoleUtils.print(Constants.LECTURE_ID);
        int lectureId = lectureService.lectureIdIsValid();

        List<AdditionalMaterial> allByLectureId = additionalMaterialService.findAllByLectureId(lectureId);
        allByLectureId.forEach((System.out::println));
        return lectureId;
    }

    private AdditionalMaterial getAdditionalMaterial(AdditionalMaterialService additionalMaterialService) {
        ConsoleUtils.print(Constants.MATERIAL_ID);

        int additionalMaterialId = additionalMaterialService.additionalMaterialIdIsValid();
        AdditionalMaterial additionalMaterial = additionalMaterialService.getRequireById(additionalMaterialId);
        print(additionalMaterial);
        return additionalMaterial;
    }

    private void createNew(AdditionalMaterialService additionalMaterialService) {
        ConsoleUtils.print(Constants.LECTURE_ID);
        int lectureId = lectureService.lectureIdIsValid();

        addNewMaterialToLecture(additionalMaterialService, lectureId);
    }

    private void editAdditionMaterial(AdditionalMaterialService additionalMaterialService, AdditionalMaterial additionalMaterial) {
        while (userChoice.equalsIgnoreCase("Y")) {
            ConsoleUtils.print(Constants.NAME);
            String name = ConsoleUtils.readAndValidationInput(Constants.NAME_OR_DESCRIPTION);

            ConsoleUtils.print(Constants.LECTURE_ID);
            int lectureId = lectureService.lectureIdIsValid();

            ConsoleUtils.print(Constants.RESOURCE_TYPE);
            String parameter = ConsoleUtils.choiceParameterResource();
            ResourceType resourceType = ResourceType.valueOf(parameter);

            AdditionalMaterialDto additionalMaterialDto = additionalMaterialService.createAdditionalMaterialDto(lectureId, name, resourceType);
            AdditionalMaterial additionalMaterialUpdate = additionalMaterialService.updateAdditionalMaterial(additionalMaterial, additionalMaterialDto);
            System.out.println(additionalMaterialUpdate);

            ConsoleUtils.print(Constants.ELEMENT_EDIT);
            userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);
        }
    }

    private void deleteAdditionalMaterial(AdditionalMaterialService additionalMaterialService) {
        ConsoleUtils.print(Constants.MATERIAL_ID);
        int additionalMaterialId = additionalMaterialService.additionalMaterialIdIsValid();
        additionalMaterialService.deleteById(additionalMaterialId);
    }

    private void sortMaterial(AdditionalMaterialService additionalMaterialService, int lectureId) {

        additionalMaterialService.getAll(AdditionalMaterial.SortField.ID, lectureId);

        userChoice = "Y";
        while (userChoice.equalsIgnoreCase("Y")) {

            ConsoleUtils.print("by other parameter " + Constants.APPLY_SORT);
            userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);

            if (userChoice.equalsIgnoreCase("Y")) {
                ConsoleUtils.print(Constants.SELECT_PARAMETER_SORT);

                int sortChoice = ConsoleUtils.choiceParameterSort();
                AdditionalMaterial.SortField field = AdditionalMaterial.SortField.getById(sortChoice);

                additionalMaterialService.getAll(field, lectureId);
            }
        }
    }

    private void addNewMaterialToLecture(AdditionalMaterialService additionalMaterialService, int lectureId) {

        ConsoleUtils.print(Constants.NAME);
        String additionalMaterialName = ConsoleUtils.readAndValidationInput(Constants.NAME_OR_DESCRIPTION);

        additionalMaterialService.createAdditionalMaterial(additionalMaterialName, lectureId);


    }

    private void print(AdditionalMaterial additionalMaterial) {
        System.out.println(additionalMaterial);
    }
}
